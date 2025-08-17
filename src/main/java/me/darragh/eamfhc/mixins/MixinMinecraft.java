package me.darragh.eamfhc.mixins;

import com.mojang.blaze3d.platform.Window;
import me.darragh.eamfhc.imgui.ImGuiHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin used to initialise the ImGui context.
 *
 * @author darraghd493
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    @Final
    private Window window;

    @Inject(
            method = "<init>*",
            at = @At("RETURN")
    )
    public void init(CallbackInfo ci) {
        ImGuiHandler.create(window.getWindow());
    }
}
