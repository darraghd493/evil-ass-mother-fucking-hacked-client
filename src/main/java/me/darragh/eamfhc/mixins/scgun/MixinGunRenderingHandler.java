package me.darragh.eamfhc.mixins.scgun;

import me.darragh.eamfhc.module.impl.combat.AntiRecoilModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ribs.scguns.client.handler.GunRenderingHandler;

@Mixin(value = GunRenderingHandler.class, remap = false)
public class MixinGunRenderingHandler {
    @Inject(
            method = "applyAimingTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeAimingTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applySwayTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeSwayTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applySprintingTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeSprintingTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applyRecoilTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeRecoilTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applyReloadTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeReloadTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applyShieldTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeShieldTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }

    @Inject(
            method = "applyMeleeTransforms",
            at = @At("HEAD"),
            cancellable = true
    )
    public void removeMeleeTransforms(CallbackInfo ci) {
        if (AntiRecoilModule.active) {
            ci.cancel();
        }
    }
}
