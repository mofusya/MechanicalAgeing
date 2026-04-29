package net.mofusya.mechanical_ageing.jei.ingredient;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.common.util.ErrorUtil;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.jei.MAgIngredient;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import org.jetbrains.annotations.Nullable;

public class MatterHelper implements IIngredientHelper<MatterStack> {
    @Override
    public String getDisplayName(MatterStack matterStack) {
        var type = matterStack.getType();
        ErrorUtil.checkNotNull(type, "matterStack");
        ErrorUtil.checkNotNull(type.getTranslationId(), "matterStack.getTranslationId()");
        return type.getTranslationId();
    }

    @Override
    public IIngredientType<MatterStack> getIngredientType() {
        return MAgIngredient.MATTER_TYPE;
    }

    @Override
    public String getUniqueId(MatterStack matterStack, UidContext uidContext) {
        return "matter: " + this.getDisplayName(matterStack);
    }

    @Override
    public ResourceLocation getResourceLocation(MatterStack matterStack) {
        var type = matterStack.getType();
        ErrorUtil.checkNotNull(type, "matterStack");
        return type.getId();
    }

    @Override
    public MatterStack copyIngredient(MatterStack matterStack) {
        return matterStack.copy();
    }

    @Override
    public String getErrorInfo(@Nullable MatterStack matterStack) {
        if (matterStack == null){
            matterStack = MatterStack.empty();
        }
        return "A error happened is getErrorInfo() with: " + matterStack;
    }
}
