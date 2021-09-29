package com.tetonis.smithery.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(String name, CreativeTabs tab){
        this.setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(tab);
    }
}
