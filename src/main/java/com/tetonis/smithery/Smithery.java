package com.tetonis.smithery;

import com.tetonis.smithery.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "smithery" , name = "Smithery", version = "0.1")
public class Smithery
{
    @Mod.Instance
    public static Smithery instance;

    public static final String CLIENT = "com.tetonis.smithery.proxy.ClientProxy";
    public static final String SERVER = "com.tetonis.smithery.proxy.CommonProxy";
    @SidedProxy(clientSide = Smithery.CLIENT, serverSide = Smithery.SERVER)
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @EventHandler
    public void Init(FMLInitializationEvent event){
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event){

    }

}
