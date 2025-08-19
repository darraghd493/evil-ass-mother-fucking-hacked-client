package me.darragh.eamfhc.mixins;

import me.darragh.eamfhc.event.impl.player.EventPlayerPostTick;
import me.darragh.eamfhc.event.impl.player.EventPlayerPreTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin used to hook into player actions.
 *
 * @author darraghd493
 */
@SuppressWarnings("SpellCheckingInspection")
@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Shadow @Final protected Minecraft minecraft;

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/AbstractClientPlayer;tick()V"
            )
    )
    private void injectPreTick(CallbackInfo ci) {
        if ((Object) this != minecraft.player) return;
        new EventPlayerPreTick().post();
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void injectPostTick(CallbackInfo ci) {
        if ((Object) this != minecraft.player) return;
        new EventPlayerPostTick().post();
    }
}
