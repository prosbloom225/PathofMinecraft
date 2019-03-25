package com.prosbloom.pom;


import com.prosbloom.pom.factory.CurrencyFactory;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Currency;


@Mod(modid = Pom.MODID, name = Pom.MODNAME, version = Pom.MODVERSION, dependencies = "", useMetadata = true)
public class Pom {

    public static final String MODID = "pom";
    public static final String MODNAME = "Path of Minecraft";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "com.prosbloom.pom.proxy.ClientProxy", serverSide = "com.prosbloom.pom.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabPom = new CreativeTabs("Pom") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(new ItemEnderEye());
        }
    };

    @Mod.Instance
    public static Pom instance;

    public static ItemFactory itemFactory = new ItemFactory();
    public static CurrencyFactory currencyFactory;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
