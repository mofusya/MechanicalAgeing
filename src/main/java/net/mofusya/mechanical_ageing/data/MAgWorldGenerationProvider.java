package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.mofusya.mechanical_ageing.MAg;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MAgWorldGenerationProvider extends DatapackBuiltinEntriesProvider {
    public MAgWorldGenerationProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, builder(), Set.of(MAg.MOD_ID));
    }

    private static RegistrySetBuilder builder() {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            provider.generateWorldGenerations(builder);
        }
        return builder;
    }
}
