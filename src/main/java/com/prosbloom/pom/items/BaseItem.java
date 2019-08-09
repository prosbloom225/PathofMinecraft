package com.prosbloom.pom.items;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item {

    public BaseItem() {
        super();
        setRegistryName(LibMisc.MODID, this.getBaseName());
        setCreativeTab(Pom.tabPom);
        setTranslationKey(LibMisc.MODID + "." + this.getBaseName());
    }

    public String getBaseName() {
        return "baseitem";
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
