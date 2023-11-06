package me.voidxwalker.autoreset.mixin;

import net.minecraft.client.util.NetworkUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NetworkUtils.class)
public abstract class NetworkUtilsMixin {
    /**
     * @author DuncanRuns
     * @reason force port
     */
    @Overwrite
    public static int findLocalPort() {
        return 25565;
    }
}
