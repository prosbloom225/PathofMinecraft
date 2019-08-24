package com.prosbloom.pom.events;

import com.prosbloom.pom.Pom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;


@Mod.EventBusSubscriber
public class MobDeathEvents {

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        Entity e = event.getEntity();
        List<EntityItem> drops = event.getDrops();
        Pom.dropFactory.generateWeightedDrops(new Random().nextInt(3), 99)
                .forEach(d-> drops.add(new EntityItem(e.world, e.posX, e.posY, e.posZ, d)));
    }
}
