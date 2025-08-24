package me.darragh.eamfhc.mixins.scgun;

import me.darragh.eamfhc.module.impl.combat.AntiRecoilModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.ribs.scguns.common.Gun;

@Mixin(value = Gun.General.class, remap = false)
public class MixinGunGeneral {
    @Inject(
            method = "hasCameraShake",
            at = @At("RETURN"),
            cancellable = true
    )
    public void hasCameraShake(CallbackInfoReturnable<Boolean> cir) {
        if (AntiRecoilModule.active) {
            cir.setReturnValue(false);
        }
    }
}
