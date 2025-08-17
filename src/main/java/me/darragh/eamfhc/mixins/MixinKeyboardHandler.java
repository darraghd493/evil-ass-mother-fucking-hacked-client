package me.darragh.eamfhc.mixins;

import me.darragh.eamfhc.event.impl.input.EventKeyInput;
import net.minecraft.client.KeyboardHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin used to intercept key presses.
 *
 * @author darraghd493
 */
@Mixin(KeyboardHandler.class)
public class MixinKeyboardHandler {
    @SuppressWarnings("DiscouragedShift")
    @Inject(
            method = "keyPress",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;",
                    opcode = Opcodes.GETFIELD,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void injectBeforeNarrator(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        EventKeyInput event = new EventKeyInput(
                key,
                scancode,
                action,
                modifiers
        ).post();
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
