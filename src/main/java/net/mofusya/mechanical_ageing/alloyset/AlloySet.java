package net.mofusya.mechanical_ageing.alloyset;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.matter.MatterType;

public final class AlloySet {
    private final RegistryObject<Item> alloy;
    private final RegistryObject<Item> compressed;
    private final RegistryObject<Item> duoCompressed;
    private final RegistryObject<Item> triCompressed;
    private final RegistryObject<Item> quadCompressed;
    private final MatterType matterType;
    private final int color;

    private final String modId;
    private final String id;

    public AlloySet(String modId, String id, RegistryObject<Item> alloy, RegistryObject<Item> compressed, RegistryObject<Item> duoCompressed, RegistryObject<Item> triCompressed, RegistryObject<Item> quadCompressed, MatterType matterType, int color) {
        this.modId = modId;
        this.id = id;
        this.alloy = alloy;
        this.compressed = compressed;
        this.duoCompressed = duoCompressed;
        this.triCompressed = triCompressed;
        this.quadCompressed = quadCompressed;
        this.matterType = matterType;
        this.color = color;
    }

    public Item alloy() {
        return this.alloy.get();
    }

    public Item compressed() {
        return this.compressed.get();
    }

    public Item duoCompressed() {
        return this.duoCompressed.get();
    }

    public Item triCompressed() {
        return this.triCompressed.get();
    }

    public Item quadCompressed() {
        return this.quadCompressed.get();
    }

    public int color() {
        return this.color;
    }

    public MatterType matterType() {
        return this.matterType;
    }

    public String getModId() {
        return this.modId;
    }

    public String getId() {
        return this.id;
    }

    public static Builder builder(double density, double hardness, int meltingPoint, int boilingPoint) {
        return new Builder(density, hardness, meltingPoint, boilingPoint);
    }

    public static class Builder {
        private final double density;
        private final double hardness;
        private final int meltingPoint;
        private final int boilingPoint;
        private float radiationMultiplier = 1f;
        private boolean magnetic = false;
        private int color = 0xFFFFFF;

        private Item.Properties itemBuild = new Item.Properties();

        private Builder(double density, double hardness, int meltingPoint, int boilingPoint) {
            this.density = density;
            this.hardness = hardness;
            this.meltingPoint = meltingPoint;
            this.boilingPoint = boilingPoint;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder radiationMultiplier(float radiationMultiplier) {
            this.radiationMultiplier = radiationMultiplier;
            return this;
        }

        public Builder magnetic() {
            this.magnetic = true;
            return this;
        }

        public Builder itemBuild(Item.Properties itemBuild) {
            this.itemBuild = itemBuild;
            return this;
        }

        public int getBoilingPoint() {
            return this.boilingPoint;
        }

        public int getColor() {
            return this.color;
        }

        public double getDensity() {
            return this.density;
        }

        public double getHardness() {
            return this.hardness;
        }

        public Item.Properties getItemBuild() {
            return this.itemBuild;
        }

        public boolean isMagnetic() {
            return this.magnetic;
        }

        public int getMeltingPoint() {
            return this.meltingPoint;
        }

        public float getRadiationMultiplier() {
            return this.radiationMultiplier;
        }
    }
}
