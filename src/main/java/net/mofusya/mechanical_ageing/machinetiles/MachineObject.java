package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;

public record MachineObject(MachineTile tile, RegistryObject<Block> blockObject, RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntityObject, RegistryObject<MenuType<MachineMenu>> menuObject) {

    public Block block(){
        return this.blockObject().get();
    }

    public BlockEntityType<MachineBlockEntity> blockEntity(){
        return this.blockEntityObject().get();
    }

    public MenuType<MachineMenu> menu(){
        return this.menuObject().get();
    }
}
