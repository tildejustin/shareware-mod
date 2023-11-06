package me.voidxwalker.autoreset.mixin;

import me.voidxwalker.autoreset.interfaces.IPauseMenuScreen;
import net.minecraft.client.gui.menu.PauseMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseMenuScreen.class)
public abstract class PauseMenuScreenMixin implements IPauseMenuScreen {
    @Unique
    private boolean showMenu = true;

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/menu/PauseMenuScreen;renderBackground()V"
            )
    )
    private void stopBackgroundRender(PauseMenuScreen instance) {
        if (showMenu) instance.renderBackground();
    }


    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/menu/PauseMenuScreen;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
            ), index = 3
    )
    private int modifyPauseTextHeight(int y) {
        if (!showMenu) return 23;
        return y;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void hideMenu() {
        showMenu = false;
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void stopButtonRender(CallbackInfo ci) {
        if (!showMenu) ci.cancel();
    }
}
