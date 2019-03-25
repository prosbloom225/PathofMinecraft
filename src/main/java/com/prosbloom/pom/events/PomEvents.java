package com.prosbloom.pom.events;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.items.ChaosOrb;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value =
        { Side.SERVER, Side.CLIENT })
public class PomEvents {


    @SubscribeEvent
    public static void playerContainerEvent(PlayerContainerEvent event) {
        for (ItemStack stack : event.getContainer().getInventory()) {
            if (stack.getItem() instanceof ModSword) {
                System.out.println("ContainerEvent: " + stack.getItem().getUnlocalizedName());
                if (stack.getTagCompound().hasKey(PomTag.CURRENCY_NAME))
                    System.out.println("Tag: " + stack.getTagCompound().getString(PomTag.CURRENCY_NAME));
            }
        }
    }
    @SubscribeEvent
    public static void EntityItemPickup(EntityItemPickupEvent event) {
        System.out.println("Picked up: " + event.getItem());
    }

    @SubscribeEvent
    public static void AnvilRepairEvent(AnvilRepairEvent event) {
        System.out.println("Left: " + event.getItemInput());
        System.out.println("Right: " + event.getIngredientInput());
        System.out.println("Right: " + event.getItemResult());
    }

    @SubscribeEvent
    public static void AnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack rightItem = event.getRight();
        ItemStack leftItem = event.getLeft();

        System.out.println("AnvilUpdate: " + leftItem.getItem().getUnlocalizedName() + " " + rightItem.getItem().getUnlocalizedName());
        if (leftItem.getItem() instanceof ModSword && rightItem.getItem() instanceof ChaosOrb) {
            event.setCost(1);
            event.setMaterialCost(1);
            ItemStack stack = new ItemStack(ModItems.modSword);
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setString(PomTag.CURRENCY_NAME, rightItem.getUnlocalizedName());
            if (leftItem.hasTagCompound() && leftItem.getTagCompound().hasKey(PomTag.ILVL))
                stack.getTagCompound().setInteger(PomTag.ILVL, leftItem.getTagCompound().getInteger(PomTag.ILVL));
            event.setOutput(stack);
        }
    }

    @SubscribeEvent
    public static void ItemCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        System.out.println("Crafting: " + event.getResult());
        System.out.println("C: " + event.crafting.getItem().getUnlocalizedName());
    }

}
