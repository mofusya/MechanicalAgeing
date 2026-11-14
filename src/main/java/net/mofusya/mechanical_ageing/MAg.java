package net.mofusya.mechanical_ageing;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mofusya.mechanical_ageing.items.MAgCreativeTabs;
import net.mofusya.mechanical_ageing.matter.MAgMatters;
import net.mofusya.mechanical_ageing.ores.MAgOreTypes;
import org.slf4j.Logger;

/**
 * The main {@code @Mod} class.
 */
@Mod(MAg.MOD_ID)
public class MAg
{
    /**
     * The modId for this mod.
     */
    public static final String MOD_ID = "mechanical_ageing";
    /**
     * The static {@code Logger} for this class.
     */
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * The main constructor for this mod.
     */
    public MAg() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MAgMatters.R.register();
        MAgOreTypes.R.register(modEventBus);
        MAgCreativeTabs.R.register(modEventBus);
    }
}