package net.mofusya.mechanical_ageing.tint;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.ores.MAgOreTypeTints;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = C.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MAgItemLikeTints {
    private static final List<IMAgTintProvider> TINT_PROVIDERS = new ArrayList<>();

    static {
        add(MAgOreTypeTints.INSTANCE);
    }

    private static void add(IMAgTintProvider provider) {
        TINT_PROVIDERS.add(provider);
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersItem(RegisterColorHandlersEvent.Item event) {
        for (IMAgTintProvider tintProvider : TINT_PROVIDERS) {
            tintProvider.registerItemColorTint(event::register, event.getItemColors());
        }
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersBlock(RegisterColorHandlersEvent.Block event) {
        for (IMAgTintProvider tintProvider : TINT_PROVIDERS) {
            tintProvider.registerBlockColorTint(event::register, event.getBlockColors());
        }
    }
}
