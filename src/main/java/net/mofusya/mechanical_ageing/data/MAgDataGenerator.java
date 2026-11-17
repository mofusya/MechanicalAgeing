package net.mofusya.mechanical_ageing.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.ores.MAgOreTypeData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = C.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MAgDataGenerator {
    private static final List<IMAgDataProvider> DATA_PROVIDERS = new ArrayList<>();

    static {
        add(MAgOreTypeData.INSTANCE);
    }

    private static void add(IMAgDataProvider provider){
        DATA_PROVIDERS.add(provider);
    }

    public static ImmutableList<IMAgDataProvider> getProviders(){
        return ImmutableList.copyOf(DATA_PROVIDERS);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), MAgLootTableProvider.create(packOutput));
        var blockTagProvider = new MAgBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new MAgItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new MAgRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new MAgWorldGenerationProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(), new MAgItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MAgBlockStateProvider(packOutput, existingFileHelper));
    }
}
