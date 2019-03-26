package com.prosbloom.pom.events;

import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value =
        { Side.SERVER, Side.CLIENT })
public class PomEvents {


    @SubscribeEvent
    public static void EntityItemPickup(EntityItemPickupEvent event) {
        System.out.println("Picked up: " + event.getItem());
    }

    @SubscribeEvent
    public static void AnvilRepairEvent(AnvilRepairEvent event) {
        ItemStack rightStack = event.getIngredientInput();
        ItemStack outputStack = event.getItemResult();
        //ItemStack leftStack = event.getItemInput();

        if (rightStack.getItem() instanceof ICurrency) {
            // TODO - combine these if possible
            // shift click clears the anvil
            event.getEntityPlayer().inventory.mainInventory.stream()
                    .filter(stack->stack.getItem() instanceof ModSword)
                    .forEach(stack->((ICurrency)rightStack.getItem()).process(stack));
            // clicking item out of output slot leaves it in the anvil
            if (outputStack.getItem() instanceof ModSword)
                ((ICurrency) rightStack.getItem()).process(outputStack);
        }
    }

    @SubscribeEvent
    public static void AnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack rightItem = event.getRight();
        ItemStack leftItem = event.getLeft();

        if (leftItem.getItem() instanceof ModSword && rightItem.getItem() instanceof ICurrency) {
            // we need to set a valid cost and output item to give the player something in output slot
            event.setCost(1);
            event.setMaterialCost(1);
            ItemStack stack = new ItemStack(ModItems.modSword);
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setInteger(PomTag.ILVL, NbtHelper.getIlvl(leftItem));
            event.setOutput(stack);
        }
    }
}
