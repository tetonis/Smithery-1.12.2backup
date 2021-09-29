package com.tetonis.smithery.registration;

import com.tetonis.smithery.EnumHandler;
import com.tetonis.smithery.blocks.MoldTable;
import com.tetonis.smithery.items.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber(modid = "smithery")
public class Registration {

    public static MoldTable moldTable;
    public static PickaxeMold pickaxeMold;
    public static PickaxeMold pickaxeMoldFilledStone;
    public static PickaxeMold pickaxeHeadStone;
    public static PickaxeMold clayBucket;
    public static BucketBase clayBucketFired;
    public static BucketBase clayBucketFiredWater;
    public static BucketBase clayBucketFiredLava;
    public static CreativeTabs tab = new CreativeTabs("Smithery") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(moldTable));
        }
    };
    public static void init(){

        clayBucket = new PickaxeMold("clay_bucket", tab);
        clayBucketFired = new BucketBase("clay_bucket_fired", tab, Blocks.AIR);
        clayBucketFiredWater = new BucketBase("clay_bucket_fired_water", tab, Blocks.FLOWING_WATER);
        clayBucketFiredLava = new BucketBase("clay_bucket_fired_lava", tab, Blocks.FLOWING_LAVA);
        moldTable = new MoldTable("mold_table", Material.ANVIL, 5.0f, 6.0f, 1, "pickaxe", tab);
        pickaxeMold = new PickaxeMold("pickaxe_mold", tab);
        pickaxeHeadStone = new PickaxeMold("pickaxe_head_stone", tab);
        pickaxeMoldFilledStone = new PickaxeMold("pickaxe_mold_stone", tab);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(moldTable);
        GameRegistry.registerTileEntity(moldTable.getTileEntityClass(), moldTable.getRegistryName());
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event){
        ResourceLocation stonePickaxe = new ResourceLocation("minecraft:stone_pickaxe");
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
        modRegistry.remove(stonePickaxe);
        GameRegistry.addSmelting(clayBucket, new ItemStack(clayBucketFired), 0);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(pickaxeMold, pickaxeMoldFilledStone, pickaxeHeadStone, clayBucket, clayBucketFired, clayBucketFiredWater, clayBucketFiredLava);
        event.getRegistry().registerAll(new ItemBlock(moldTable).setRegistryName(moldTable.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event){
        registerRender(moldTable);
        registerRender(pickaxeMold);
        registerRender(pickaxeMoldFilledStone);
        registerRender(pickaxeHeadStone);
        registerRender(clayBucket);
        registerRender(clayBucketFired);
        registerRender(clayBucketFiredLava);
        registerRender(clayBucketFiredWater);
        for (int i = 0; i < EnumHandler.Molds.values().length; i++){
            registerRender(moldTable, i, "mold_table_" + EnumHandler.Molds.values()[i].getName());
        }
    }

    public static void registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation("smithery", fileName).toString()));
    }

    public static void registerRender(Block block){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString()));
    }

    public static void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
    }
}
