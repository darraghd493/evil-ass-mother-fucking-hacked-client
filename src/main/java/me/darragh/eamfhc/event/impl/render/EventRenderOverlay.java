package me.darragh.eamfhc.event.impl.render;

import com.mojang.blaze3d.platform.Window;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.ClientEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;

/**
 * Called when overlay (2D/HUD) rendering should occur.
 *
 * @see me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor
 * @see net.minecraftforge.client.event.RenderGuiOverlayEvent.Post
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public class EventRenderOverlay extends ClientEvent {
    private final Window window;
    private final GuiGraphics guiGraphics;
    private final float partialTick;
    private final NamedGuiOverlay overlay;
}
