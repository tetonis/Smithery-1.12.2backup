package com.tetonis.smithery.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block {


    public BlockBase(String name, Material material, float hardness, float resistance, int miningLevel, String tool, CreativeTabs tabs){
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel(tool, miningLevel);
        setCreativeTab(tabs);
    }


}
