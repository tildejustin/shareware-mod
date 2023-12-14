package dev.tildejustin.sharewaremod.mixin;

import dev.tildejustin.sharewaremod.Atum;
import dev.tildejustin.sharewaremod.screen.AtumOptionsScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.TitleScreen;
import net.minecraft.client.gui.menu.DifficultyScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    private static final Identifier BUTTON_IMAGE = new Identifier("textures/item/golden_boots.png");

    protected TitleScreenMixin(TextComponent textComponent) {
        super(textComponent);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo info) {
        this.minecraft.getLevelStorage().deleteLevel("shareware");
        if (Atum.running) {
            minecraft.openScreen(new DifficultyScreen());
        } else {
            this.addButton(new ButtonWidget(this.width / 2 - 124, this.height / 4 + 48, 20, 20, "", (buttonWidget) -> {
                if (Screen.hasShiftDown()) {
                    this.minecraft.openScreen(new AtumOptionsScreen());
                } else {
                    Atum.running = true;
                    this.minecraft.openScreen(this);
                }
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlay(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.minecraft.getTextureManager().bindTexture(BUTTON_IMAGE);
        blit(this.width / 2 - 124 + 2, this.height / 4 + 48 + 2, 0.0F, 0.0F, 16, 16, 16, 16);
    }
}
