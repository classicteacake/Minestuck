package com.mraof.minestuck.world.lands.title;

import com.mraof.minestuck.world.lands.decorator.structure.CogDecorator;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.Vec3d;

public class LandAspectClockwork extends TitleLandAspect
{
	
	@Override
	public String getPrimaryName()
	{
		return "clockwork";
	}
	
	@Override
	public String[] getNames()
	{
		return new String[]{"clockwork", "gear"};
	}
	
	@Override
	protected void prepareChunkProvider(ChunkProviderLands chunkProvider)
	{
		chunkProvider.mergeFogColor(new Vec3d(0.5, 0.5, 0.5), 0.5F);
	}
	
	@Override
	protected void prepareChunkProviderServer(ChunkProviderLands chunkProvider)
	{
		chunkProvider.blockRegistry.setBlockState("structure_wool_2", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER));
		chunkProvider.decorators.add(new CogDecorator());
		chunkProvider.sortDecorators();
	}
	
}
