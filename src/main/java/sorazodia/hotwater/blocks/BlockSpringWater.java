package sorazodia.hotwater.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import sorazodia.hotwater.mechanics.EffectManager;

public class BlockSpringWater extends BlockFluidClassic implements IName
{
	private float hunger = 0;
	private final String SPRING_WATER_NAME;

	public BlockSpringWater(Fluid fluid, String name, Material material)
	{
		super(fluid, material);
		this.setUnlocalizedName(name);
		this.SPRING_WATER_NAME = name;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
	{
		if ((entity instanceof EntityLivingBase)&& !world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
			for (Potion remove : EffectManager.getBlacklist())
			{
				living.removePotionEffect(remove.id);
			}
			
			for (PotionEffect activeEffect : living.getActivePotionEffects())
			{
				Potion potion = Potion.potionTypes[activeEffect.getPotionID()];
				
				if (potion.isBadEffect() && !EffectManager.getWhitelist().contains(potion))
					living.removePotionEffect(activeEffect.getPotionID());
			}

			if (living.ticksExisted % 100 == 0 && living.getHealth() > 0)
			{
				living.setHealth(living.getHealth() + 0.5F);
				if (living instanceof EntityPlayer)
					((EntityPlayer) living).getFoodStats().addExhaustion(hunger);
			}
		}
	}
	
	@Override
	public String getName()
	{
		return SPRING_WATER_NAME;
	}

}
