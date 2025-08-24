package me.darragh.eamfhc.mixins.scgun;

import me.darragh.eamfhc.module.impl.combat.AntiRecoilModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ribs.scguns.client.handler.RecoilHandler;

@Mixin(value = RecoilHandler.class, remap = false)
public class MixinRecoilHandler {
    @Shadow
    private boolean enableRecoil;

    @Inject(
            method = "onRenderTick",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeRecoil(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            this.enableRecoil = false;
            ci.cancel();
        }
    }
}