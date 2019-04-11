package com.prosbloom.pom.events;

import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = { Side.CLIENT })
public class PomTooltipEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)){
            PomItemData item = NbtHelper.getNbt(stack);
            event.getToolTip().add("(" + item.ilvl + ")");
            String suffix;
            List<String> advPrefix = new ArrayList<>();
            List<String> advSuffix = new ArrayList<>();
            List<Prefix> prefixes;
            List<Suffix> suffixes;

            // TODO - naming item on first modifiers
            String prefix = "";
            for (Prefix p : item.getPrefixes()){
                if (p != null) {
                    prefix = p.getName();
                    break;
                }
            }

            suffixes = NbtHelper.getSuffixes(stack);
            if (suffixes.size() > 0)
                suffix = suffixes.get(0).getName();
            else
                suffix = "";
            String name;
            switch (NbtHelper.getRarity(stack)) {
                case NORMAL:
                    name = TextFormatting.WHITE + "";
                    break;
                case MAGIC:
                    name = TextFormatting.BLUE+ "";
                    break;
                case RARE:
                    name = TextFormatting.YELLOW+ "";
                    break;
                case UNIQUE:
                    name = TextFormatting.RED+ "";
                    break;
                default:
                    name = TextFormatting.WHITE+ "";
            }
            event.getToolTip().add(String.format("%s %s %s %s", name, prefix, stack.getItem().getRegistryName(), suffix));
            if (GuiScreen.isShiftKeyDown()) {
                for (Prefix p : item.getPrefixes())
                    advPrefix.add(p.getAdvTooltip());
                suffixes.stream().forEach(s -> advSuffix.add(s.getAdvTooltip()));
                event.getToolTip().addAll(advPrefix);
                event.getToolTip().addAll(advSuffix);
            }

        }
    }
}
