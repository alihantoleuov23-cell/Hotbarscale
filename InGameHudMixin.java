package com.alihan.hotbarscale.mixin;

import com.alihan.hotbarscale.HotbarScaleClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void hotbarScale$begin(DrawContext context, float tickDelta, CallbackInfo ci) {
        float scale = HotbarScaleClient.getScale();
        if (scale == 1.0f) return;
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        context.getMatrices().push();
        context.getMatrices().translate(width / 2.0f, height - 22.0f * scale, 0);
        context.getMatrices().scale(scale, scale, 1.0f);
        context.getMatrices().translate(-width / 2.0f, -(height - 22.0f * scale) / scale, 0);
    }

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void hotbarScale$end(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (HotbarScaleClient.getScale() != 1.0f) context.getMatrices().pop();
    }
}
