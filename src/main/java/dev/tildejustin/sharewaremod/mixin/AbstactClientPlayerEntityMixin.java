package dev.tildejustin.sharewaremod.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstactClientPlayerEntityMixin {
    @Inject(method = "method_3118", at = @At("HEAD"), cancellable = true)
    private void stopFovUpdate(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0f);
    }
}
