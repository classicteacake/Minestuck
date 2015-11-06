package com.mraof.minestuck.network.skaianet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;

import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.Title;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import com.mraof.minestuck.MinestuckConfig;

/**
 * Handles session related stuff like title generation, consort choosing, and other session management stuff.
 * @author kirderf1
 */
public class SessionHandler {
	
	public static final String GLOBAL_SESSION_NAME = "global";
	
	/**
	 * The max numbers of players per session.
	 */
	public static int maxSize;
	
	/**
	 * If the current Minecraft world will act as if Minestuck.globalSession is true or not.
	 * Will be for example false even if Minestuck.globalSession is true if it can't merge all
	 * sessions into a single session.
	 */
	public static boolean singleSession;
	
	/**
	 * An array list of the current worlds sessions.
	 */
	static List<Session> sessions = new ArrayList<Session>();
	static Map<String, Session> sessionsByName = new HashMap<String, Session>();
	
	/**
	 * Called when the server loads a new world, after
	 * Minestuck has loaded the sessions from file.
	 */
	public static void serverStarted() {
		singleSession = MinestuckConfig.globalSession;
		if(!MinestuckConfig.globalSession) {
			split();
		} else
		{
			mergeAll();
			if(sessions.size() == 0)
			{
				Session mainSession = new Session();
				mainSession.name = GLOBAL_SESSION_NAME;
				sessions.add(mainSession);
				sessionsByName.put(mainSession.name, mainSession);
			}
		}
	}
	
	/**
	 * Merges all available sessions into one if it can.
	 * Used in the conversion of a non-global session world
	 * to a global session world.
	 */
	static void mergeAll() {
		if(!canMergeAll() || sessions.size() == 0)
		{
			singleSession = sessions.size() == 0;
			return;
		}
		
		Session session = sessions.get(0);
		for(int i = 1; i < sessions.size(); i++)
		{
			Session s = sessions.remove(i);
			session.connections.addAll(s.connections);
			if(s.skaiaId != 0) session.skaiaId = s.skaiaId;
			if(s.prospitId != 0) session.prospitId = s.prospitId;
			if(s.derseId != 0) session.derseId = s.derseId;
		}
		session.name = GLOBAL_SESSION_NAME;
		sessionsByName.clear();
		sessionsByName.put(session.name, session);
		
		session.completed = false;
	}
	
	/**
	 * Checks if it can merge all sessions in the current world into one.
	 * @return False if all registered players is more than maxSize, or if there exists more
	 * than one skaia, prospit, or derse dimension.
	 */
	static boolean canMergeAll()
	{
		int players = 0;
		boolean skaiaUsed = false, prospitUsed = false, derseUsed = false, customSession = false, customUsed = false;
		for(Session s : sessions)
		{
			if(s.skaiaId != 0)
				if(skaiaUsed) return false;
				else skaiaUsed = true;
			if(s.prospitId != 0)
				if(prospitUsed) return false;
				else prospitUsed = true;
			if(s.derseId != 0)
				if(derseUsed) return false;
				else derseUsed = true;
			if(s.isCustom())
				if(customUsed) return false;
				else customUsed = true;
			players += s.getPlayerList().size();
		}
		if(players > maxSize)
			return false;
		else return true;
	}
	
	/**
	 * Looks for the session that the player is a part of.
	 * @param player A string of the player's username.
	 * @return A session that contains at least one connection, that the player is a part of.
	 */
	static Session getPlayerSession(String player){
		for(Session s : sessions)
			for(SburbConnection c : s.connections)
				if(c.getClientName().equals(player) || c.getServerName().equals(player))
					return s;
		return null;
	}
	
	static String merge(Session cs, Session ss, SburbConnection sb)
	{
		String s = canMerge(cs, ss);
		if(s == null)
		{
			sessions.remove(ss);
			cs.connections.add(sb);
			cs.connections.addAll(ss.connections);
			if(cs.skaiaId == 0) cs.skaiaId = ss.skaiaId;
			if(cs.prospitId == 0) cs.prospitId = ss.prospitId;
			if(cs.derseId == 0) cs.derseId = ss.derseId;
			
			if(ss.isCustom())
			{
				sessionsByName.remove(ss.name);
				cs.name = ss.name;
				sessionsByName.put(cs.name, cs);
			}
			
		}
		return s;
	}
	
	static String canMerge(Session s0, Session s1)
	{
		if(s0.isCustom() && s1.isCustom())
			return "computer.messageConnectFail";
		if(MinestuckConfig.forceMaxSize && s0.getPlayerList().size()+s1.getPlayerList().size()>maxSize)
			return "session.bothSessionsFull";
		return null;
	}
	
	/**
	 * Splits up the main session into small sessions.
	 * Used for the conversion of a global session world to
	 * a non-global session.
	 */
	static void split() {
		if(MinestuckConfig.globalSession || sessions.size() != 1)
			return;
		
		split(sessions.get(0));
	}
	
	static void split(Session session)
	{
		sessions.remove(session);
		if(session.isCustom())
			sessionsByName.remove(session.name);
		boolean first = true;
		while(!session.connections.isEmpty()){
			Session s = new Session();
			if(!first)
			{
				s.connections.add(session.connections.remove(0));
				
			} else
			{
				s.name = session.name;
				s.skaiaId = session.skaiaId;
				s.prospitId = session.prospitId;
				s.derseId = session.derseId;
			}
			boolean found;
			do {
				found = false;
				Iterator<SburbConnection> iter = session.connections.iterator();
				while(iter.hasNext()){
					SburbConnection c = iter.next();
					if(s.containsPlayer(c.getClientName()) || s.containsPlayer(c.getServerName()) || first && !c.canSplit){
						found = true;
						iter.remove();
						s.connections.add(c);
					}
				}
			} while(found);
			s.checkIfCompleted();
			if(s.connections.size() > 0)
				sessions.add(s);
			first = false;
		}
	}
	
	/**
	 * Will check if two players can connect based on their main connections and sessions.
	 * Does NOT include session size checking.
	 * @return True if client connection is not null and client and server session is the same or 
	 * client connection is null and server connection is null.
	 */
	static boolean canConnect(String client, String server) {
		Session sClient = getPlayerSession(client), sServer = getPlayerSession(server);
		SburbConnection cClient = SkaianetHandler.getMainConnection(client, true);
		SburbConnection cServer = SkaianetHandler.getMainConnection(server, false);
		return cClient != null && sClient == sServer && (MinestuckConfig.allowSecondaryConnections || cClient == cServer) || cClient == null && cServer == null;
	}
	
	/**
	 * @return Null if successful or an unlocalized error message describing reason.
	 */
	static String onConnectionCreated(SburbConnection connection) {
		if(!canConnect(connection.getClientName(), connection.getServerName()))
			return "computer.messageConnectFailed";
		if(singleSession) {
			if(sessions.size() == 0)
				return "computer.messageConnectFailed";
			int i = (sessions.get(0).containsPlayer(connection.getClientName())?0:1)+(sessions.get(0).containsPlayer(connection.getServerName())?0:1);
			if(MinestuckConfig.forceMaxSize && sessions.get(0).getPlayerList().size()+i > maxSize)
				return "computer.singleSessionFull";
			else {
				sessions.get(0).connections.add(connection);
				return null;
			}
		} else {
			Session sClient = getPlayerSession(connection.getClientName()), sServer = getPlayerSession(connection.getServerName());
			if(sClient == null && sServer == null)
			{
				Session s = new Session();
				sessions.add(s);
				s.connections.add(connection);
				return null;
			} else if(sClient == null || sServer == null) {
				if(MinestuckConfig.forceMaxSize && (sClient == null?sServer:sClient).getPlayerList().size()+1 > maxSize)
					return "computer."+(sClient == null?"server":"client")+"SessionFull";
				(sClient == null?sServer:sClient).connections.add(connection);
				return null;
			} else {
				if(sClient == sServer) {
					sClient.connections.add(connection);
					return null;
				}
				else return merge(sClient, sServer, connection);
			}
		}
	}
	
	/**
	 * @param normal If the connection was closed by normal means.
	 * (includes everything but getting crushed by a meteor and other reasons for removal of a main connection)
	 */
	static void onConnectionClosed(SburbConnection connection, boolean normal) {
		Session s = getPlayerSession(connection.getClientName());
		
		if(!connection.isMain && !singleSession) {
			s.connections.remove(connection);
			if(s.connections.size() == 0)
				sessions.remove(s);
			else split(s);
		} else if(!normal) {
			s.connections.remove(connection);
			if(!SkaianetHandler.getAssociatedPartner(connection.getClientName(), false).isEmpty() && !connection.getServerName().equals(".null")) {
				SburbConnection c = SkaianetHandler.getMainConnection(connection.getClientName(), false);
				if(c.isActive)
					SkaianetHandler.closeConnection(c.getClientName(), c.getServerName(), true);
				switch(MinestuckConfig.escapeFailureMode) {
				case 0:
					c.serverName = connection.getServerName();
					break;
				case 1:
					c.serverName = ".null";
					break;
				}
			}
			if(s.connections.size() == 0)
				sessions.remove(s);
		}
	}
	
	static List<String> getServerList(String client) {
		ArrayList<String> list = new ArrayList<String>();
		for(String server : SkaianetHandler.serversOpen.keySet()) {
			if(canConnect(client, server))
				list.add(server);
		}
		return list;
	}
	
	public static NBTTagCompound createDataTag()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList sessionList = new NBTTagList();
		nbt.setTag("sessions", sessionList);
		int nameIndex = 1;
		for(int i = 0; i < sessions.size(); i++)
		{
			Session session = sessions.get(i);
			NBTTagList connectionList = new NBTTagList();
			for(SburbConnection c :session.connections)
			{
				NBTTagCompound connectionTag = new NBTTagCompound();
				connectionTag.setString("client", c.getClientName());
				connectionTag.setString("server", c.getServerName());
				connectionTag.setBoolean("isMain", c.isMain);
				connectionTag.setBoolean("isActive", c.isActive);
				if(c.isMain)
				{
					connectionTag.setInteger("clientDim", c.enteredGame ? c.clientHomeLand : 0);
					if(c.enteredGame && DimensionManager.isDimensionRegistered(c.clientHomeLand))
					{
						LandAspectRegistry.AspectCombination aspects = MinestuckDimensionHandler.getAspects(c.clientHomeLand);
						IChunkProvider chunkGen = MinecraftServer.getServer().worldServerForDimension(c.clientHomeLand).provider.createChunkGenerator();
						if(chunkGen instanceof ChunkProviderLands)
						{
							ChunkProviderLands landChunkGen = (ChunkProviderLands) chunkGen;
							if(landChunkGen.nameOrder)
							{
								connectionTag.setString("aspect1", aspects.aspectTerrain.getNames()[landChunkGen.nameIndex1]);
								connectionTag.setString("aspect2", aspects.aspectTitle.getNames()[landChunkGen.nameIndex2]);
							} else
							{
								connectionTag.setString("aspect1", aspects.aspectTitle.getNames()[landChunkGen.nameIndex2]);
								connectionTag.setString("aspect2", aspects.aspectTerrain.getNames()[landChunkGen.nameIndex1]);
							}
						}
						Title title = MinestuckPlayerData.getTitle(c.getClientName());
						connectionTag.setByte("class", (byte) title.getHeroClass().ordinal());
						connectionTag.setByte("aspect", (byte) title.getHeroAspect().ordinal());
					}
				}
				connectionList.appendTag(connectionTag);
			}
			
			NBTTagCompound sessionTag = new NBTTagCompound();
			if(session.name != null)
				sessionTag.setString("name", session.name);
			sessionTag.setTag("connections", connectionList);
			sessionList.appendTag(sessionTag);
		}
		return nbt;
	}
}