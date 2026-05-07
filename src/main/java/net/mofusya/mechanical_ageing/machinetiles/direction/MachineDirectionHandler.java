package net.mofusya.mechanical_ageing.machinetiles.direction;

import net.minecraft.nbt.CompoundTag;
import net.mofusya.mechanical_ageing.util.annotations.FieldsAreNonNullByDefault;
import net.mofusya.mechanical_ageing.util.annotations.ParametersAreNonNullByDefault;

import java.util.ArrayList;

@FieldsAreNonNullByDefault
@ParametersAreNonNullByDefault
public class MachineDirectionHandler {
    private final DirectionType[] itemDirection;
    private final DirectionType[] matterDirection;
    private final DirectionType[] energyDirection;
    private DirectionType fluidDirection = DirectionType.UP;

    public MachineDirectionHandler(int itemCount, int matteCount, int energyCount) {
        this.itemDirection = new DirectionType[itemCount];
        this.matterDirection = new DirectionType[matteCount];
        this.energyDirection = new DirectionType[energyCount];
    }

    public DirectionType getItemDirection(int slot) {
        if (this.itemDirection[slot] == null) {
            this.itemDirection[slot] = DirectionType.NONE;
        }
        return this.itemDirection[slot];
    }

    public void setItemDirection(int slot, DirectionType direction) {
        this.itemDirection[slot] = direction;
    }

    public ArrayList<Integer> getItemSlots(DirectionType direction){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for (int i = 0; i < this.itemDirection.length; i++) {
            var pDirection = this.getItemDirection(i);
            if (pDirection.equals(direction)) toReturn.add(i);
        }
        return toReturn;
    }

    public DirectionType getMatterDirection(int slot) {
        if (this.matterDirection[slot] == null) {
            this.matterDirection[slot] = DirectionType.NONE;
        }
        return this.matterDirection[slot];
    }

    public void setMatterDirection(int slot, DirectionType direction) {
        this.matterDirection[slot] = direction;
    }

    public ArrayList<Integer> getMatterSlots(DirectionType direction){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for (int i = 0; i < this.matterDirection.length; i++) {
            var pDirection = this.getMatterDirection(i);
            if (pDirection.equals(direction)) toReturn.add(i);
        }
        return toReturn;
    }

    public DirectionType getFluidDirection() {
        return this.fluidDirection;
    }

    public void setFluidDirection(DirectionType direction) {
        this.fluidDirection = direction;
    }

    public DirectionType getEnergyDirection(int slot) {
        if (this.energyDirection[slot] == null) {
            this.energyDirection[slot] = DirectionType.NONE;
        }
        return this.energyDirection[slot];
    }

    public void setEnergyDirection(int slot, DirectionType direction) {
        this.energyDirection[slot] = direction;
    }

    public ArrayList<Integer> getEnergySlots(DirectionType direction){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for (int i = 0; i < this.energyDirection.length; i++) {
            var pDirection = this.getEnergyDirection(i);
            if (pDirection.equals(direction)) toReturn.add(i);
        }
        return toReturn;
    }

    public void serializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.itemDirection.length; i++) {
            DirectionType direction = this.getItemDirection(i);
            tag.putString("direction_handler_item" + i, direction.toString());
        }
        for (int i = 0; i < this.matterDirection.length; i++) {
            DirectionType direction = this.getMatterDirection(i);
            tag.putString("direction_handler_matter" + i, direction.name());
        }
        tag.putString("direction_handler_fluid", this.getFluidDirection().name());
        for (int i = 0; i < this.energyDirection.length; i++) {
            DirectionType direction = this.getEnergyDirection(i);
            tag.putString("direction_handler_energy" + i, direction.name());
        }
    }

    public void deserializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.itemDirection.length; i++) {
            this.setItemDirection(i, DirectionType.valueOf(tag.getString("direction_handler_item" + i)));
        }
        for (int i = 0; i < this.matterDirection.length; i++) {
            this.setMatterDirection(i, DirectionType.valueOf(tag.getString("direction_handler_matter" + i)));
        }
        this.setFluidDirection(DirectionType.valueOf(tag.getString("direction_handler_fluid")));
        for (int i = 0; i < this.energyDirection.length; i++) {
            this.setEnergyDirection(i, DirectionType.valueOf(tag.getString("direction_handler_energy" + i)));
        }
    }
}