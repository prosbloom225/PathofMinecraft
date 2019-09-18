package com.prosbloom.pom.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DamageEvents {


    @SubscribeEvent
    public static void OnLivingHurt(LivingHurtEvent event) {

        // should probably ignore my own dmg types
        /*
        if (event.getSource() instanceof MyDamageSource) {
            return;
        }
         */

        if (event.getSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase){
            event.setAmount(event.getAmount() * 2);
        }
    }

}

