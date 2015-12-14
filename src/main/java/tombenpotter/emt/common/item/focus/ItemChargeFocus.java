package tombenpotter.emt.common.item.focus;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import tombenpotter.emt.common.item.focus.ItemBaseFocus;
import tombenpotter.emt.common.util.EMTConfigHandler;

public class ItemChargeFocus extends ItemBaseFocus {

	private static final AspectList visCost = new AspectList().add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.AIR, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10);

	public ItemChargeFocus() {
		super(".focus.charge", "focus_charge");
	}

	@Override
	public int getFocusColor(ItemStack stack) {
		return 0xFFFF00;
	}

	@Override
	public AspectList getVisCost(ItemStack stack) {
		return visCost;
	}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "WANDCHARGING";
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition) {
		ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
		if (player.capabilities.isCreativeMode || wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, true)) {
			if (!world.isRemote) {

				int energyLeft = EMTConfigHandler.chargeFocusProduction;
				for (int i = 0; i < player.inventory.armorInventory.length; i++) {
					if (energyLeft > 0) {
						if ((player.inventory.armorInventory[i] != null) && (player.inventory.armorInventory[i].getItem() instanceof IElectricItem)) {
							double sentPacket = ElectricItem.manager.charge(player.inventory.armorInventory[i], energyLeft, 4, false, false);
							energyLeft -= sentPacket;
						}
					}
					else {
						return itemstack;
					}
				}
				for (int j = 0; j < player.inventory.mainInventory.length; j++) {
					if (energyLeft > 0) {
						if ((player.inventory.mainInventory[j] != null) && (player.inventory.mainInventory[j].getItem() instanceof IElectricItem)) {
							double sentPacket = ElectricItem.manager.charge(player.inventory.mainInventory[j], energyLeft, 4, false, false);
							energyLeft -= sentPacket;
						}
					}
					else {
						return itemstack;
					}
				}
			}
		}
		return itemstack;
	}
}