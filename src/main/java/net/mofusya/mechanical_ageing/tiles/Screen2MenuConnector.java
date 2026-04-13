package net.mofusya.mechanical_ageing.tiles;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;

@Mod.EventBusSubscriber(modid = MechanicalAgeing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Screen2MenuConnector {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<MenuType<MachineMenu>> menu : ModMachines.MACHINES.getMenuEntries()) {
                MenuScreens.register(menu.get(), MachineScreen::new);
            }
        });
    }
}
