package com.tetonis.smithery.blocks;

import com.tetonis.smithery.EnumHandler;
import com.tetonis.smithery.registration.Registration;
import javafx.scene.paint.Material;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;

import javax.annotation.Nullable;

public class MoldTableEntity extends TileEntity implements ITickable {
    private ItemStackHandler inventory = new ItemStackHandler(1);

    private final PropertyEnum MOLD = MoldTable.MOLD;
    private int timer;
    private int tempTime = 100;
    private boolean isCasting = false;
    private String currentMaterial = "none";

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("isCasting", isCasting);
        compound.setString("material", currentMaterial);
        compound.setInteger("timer", timer);
        compound.setTag("inventory", inventory.serializeNBT());
        super.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        currentMaterial = compound.getString("material");
        isCasting = compound.getBoolean("isCasting");
        timer = compound.getInteger("timer");
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        IBlockState state = world.getBlockState(getPos());
        if(isCasting){
            if(inventory.getStackInSlot(0).getItem() == Registration.pickaxeMold){
                if(timer == 0){
                    world.setBlockState(getPos(), state.withProperty(MOLD, EnumHandler.Molds.PICKAXE_FILLED_STONE_PROGRESS));
                }
                timer++;
                if(timer >= tempTime){
                    timer = 0;
                    cast(Registration.pickaxeMold, currentMaterial);
                    world.setBlockState(getPos(), state.withProperty(MOLD, EnumHandler.Molds.PICKAXE_FILLED_STONE));
                    world.playSound(getPos().getX(), getPos().getX(), getPos().getX(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                    isCasting = false;
                    return;
                }
            }
            if(inventory.getStackInSlot(0).getItem() == Registration.pickaxeMoldFilledStone){
                if(timer == 0){
                    world.setBlockState(getPos(), state.withProperty(MOLD, EnumHandler.Molds.PICKAXE_HEAD_STONE_PROGRESS));
                }
                timer++;
                if(timer >= tempTime){
                    timer = 0;
                    cast(Registration.pickaxeMoldFilledStone, currentMaterial);
                    world.setBlockState(getPos(), state.withProperty(MOLD, EnumHandler.Molds.PICKAXE_HEAD_STONE));
                    world.playSound(getPos().getX(), getPos().getX(), getPos().getX(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                    isCasting = false;
                    return;
                }
            }
        }
    }



    private void cast(Item item, String material){
        Item result = recipe(item, material);
        ItemStack stack = new ItemStack(result);
        inventory.setStackInSlot(0, stack);
    }

    private Item recipe(Item item, String material){
        if(material == "none"){
            return Items.AIR;
        }
        if(item == Registration.pickaxeMold){
            if(material == "stone"){
                return Registration.pickaxeMoldFilledStone;
            }
            if(material == "gold"){
                return Items.AIR;
            }
            if(material == "iron"){
                return Items.AIR;
            }
        }
        if(item == Registration.pickaxeMoldFilledStone){
            if(material == "stone"){
                return Registration.pickaxeHeadStone;
            }
            if(material == "gold"){
                return Items.AIR;
            }
            if(material == "iron"){
                return Items.AIR;
            }
        }
        return null;
    }
}
