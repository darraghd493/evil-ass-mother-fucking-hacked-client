package me.darragh.eamfhc.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.GameInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

/**
 * A utility class for chat-related functionalities.
 *
 * @author darraghd493
 */
@UtilityClass
public class ChatUtil implements GameInstance {
    private static final Component MOD_PREFIX = generateGradientComponent(
            "EAMFHC",
            0x154D71,
            0x154D71
    );

    public static void printRawMessage(Component component) {
        if (minecraft.player == null || component == null) {
            return;
        }
        minecraft.player.sendSystemMessage(component);
    }

    public static void printMessage(String message) {
        MutableComponent component = MOD_PREFIX.copy();
        component.append(message);
        printRawMessage(component);
    }

    public static MutableComponent generateGradientComponent(String message, int startColor, int endColor) {
        int length = message.length();
        MutableComponent component = Component.literal("");

        for (int i = 0; i < length; i++) {
            char c = message.charAt(i);
            int r = ((startColor >> 16 & 0xFF) * (length - i) + (endColor >> 16 & 0xFF) * i) / length,
                    g = ((startColor >> 8 & 0xFF) * (length - i) + (endColor >> 8 & 0xFF) * i) / length,
                    b = ((startColor & 0xFF) * (length - i) + (endColor & 0xFF) * i) / length;

            TextColor color = TextColor.fromRgb((r << 16) | (g << 8) | b);
            component.append(
                    Component.literal(String.valueOf(c))
                            .withStyle(Style.EMPTY.withColor(color))
            );
        }

        return component;
    }
}
