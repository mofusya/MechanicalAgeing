package net.mofusya.mechanical_ageing.jade;

import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeMechanicalAgeingPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(MachineTileProvider.INSTANCE, MachineBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MachineTileProvider.INSTANCE, MachineBlock.class);
    }
}
