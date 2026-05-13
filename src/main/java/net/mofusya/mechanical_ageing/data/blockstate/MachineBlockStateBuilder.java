package net.mofusya.mechanical_ageing.data.blockstate;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.util.annotations.FieldsMayBeNullByDefault;
import net.mofusya.mechanical_ageing.util.annotations.ParametersAreNonNullByDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonNullByDefault
@FieldsMayBeNullByDefault
public class MachineBlockStateBuilder {
    @NotNull
    private final MachineBlock block;
    @NotNull
    private final ResourceLocation blockLoc;

    private ResourceLocation topBaseTexture = null;
    private ResourceLocation sideBaseTexture = null;
    private ResourceLocation frontBaseTexture = null;
    private ResourceLocation bottomBaseTexture = null;
    private ResourceLocation topCoverTexture = null;
    private ResourceLocation sideCoverTexture = null;
    private ResourceLocation frontCoverTexture = null;
    private ResourceLocation bottomCoverTexture = null;
    private int frameColor = 0xFFFFFF;
    private int upperCrystalColor = -404;
    private int lowerCrystalColor = -404;
    private int sideColor = 0xFFFFFF;
    private boolean hasFacing = true;

    MachineBlockStateBuilder(MachineObject machine) {
        this.block = (MachineBlock) machine.block();

        var blockLoc = ForgeRegistries.BLOCKS.getKey(machine.block());
        if (blockLoc == null) throw new IllegalArgumentException("Illegal machine block");

        this.blockLoc = blockLoc;
    }

    public MachineBlockStateBuilder baseTextureFromLoc(boolean suffix) {
        return this.baseTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder baseTexture(String path, boolean suffix) {
        return this.baseTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder baseTexture(String namespace, String path, boolean suffix) {
        return this.baseTexture(new ResourceLocation(namespace, path), suffix);
    }

    public MachineBlockStateBuilder baseTexture(@Nullable ResourceLocation baseTexture, boolean suffix) {
        if (baseTexture == null) {
            this.topBaseTexture = null;
            this.sideBaseTexture = null;
            this.frontBaseTexture = null;
            this.bottomBaseTexture = null;
        } else {
            if (suffix) {
                this.topBaseTexture = baseTexture.withSuffix("_top");
                this.sideBaseTexture = baseTexture.withSuffix("_side");
                this.frontBaseTexture = baseTexture.withSuffix("_front");
                this.bottomBaseTexture = baseTexture.withSuffix("_bottom");
            } else {
                this.topBaseTexture = baseTexture;
                this.sideBaseTexture = baseTexture;
                this.frontBaseTexture = baseTexture;
                this.bottomBaseTexture = baseTexture;
            }
        }
        return this;
    }

    public MachineBlockStateBuilder topBaseTextureFromLoc(boolean suffix) {
        return this.topBaseTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder topBaseTexture(String path, boolean suffix) {
        return this.topBaseTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder topBaseTexture(String namespace, String path, boolean suffix) {
        return this.topBaseTexture(new ResourceLocation(namespace, path), suffix);
    }

    public MachineBlockStateBuilder topBaseTexture(@Nullable ResourceLocation topBaseTexture, boolean suffix) {
        this.topBaseTexture = topBaseTexture == null ? null : (suffix ? topBaseTexture.withSuffix("_top") : topBaseTexture);
        return this;
    }

    public MachineBlockStateBuilder sideBaseTextureFromLoc(boolean suffix) {
        return this.sideBaseTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder sideBaseTexture(String path, boolean suffix) {
        return this.sideBaseTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder sideBaseTexture(String namespace, String path, boolean suffix) {
        return this.sideBaseTexture(new ResourceLocation(namespace, path), suffix);
    }

    public MachineBlockStateBuilder sideBaseTexture(@Nullable ResourceLocation sideBaseTexture, boolean suffix) {
        this.sideBaseTexture = sideBaseTexture == null ? null : (suffix ? sideBaseTexture.withSuffix("_side") : sideBaseTexture);
        return this;
    }

    public MachineBlockStateBuilder frontBaseTextureFromLoc(boolean suffix) {
        return this.frontBaseTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder frontBaseTexture(String path, boolean suffix) {
        return this.frontBaseTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder frontBaseTexture(String namespace, String path, boolean suffix) {
        return this.frontBaseTexture(new ResourceLocation(namespace, path), suffix);
    }

    public MachineBlockStateBuilder frontBaseTexture(@Nullable ResourceLocation frontBaseTexture, boolean suffix) {
        this.frontBaseTexture = frontBaseTexture == null ? null : (suffix ? frontBaseTexture.withSuffix("_front") : frontBaseTexture);
        return this;
    }

    public MachineBlockStateBuilder bottomBaseTextureFromLoc(boolean suffix) {
        return this.bottomBaseTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder bottomBaseTexture(String path, boolean suffix) {
        return this.bottomBaseTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder bottomBaseTexture(String namespace, String path, boolean suffix) {
        return this.bottomBaseTexture(new ResourceLocation(namespace, path), suffix);
    }

    public MachineBlockStateBuilder bottomBaseTexture(@Nullable ResourceLocation bottomBaseTexture, boolean suffix) {
        this.bottomBaseTexture = bottomBaseTexture == null ? null : (suffix ? bottomBaseTexture.withSuffix("_bottom") : bottomBaseTexture);
        return this;
    }

    public MachineBlockStateBuilder coverTextureFromLoc(boolean suffix) {
        return this.coverTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder coverTexture(String path, boolean suffix) {
        return this.coverTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder coverTexture(String nameSpace, String path, boolean suffix) {
        return this.coverTexture(new ResourceLocation(nameSpace, path), suffix);
    }

    public MachineBlockStateBuilder coverTexture(@Nullable ResourceLocation coverTexture, boolean suffix) {
        if (coverTexture == null){
            this.topCoverTexture = null;
            this.sideCoverTexture = null;
            this.frontCoverTexture = null;
            this.bottomCoverTexture = null;
        } else {
            if (suffix){
                this.topCoverTexture = coverTexture.withSuffix("_top");
                this.sideCoverTexture = coverTexture.withSuffix("_side");
                this.frontCoverTexture = coverTexture.withSuffix("_front");
                this.bottomCoverTexture = coverTexture.withSuffix("_bottom");
            } else {
                this.topCoverTexture = coverTexture;
                this.sideCoverTexture = coverTexture;
                this.frontCoverTexture = coverTexture;
                this.bottomCoverTexture = coverTexture;
            }
        }
        return this;
    }

    public MachineBlockStateBuilder topCoverTextureFromLoc(boolean suffix) {
        return this.topCoverTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder topCoverTexture(String path, boolean suffix) {
        return this.topCoverTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder topCoverTexture(String nameSpace, String path, boolean suffix) {
        return this.topCoverTexture(new ResourceLocation(nameSpace, path), suffix);
    }

    public MachineBlockStateBuilder topCoverTexture(@Nullable ResourceLocation topCoverTexture, boolean suffix) {
        this.topCoverTexture = topCoverTexture == null ? null : (suffix ? topCoverTexture.withSuffix("_top") : topCoverTexture);
        return this;
    }

    public MachineBlockStateBuilder sideCoverTextureFromLoc(boolean suffix) {
        return this.sideCoverTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder sideCoverTexture(String path, boolean suffix) {
        return this.sideCoverTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder sideCoverTexture(String nameSpace, String path, boolean suffix) {
        return this.sideCoverTexture(new ResourceLocation(nameSpace, path), suffix);
    }

    public MachineBlockStateBuilder sideCoverTexture(@Nullable ResourceLocation sideCoverTexture, boolean suffix) {
        this.sideCoverTexture = sideCoverTexture == null ? null : (suffix ? sideCoverTexture.withSuffix("_top") : sideCoverTexture);
        return this;
    }

    public MachineBlockStateBuilder frontCoverTextureFromLoc(boolean suffix) {
        return this.frontCoverTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder frontCoverTexture(String path, boolean suffix) {
        return this.frontCoverTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder frontCoverTexture(String nameSpace, String path, boolean suffix) {
        return this.frontCoverTexture(new ResourceLocation(nameSpace, path), suffix);
    }

    public MachineBlockStateBuilder frontCoverTexture(@Nullable ResourceLocation frontCoverTexture, boolean suffix) {
        this.frontCoverTexture = frontCoverTexture == null ? null : (suffix ? frontCoverTexture.withSuffix("_top") : frontCoverTexture);
        return this;
    }

    public MachineBlockStateBuilder bottomCoverTextureFromLoc(boolean suffix) {
        return this.bottomCoverTexture(this.getBlockLoc(), suffix);
    }

    public MachineBlockStateBuilder bottomCoverTexture(String path, boolean suffix) {
        return this.bottomCoverTexture(new ResourceLocation(MAg.MOD_ID, path), suffix);
    }

    public MachineBlockStateBuilder bottomCoverTexture(String nameSpace, String path, boolean suffix) {
        return this.bottomCoverTexture(new ResourceLocation(nameSpace, path), suffix);
    }

    public MachineBlockStateBuilder bottomCoverTexture(@Nullable ResourceLocation bottomCoverTexture, boolean suffix) {
        this.bottomCoverTexture = bottomCoverTexture == null ? null : (suffix ? bottomCoverTexture.withSuffix("_top") : bottomCoverTexture);
        return this;
    }

    public MachineBlockStateBuilder frameColor(int frameColor) {
        this.frameColor = frameColor;
        return this;
    }

    public MachineBlockStateBuilder upperCrystalColor(int upperCrystalColor) {
        this.upperCrystalColor = upperCrystalColor;
        return this;
    }

    public MachineBlockStateBuilder lowerCrystalColor(int lowerCrystalColor) {
        this.lowerCrystalColor = lowerCrystalColor;
        return this;
    }

    public MachineBlockStateBuilder sideColor(int sideColor) {
        this.sideColor = sideColor;
        return this;
    }

    public MachineBlockStateBuilder hasFacing(boolean hasFacing){
        this.hasFacing = hasFacing;
        return this;
    }

    //Getters
    @NotNull
    public MachineBlock getBlock() {
        return this.block;
    }

    @NotNull
    public ResourceLocation getBlockLoc() {
        return this.blockLoc;
    }

    @NotNull
    public String getName(){
        return this.getBlockLoc().getPath();
    }

    @Nullable
    public ResourceLocation getBottomBaseTexture() {
        return this.bottomBaseTexture;
    }

    @Nullable
    public ResourceLocation getFrontBaseTexture() {
        return this.frontBaseTexture;
    }

    @Nullable
    public ResourceLocation getSideBaseTexture() {
        return this.sideBaseTexture;
    }

    @Nullable
    public ResourceLocation getTopBaseTexture() {
        return this.topBaseTexture;
    }

    @Nullable
    public ResourceLocation getBottomCoverTexture() {
        return this.bottomCoverTexture;
    }

    @Nullable
    public ResourceLocation getFrontCoverTexture() {
        return this.frontCoverTexture;
    }

    @Nullable
    public ResourceLocation getSideCoverTexture() {
        return this.sideCoverTexture;
    }

    @Nullable
    public ResourceLocation getTopCoverTexture() {
        return this.topCoverTexture;
    }

    public int getFrameColor() {
        return this.frameColor;
    }

    public int getLowerCrystalColor() {
        return this.lowerCrystalColor;
    }

    public int getSideColor() {
        return this.sideColor;
    }

    public int getUpperCrystalColor() {
        return this.upperCrystalColor;
    }

    public boolean hasFacing() {
        return this.hasFacing;
    }
}