package com.prosbloom.pom.items;

import com.prosbloom.pom.Pom;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCase extends Item {
    public ItemCase() {
        super();
        setUnlocalizedName("itemcase");
        setRegistryName(Pom.MODID, "itemcase");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        ItemStack loot = Pom.itemFactory.testGenerate(99);
        player.dropItem(loot, false, true);
        //itemStack.shrink(1);  // for testing
        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
