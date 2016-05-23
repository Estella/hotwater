package sorazodia.hotwater.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import sorazodia.hotwater.main.HotWater;

public class BlockSuperLava extends BlockFluidClassic implements IName
{
	private final String SUPERLAVA_NAME;
	
	public BlockSuperLava(Fluid fluid, String name, Material material) 
	{
		super(fluid, material);
		this.disableStats();
		this.setUnlocalizedName(name);
		this.SUPERLAVA_NAME = name;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
	{
		entity.attackEntityFrom(HotWater.Melted, 18.0F);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos)
	{			
		Block block = world.getBlockState(pos).getBlock();
		if(block.isAir(world, pos))return true;
		if(block == this)return false;
		else return true;
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(block == this)return false;
		else return true;
	}
	
	@Override
	public String getName()
	{
		return SUPERLAVA_NAME;
	}

}
