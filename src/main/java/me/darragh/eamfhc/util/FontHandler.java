package me.darragh.eamfhc.util;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.GameInstance;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

/**
 * Simplifies handling fonts in the mod.
 *
 * @author darraghd493
 */
@UtilityClass
public class FontHandler implements GameInstance {
    private static Font font;
    private static int fontHashCode = -1, fontHeight = -1;

    public static int draw(@NotNull String text, float x, float y, int colour, boolean dropShadow, GuiGraphics guiGraphics, PoseStack pose, MultiBufferSource.BufferSource bufferSource) {
        assertFont();
        int i = font.drawInBatch(text, x, y, colour, dropShadow, pose.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880, font.isBidirectional());
        //noinspection deprecation
        guiGraphics.flushIfUnmanaged();
        return i;
    }

    public static int getHeight() {
        assertFont();
        return fontHeight;
    }

    private static void assertFont() {
        if (font == null) {
            font = minecraft.font;
            fontHashCode = font.hashCode();
            fontHeight = font.lineHeight;
        }
        if (fontHashCode != minecraft.font.hashCode()) {
            font = minecraft.font;
            fontHashCode = font.hashCode();
            fontHeight = font.lineHeight;
        }
        font = minecraft.font;
    }
}
