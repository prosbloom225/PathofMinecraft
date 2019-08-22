package com.prosbloom.pom.events;

import com.prosbloom.pom.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;


@Mod.EventBusSubscriber
public class MobDeathEvents {

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        Entity e = event.getEntity();
        addDrop(event.getDrops(), e, 0, 1, ModItems.alchemyOrb);
    }

    private static void addDrop(List<EntityItem> drops, Entity e, int min, int max, Item item) {
        int amount = e.world.rand.nextInt(Math.abs(max - min) + 1) + Math.min(min, max);
        if (amount > 0)
            drops.add(new EntityItem(e.world, e.posX, e.posY, e.posZ, new ItemStack(item, amount)));
    }

}
