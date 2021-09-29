package com.tetonis.smithery.proxy;

import com.tetonis.smithery.registration.Registration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy{
    public void registerRenders() {
        Registration.init();
    }

    public void registerModelBakeryStuff() {
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
