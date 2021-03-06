package com.mraof.minestuck.client.util;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.*;
import com.mraof.minestuck.item.ItemMetalBoat;
import com.mraof.minestuck.item.ItemMinestuckBeverage;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.item.weapon.ItemDualWeapon;
import com.mraof.minestuck.util.GristType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

import static com.mraof.minestuck.block.MinestuckBlocks.*;
import static com.mraof.minestuck.item.MinestuckItems.*;

@SideOnly(Side.CLIENT)
public class MinestuckModelManager
{
	
	@SubscribeEvent
	public static void handleModelRegistry(ModelRegistryEvent event)
	{
		itemModels();
		blockModels();
	}
	
	private static void itemModels()
	{
		//3D Models
		if(MinestuckConfig.oldItemModels)
		{
			register(clawHammer, 0, "claw_hammer_old");
			register(zillyhooHammer, 0, "zillyhoo_hammer_old");
		} else
		{
			//register(clawHammer);
			register(clawHammer, 0, "claw_hammer_old");	//Until the issues with the model are fixed
			register(zillyhooHammer);
		}
		
		//Other
		register(sledgeHammer);
		register(blacksmithHammer);
		register(pogoHammer);
		register(telescopicSassacrusher);
		register(regiHammer);
		register(fearNoAnvil);
		register(popamaticVrillyhoo);
		register(scarletZillyhoo);
		register(mwrthwl);
		
		register(sord);
		register(cactusCutlass);
		register(katana);
		register(unbreakableKatana);
		register(firePoker);
		register(hotHandle);
		register(caledscratch);
		register(caledfwlch);
		register(royalDeringer);
		register(zillywairCutlass);
		register(regisword);
		register(scarletRibbitar);
		register(doggMachete);
		register(cobaltSabre);
		register(quantumSabre);
		
		register(blacksmithBane);
		register(scraxe);
		register(rubyCroak);
		register(hephaestusLumber);
		register(copseCrusher);
		register(qPHammerAxe);
		register(qFHammerAxe);
		
		
		register(dice);
		register(fluoriteOctet);
		
		ModelLoader.registerItemVariants(catClaws, new ResourceLocation("minestuck:catclaws_sheathed"), new ResourceLocation("minestuck:catclaws_drawn"));
		ModelLoader.setCustomMeshDefinition(catClaws, new DualWeaponDefinition(catClaws));
		
		register(sickle);
		register(homesSmellYaLater);
		register(fudgeSickle);
		register(regiSickle);
		register(clawSickle);
		register(candySickle);
		
		register(deuceClub);
		register(nightClub);
		register(pogoClub);
		register(metalBat);
		register(spikedClub);
		
		register(cane);
		register(ironCane);
		register(spearCane);
		register(regiCane);
		register(dragonCane);
		register(pogoCane);
		register(upStick);
		
		register(woodenSpoon);
		register(silverSpoon);
		ModelLoader.registerItemVariants(crockerSpork, new ResourceLocation("minestuck:crocker_fork"), new ResourceLocation("minestuck:crocker_spoon"));
		ModelLoader.setCustomMeshDefinition(crockerSpork, new CrockerSporkDefinition());
		register(skaiaFork);
		register(fork);
		register(spork);
		
		register(emeraldSword);
		register(emeraldAxe);
		register(emeraldPickaxe);
		register(emeraldShovel);
		register(emeraldHoe);
		
		register(prismarineHelmet);
		register(prismarineChestplate);
		register(prismarineLeggings);
		register(prismarineBoots);
		
		register(rawCruxite);
		register(rawUranium);
		register(energyCore);
		ModelLoader.registerItemVariants(cruxiteDowel, new ResourceLocation("minestuck:dowel_uncarved"), new ResourceLocation("minestuck:dowel_carved"), new ResourceLocation("minestuck:dowel_uncarved_blank"), new ResourceLocation("minestuck:dowel_carved_blank"));
		ModelLoader.setCustomMeshDefinition(cruxiteDowel, new CruxiteDowelDefinition());
		ModelLoader.registerItemVariants(captchaCard, new ResourceLocation("minestuck:card_empty"), new ResourceLocation("minestuck:card_full"), new ResourceLocation("minestuck:card_punched"));
		ModelLoader.setCustomMeshDefinition(captchaCard, new CaptchaCardDefinition());
		ModelLoader.registerItemVariants(cruxiteApple, new ResourceLocation("minestuck:cruxite_apple"), new ResourceLocation("minestuck:cruxite_apple_blank"));
		ModelLoader.setCustomMeshDefinition(cruxiteApple, new ColoredItemDefinition("minestuck:cruxite_apple"));
		ModelLoader.registerItemVariants(cruxitePotion, new ResourceLocation("minestuck:cruxite_potion"), new ResourceLocation("minestuck:cruxite_potion_blank"));
		ModelLoader.setCustomMeshDefinition(cruxitePotion, new ColoredItemDefinition("minestuck:cruxite_potion"));
		register(disk, 0, "disk_client");
		register(disk, 1, "disk_server");
		register(chessboard);
		register(minestuckBucket, 0, "bucket_oil");
		register(minestuckBucket, 1, "bucket_blood");
		register(minestuckBucket, 2, "bucket_brain_juice");
		for(int i = 0; i < ItemModus.NAMES.length; i++)
			register(modusCard, i, "modus_" + ItemModus.NAMES[i]);
		register(goldSeeds);
		for(int i = 0; i < ItemMetalBoat.NAMES.length; i++)
			register(metalBoat, i, "boat_" + ItemMetalBoat.NAMES[i]);
		register(obsidianBucket);

		register(candy, 0, "candy_corn");
		register(candy, GristType.REGISTRY.getID(GristType.Amber) + 1, "amber_gummy_worm");
		register(candy, GristType.REGISTRY.getID(GristType.Amethyst) + 1, "amethyst_hard_candy");
		register(candy, GristType.REGISTRY.getID(GristType.Artifact) + 1, "artifact_war_head");
		register(candy, GristType.REGISTRY.getID(GristType.Build) + 1, "build_gushers");
		register(candy, GristType.REGISTRY.getID(GristType.Caulk) + 1, "caulk_pretzel");
		register(candy, GristType.REGISTRY.getID(GristType.Chalk) + 1, "chalk_candy_cigarette");
		register(candy, GristType.REGISTRY.getID(GristType.Cobalt) + 1, "cobalt_gum");
		register(candy, GristType.REGISTRY.getID(GristType.Diamond) + 1, "diamond_mint");
		register(candy, GristType.REGISTRY.getID(GristType.Garnet) + 1, "garnet_twix");
		register(candy, GristType.REGISTRY.getID(GristType.Gold) + 1, "gold_candy_ribbon");
		register(candy, GristType.REGISTRY.getID(GristType.Iodine) + 1, "iodine_licorice");
		register(candy, GristType.REGISTRY.getID(GristType.Marble) + 1, "marble_jawbreaker");
		register(candy, GristType.REGISTRY.getID(GristType.Mercury) + 1, "mercury_sixlets");
		register(candy, GristType.REGISTRY.getID(GristType.Quartz) + 1, "quartz_jelly_bean");
		register(candy, GristType.REGISTRY.getID(GristType.Ruby) + 1, "ruby_lollipop");
		register(candy, GristType.REGISTRY.getID(GristType.Rust) + 1, "rust_gummy_eye");
		register(candy, GristType.REGISTRY.getID(GristType.Shale) + 1, "shale_peep");
		register(candy, GristType.REGISTRY.getID(GristType.Sulfur) + 1, "sulfur_candy_apple");
		register(candy, GristType.REGISTRY.getID(GristType.Tar) + 1, "tar_black_licorice");
		register(candy, GristType.REGISTRY.getID(GristType.Uranium) + 1, "uranium_gummy_bear");
		register(candy, GristType.REGISTRY.getID(GristType.Zillium) + 1, "zillium_skittles");
		
		for(int i = 0; i < ItemMinestuckBeverage.NAMES.length; i++)
			register(beverage, i, ItemMinestuckBeverage.NAMES[i]);
		register(bugOnAStick);
		register(chocolateBeetle);
		register(coneOfFlies);
		register(grasshopper);
		register(jarOfBugs);
		register(onion);
		register(salad);
		register(irradiatedSteak);
		
		register(threshDvd);
		register(crewPoster);
		register(sbahjPoster);
		register(carvingTool);
		register(crumplyHat);
		register(frogStatueReplica);
		register(stoneSlab);
		register(recordEmissaryOfDance);
		register(recordDanceStab);
		register(glowystoneDust);
		register(fakeArms);
	}
	
	private static void blockModels()
	{
		for(BlockChessTile.BlockType type : BlockChessTile.BlockType.values())
			register(chessTile, type.ordinal(), "chesstile_"+type.name);
		register(skaiaPortal);
		register(transportalizer);
		register(blockComputerOff);
		register(oreCruxite, 0, "cruxite_stone");
		register(oreCruxite, 1, "cruxite_netherrack");
		register(oreCruxite, 2, "cruxite_cobblestone");
		register(oreCruxite, 3, "cruxite_sandstone");
		register(oreCruxite, 4, "cruxite_sandstone_red");
		register(oreUranium, 0, "uranium_stone");
		register(oreUranium, 1, "uranium_netherrack");
		register(oreUranium, 2, "uranium_cobblestone");
		register(oreUranium, 3, "uranium_sandstone");
		register(oreUranium, 4, "uranium_sandstone_red");
		register(cruxiteBlock);
		register(genericObject);
		register(coalOreNetherrack);
		register(ironOreSandstone);
		register(ironOreSandstoneRed);
		register(goldOreSandstone);
		register(goldOreSandstoneRed);
		for(BlockColoredDirt.BlockType type : BlockColoredDirt.BlockType.values())
			register(coloredDirt, type.ordinal(), "colored_dirt_"+type.name);
		register(layeredSand);
		for(BlockSburbMachine.MachineType type : BlockSburbMachine.MachineType.values())
			register(sburbMachine, type.ordinal(), "machine_"+type.getName());
		for(BlockCrockerMachine.MachineType type : BlockCrockerMachine.MachineType.values())
			register(crockerMachine, type.ordinal(), "machine_"+type.getName());
		register(glowingMushroom);
		register(glowingLog);
		register(glowingPlanks);
		for(BlockMinestuckStone.BlockType type : BlockMinestuckStone.BlockType.values())
			register(stone, type.getMetadata(), type.getName());
		register(coarseStoneStairs);
		register(shadeBrickStairs);
		register(frostBrickStairs);
		register(castIronStairs);
		for(BlockMinestuckLog.BlockType type : BlockMinestuckLog.BlockType.values())
			register(log, type.ordinal(), type.getName()+"_log");
		register(woodenCactus);
		register(sugarCube);
		register(appleCake);
		register(blueCake);
		register(coldCake);
		register(redCake);
		register(hotCake);
		register(reverseCake);
		
		register(primedTnt);
		register(unstableTnt);
		register(instantTnt);
		register(woodenExplosiveButton);
		register(stoneExplosiveButton);

		register(uraniumCooker);
		
		ModelLoader.setCustomStateMapper(blockOil, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockBlood, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockBrainJuice, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(primedTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(unstableTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(instantTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(log, (new StateMap.Builder()).withName(BlockMinestuckLog.VARIANT).withSuffix("_log").build());
		ModelLoader.setCustomStateMapper(woodenCactus, new StateMap.Builder().ignore(BlockCactus.AGE).build());
		ModelLoader.setCustomStateMapper(returnNode, (Block block) -> Collections.emptyMap());
		ModelLoader.setCustomStateMapper(gate, (Block block) -> Collections.emptyMap());
		ModelLoader.setCustomStateMapper(rabbitSpawner, (Block block) -> Collections.emptyMap());
	}
	
	private static void register(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void register(Item item, int meta, String modelResource)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("minestuck:"+modelResource, "inventory"));
	}
	
	private static void register(Block block)
	{
		register(Item.getItemFromBlock(block));
	}
	
	private static void register(Block block, int meta, String modelResource)
	{
		register(Item.getItemFromBlock(block), meta, modelResource);
	}
	
	private static class CrockerSporkDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return new ModelResourceLocation("minestuck:" + (crockerSpork.isSpoon(stack) ? "crocker_spoon" : "crocker_fork"), "inventory");
		}
	}
	
	
	private static class DualWeaponDefinition implements ItemMeshDefinition
	{
		private Item item;
		public DualWeaponDefinition(Item item){
			this.item=item;
		}
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			if(((ItemDualWeapon)this.item).IsDrawn(stack)){
				
				return new ModelResourceLocation("minestuck:" + ((ItemDualWeapon)this.item).Prefex + "_drawn","inventory");
			}else
				return new ModelResourceLocation("minestuck:" + ((ItemDualWeapon)this.item).Prefex + "_sheathed","inventory");
			
		}
	}
	


	
	private static class CruxiteDowelDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			String suffix = stack.getMetadata() == 0 ? "" : "_blank";
			return new ModelResourceLocation("minestuck:"+(stack.hasTagCompound() && stack.getTagCompound().hasKey("contentID") ? "dowel_carved" : "dowel_uncarved")+suffix, "inventory");
		}
	}
	
	private static class ColoredItemDefinition implements ItemMeshDefinition
	{
		private String name;
		ColoredItemDefinition(String name)
		{
			this.name = name;
		}
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			if(stack.getMetadata() == 0)
				return new ModelResourceLocation(name, "inventory");
			else return new ModelResourceLocation(name + "_blank", "inventory");
		}
	}
	
	private static class CaptchaCardDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String str;
			if(nbt != null && nbt.hasKey("contentID"))
			{
				if(nbt.getBoolean("punched") && !(Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("contentID"))) == Item.getItemFromBlock(genericObject)))
					str = "card_punched";
				else str = "card_full";
			}
			else str = "card_empty";
			return new ModelResourceLocation("minestuck:" + str, "inventory");
		}
	}
}
