package net.mofusya.mechanical_ageing.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.mofusya.mechanical_ageing.MAg;

public class MAgTags {
    public static class Items{
        public static final TagKey<Item> MACHINE_UPGRADE_ARCHIVE = create("upgrade_archive");

        private static TagKey<Item> create(String name){
            return ItemTags.create(new ResourceLocation(MAg.MOD_ID, name));
        }

        private static TagKey<Item> createForge(String name){
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}