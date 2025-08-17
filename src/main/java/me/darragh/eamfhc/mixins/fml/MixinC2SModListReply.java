package me.darragh.eamfhc.mixins.fml;

import me.darragh.eamfhc.EntryPoint;
import net.minecraftforge.network.HandshakeMessages;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin used to intercept the mod list reply message and remove the used mod ID from it.
 *
 * @author darraghd493
 */
@Mixin(HandshakeMessages.C2SModListReply.class)
public class MixinC2SModListReply {
    @Shadow
    private List<String> mods;

    @Inject(
            method = "<init>*",
            at = @At("RETURN")
    )
    public void init(CallbackInfo ci) {
        this.mods.remove(EntryPoint.MOD_ID);
    }
}
