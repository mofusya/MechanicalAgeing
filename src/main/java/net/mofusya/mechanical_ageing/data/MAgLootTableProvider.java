package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.mofusya.mechanical_ageing.data.loot_tables.MAgBlockLootTableProvider;

import java.util.List;
import java.util.Set;

public class MAgLootTableProvider {
    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(MAgBlockLootTableProvider::new, LootContextParamSets.BLOCK))
        );
    }
}
