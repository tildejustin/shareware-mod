package me.voidxwalker.autoreset.mixin;

import me.voidxwalker.autoreset.interfaces.IPauseMenuScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.menu.PauseMenuScreen;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    public void openPauseMenu(boolean hideMenu) {
        if (this.client.currentScreen == null) {
            PauseMenuScreen screen = new PauseMenuScreen();
            if (hideMenu) ((IPauseMenuScreen) screen).hideMenu();
            this.client.openScreen(screen);
            if (this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote()) {
                this.client.getSoundManager().pauseAll();
            }
        }
    }


    @Redirect(
            method = "onKey",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openPauseMenu()V")
    )
    private void openGameMenuWithPause(MinecraftClient instance) {
        boolean hide = InputUtil.isKeyPressed(this.client.window.getHandle(), 292);
        this.openPauseMenu(hide);
    }
}
