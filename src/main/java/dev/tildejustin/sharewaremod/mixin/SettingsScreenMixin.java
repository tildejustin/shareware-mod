package dev.tildejustin.sharewaremod.mixin;

import dev.tildejustin.sharewaremod.Atum;
import net.minecraft.client.gui.CloseWorldScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.TitleScreen;
import net.minecraft.client.gui.menu.SettingsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public class SettingsScreenMixin extends Screen {
    protected SettingsScreenMixin(TextComponent textComponent) {
        super(textComponent);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void addAutoResetButton(CallbackInfo ci) {
        if (Atum.running) {
            this.addButton(new ButtonWidget(0, this.height - 20, 100, 20, "Stop Resets & Quit", (buttonWidget) -> {
                Atum.running = false;
                this.minecraft.world.disconnect();
                this.minecraft.method_18096(new CloseWorldScreen(new TranslatableTextComponent("menu.savingLevel")));
                this.minecraft.openScreen(new TitleScreen());
            }));
        }
    }
}
