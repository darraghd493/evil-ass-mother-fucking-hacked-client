package me.darragh.eamfhc.mixins;

import me.darragh.eamfhc.event.impl.player.EventPlayerTick;
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
    private void injectPlayerTick(CallbackInfo ci) {
        if ((Object) this != minecraft.player) return;
        new EventPlayerTick().post();
    }
}
