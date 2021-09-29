package com.tetonis.smithery.proxy;

import com.tetonis.smithery.registration.Registration;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy implements IProxy{
    @Override
    public void registerRenders() {
        Registration.init();
    }

    @Override
    public void registerModelBakeryStuff() {
        ModelBakery.registerItemVariants(Item.getItemFromBlock(Registration.moldTable), new ResourceLocation("smithery", "mold_table_none"), new ResourceLocation("smithery", "mold_table_pickaxe"));
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        registerRenders();
        registerModelBakeryStuff();
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
