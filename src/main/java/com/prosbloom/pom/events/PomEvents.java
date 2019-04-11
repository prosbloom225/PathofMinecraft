package com.prosbloom.pom.events;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(value =
        { Side.SERVER, Side.CLIENT })
public class PomEvents {

    // Inventory closed after adding new item
    @SubscribeEvent
    public static void PlayerContainerEvent(PlayerContainerEvent event) {
        System.out.println("Container: " + event.getContainer());
        List<ItemStack> stacks = event.getContainer().getInventory().stream()
                .filter(i-> i.getItem().getRegistryName().toString().contains("sword"))
                .filter(i-> !i.hasTagCompound() || (i.hasTagCompound() && !i.getTagCompound().hasKey(PomTag.POM_TAG)))
                .collect(Collectors.toList());
        for (ItemStack stack : stacks) {
            System.out.println("Adding item to system" + stack.getDisplayName());
            // TODO - need to handle ilvl somehow...
            // other mods base it on distance from spawn.  should probably do that for the time
            // ultimately id like to have maps have zone level and drop a range of ilvls like path
            Pom.itemFactory.generateItem(stack, 99);
        }
    }

    //@SideOnly(Side.SERVER)
    @SubscribeEvent
    public static void EntityItemPickup(EntityItemPickupEvent event) {
        System.out.println("Picked up: " + event.getItem());
        if (event.getItem().getName().contains("Sword")){

        }
    }

    //@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void AnvilRepairEvent(AnvilRepairEvent event) {
        ItemStack rightStack = event.getIngredientInput();
        ItemStack outputStack = event.getItemResult();
        ItemStack leftStack = event.getItemInput();

        // TODO - ok so if you shift click a ModSword out of anvil, with a ModSword in hand, it breaks, fix it later
        if (rightStack.getItem() instanceof ICurrency){
            ItemStack newStack = leftStack.copy();
            // only apply currency on server
            if (event.getEntity().getEntityWorld().isRemote == false)
                newStack = ((ICurrency) rightStack.getItem()).process(newStack);

            // TODO - item in hand isnt refreshed until a new container event triggers, item doesnt appear updated when mousing out of anvil
            // clicking item out of output slot leaves it in the anvil
            if (event.getEntityPlayer().inventory.getItemStack().getItem() instanceof ModSword)
                event.getEntityPlayer().inventory.setItemStack(newStack.copy());
            else
                // shift click clears the anvil
                event.getEntityPlayer().inventory.addItemStackToInventory(newStack);
            // remove the(all) dummy item(s)
            event.getEntityPlayer().inventory.mainInventory.stream()
                    .filter(stack->stack.getItem() instanceof ModSword && NbtHelper.isDummy(stack))
                    .forEach(stack->stack.shrink(1));
        }
    }

    @SubscribeEvent
    public static void AnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack rightItem = event.getRight();
        ItemStack leftItem = event.getLeft();
        if (leftItem.getItem() instanceof ModSword && rightItem.getItem() instanceof ICurrency && ((ICurrency)rightItem.getItem()).canProcess(leftItem)) {
            // we need to set a valid cost and output item to give the player something in output slot
            event.setCost(1);
            event.setMaterialCost(1);
            ItemStack stack = new ItemStack(ModItems.modSword);
            NbtHelper.setIlvl(stack, 99);
            NbtHelper.setDummy(stack, true);
            event.setOutput(stack);
        }
    }
}
