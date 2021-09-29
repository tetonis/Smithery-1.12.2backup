package com.tetonis.smithery.blocks;

import com.tetonis.smithery.EnumHandler;
import com.tetonis.smithery.registration.Registration;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class MoldTable extends BlockTileEntity<MoldTableEntity> implements IMetaBlockName{

    public static final PropertyEnum MOLD = PropertyEnum.create("mold", EnumHandler.Molds.class);

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(MOLD, EnumHandler.Molds.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumHandler.Molds mold = (EnumHandler.Molds) state.getValue(MOLD);
        return mold.getID();
    }

    public MoldTable(String name, Material material, float hardness, float resistance, int miningLevel, String tool, CreativeTabs tabs) {
        super(name, material, hardness, resistance, miningLevel, tool, tabs);
        this.setDefaultState(this.blockState.getBaseState().withProperty(MOLD, EnumHandler.Molds.NONE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {MOLD});
    }

    @Override
    public Class<MoldTableEntity> getTileEntityClass() {
        return MoldTableEntity.class;
    }

    @Nullable
    @Override
    public MoldTableEntity createTileEntity(World world, IBlockState state) {
        return new MoldTableEntity();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        MoldTableEntity tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if(!stack.isEmpty()){
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ());
            item.setItem(stack);
            worldIn.spawnEntity(item);
        }
        tile.invalidate();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            if(!playerIn.isSneaking()){
                MoldTableEntity tile = getTileEntity(worldIn, pos);
                IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                if(playerIn.getHeldItem(hand).isEmpty()){
                    playerIn.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                    NBTTagCompound newtag = new NBTTagCompound();
                    tile.writeToNBT(newtag);
                    newtag.setBoolean("isCasting", false);
                    newtag.setString("material", "none");
                    newtag.setInteger("timer", 0);
                    tile.readFromNBT(newtag);
                    worldIn.setBlockState(pos, state.withProperty(MOLD, EnumHandler.Molds.NONE));
                }else if(playerIn.getHeldItem(hand).getItem().equals(Registration.pickaxeMold) && itemHandler.getStackInSlot(0).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, playerIn.getHeldItem(hand), false));
                    NBTTagCompound newtag = new NBTTagCompound();
                    tile.writeToNBT(newtag);
                    newtag.setBoolean("isCasting", false);
                    newtag.setString("material", "none");
                    newtag.setInteger("timer", 0);
                    tile.readFromNBT(newtag);
                    worldIn.setBlockState(pos, state.withProperty(MOLD, EnumHandler.Molds.PICKAXE));
                } else if (playerIn.getHeldItem(hand).getItem().equals(Registration.pickaxeMoldFilledStone) && itemHandler.getStackInSlot(0).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, playerIn.getHeldItem(hand), false));
                    NBTTagCompound newtag = new NBTTagCompound();
                    tile.writeToNBT(newtag);
                    newtag.setBoolean("isCasting", false);
                    newtag.setString("material", "none");
                    newtag.setInteger("timer", 0);
                    tile.readFromNBT(newtag);
                    worldIn.setBlockState(pos, state.withProperty(MOLD, EnumHandler.Molds.PICKAXE_FILLED_STONE));
                } else if (playerIn.getHeldItem(hand).getItem().equals(Items.LAVA_BUCKET) && itemHandler.getStackInSlot(0).getItem().equals(Registration.pickaxeMold)) {
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    NBTTagCompound newtag = new NBTTagCompound();
                    tile.writeToNBT(newtag);
                    newtag.setBoolean("isCasting", true);
                    newtag.setString("material", "stone");
                    newtag.setInteger("timer", 0);
                    tile.readFromNBT(newtag);
                }else if (playerIn.getHeldItem(hand).getItem().equals(Items.LAVA_BUCKET) && itemHandler.getStackInSlot(0).getItem().equals(Registration.pickaxeMoldFilledStone)){
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    NBTTagCompound newtag = new NBTTagCompound();
                    tile.writeToNBT(newtag);
                    newtag.setBoolean("isCasting", true);
                    newtag.setString("material", "stone");
                    newtag.setInteger("timer", 0);
                    tile.readFromNBT(newtag);
                }
                tile.markDirty();
            }
        }
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.Molds.values()[stack.getItemDamage()].getName();
    }
}
