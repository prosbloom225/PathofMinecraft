package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.Modifiers;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemFactory {
    private static Logger logger;
    private static Gson gson;
    private Modifiers modifiers;
    //private List<Modifier> prefixes;


    public ItemFactory() {
        gson = new Gson();
        //Type listType = new TypeToken<List<Modifiers>>() {}.getType();
        modifiers = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/prefixes.json"))), Modifiers.class);
    }
    /*
    public IModifiable regenerate(int ilvl, IModifiable item){
        // Get the list of prefixes available for our ilvl
        List <Modifier>viablePrefixes = modifiers.getPrefixes().stream().filter(i->ilvl >= i.getIlvl()).collect(Collectors.toList());
        // Grab a random one and apply it
        //item.addPrefix(viablePrefixes.get(new Random().nextInt(viablePrefixes.size())));
        return item;
    }
    */

    public Modifier getPrefix (String name) {
        return modifiers.getPrefixes().stream()
                .filter(s-> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }

    public ItemStack testGenerate(int ilvl) {
        ItemStack itemStack = new ItemStack(ModItems.modSword);
        List <Modifier>viablePrefixes = modifiers.getPrefixes().stream().filter(i->ilvl>= i.getIlvl()).collect(Collectors.toList());
        Modifier prefix = viablePrefixes.get(new Random().nextInt(viablePrefixes.size()));

        // build nbt for item
        NBTTagCompound nbt = prefix.toNbt();


        // roll the mods
        float physMod =1 + (prefix.getDamageModRange()[0] + new Random().nextFloat() * (prefix.getDamageModRange()[1] - prefix.getDamageModRange()[0]));
        System.out.println("PHYSMOD: " + physMod);
        nbt.setFloat(PomTag.MOD_DAMAGEMOD, physMod);
        NBTTagCompound tag = new NBTTagCompound();
        // create the prefix
        tag.setTag(PomTag.PREFIX, nbt);



        // save the item
        itemStack.setTagCompound(tag);
        return itemStack;
    }

    public static void main(String[] args) {
        ItemFactory itemFactory = new ItemFactory();
        ItemStack stack = itemFactory.testGenerate(1);
        ModSword ms = new ModSword();
        System.out.println("DMG: " + ms.getAttackDamage());

        System.out.println("end");
    }
}
