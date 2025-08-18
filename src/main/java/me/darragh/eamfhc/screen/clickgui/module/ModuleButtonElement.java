package me.darragh.eamfhc.screen.clickgui.module;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.screen.clickgui.Element;
import me.darragh.eamfhc.screen.clickgui.property.PropertyElement;
import me.darragh.eamfhc.util.ColourUtil;
import me.darragh.eamfhc.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A button element for a module in the ClickGui.
 * <p>
 * Left-clicking on this will toggle the module's enabled state.
 * Right-clicking on this will expose the module's properties.
 * Middle-clicking on this will toggle binding the module.
 *
 * @author darraghd493
 */
class ModuleButtonElement extends Element {
    protected static final int HEIGHT = 16;

    protected static final int BACKGROUND_COLOUR = ColourUtil.getInt(16, 16, 16);
    protected static final int BORDER_COLOUR = ColourUtil.getInt(32, 32, 32);

    private final List<Element> elements = new ObjectArrayList<>();

    private final Module module;
    private boolean binding, expanded;

    ModuleButtonElement(Module module) {
        this.module = module;

        // Generate children
        this.elements.addAll(
                PropertyElement.create(module.getPropertyManager())
        );
    }

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        boolean hovered = RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, HEIGHT);
        guiGraphics.fill(
                this.x, this.y,
                this.x + this.width, this.y + HEIGHT,
                BACKGROUND_COLOUR
        );
        guiGraphics.drawString(
                minecraft.font,
                this.binding ? "Binding..." : this.module.getMetadata().getDisplayName(),
                this.x + 4, this.y + (HEIGHT - 1 - minecraft.font.lineHeight) / 2,
                    this.module.isEnabled() ? 0xFFFFFF : hovered ? 0xAAAAAA : 0x888888
        );
        if (!this.elements.isEmpty()) {
            guiGraphics.drawString(
                    minecraft.font,
                    this.expanded ? "-" : "+",
                    this.x + this.width - 4 - minecraft.font.width(this.expanded ? "-" : "+"), this.y + (HEIGHT - 1 - minecraft.font.lineHeight) / 2,
                    0x888888
            );
        }
        guiGraphics.fill(
                this.x, this.y + HEIGHT - 1,
                this.x + this.width, this.y + HEIGHT,
                BORDER_COLOUR
        );
        if (!this.expanded) return HEIGHT;
        int currentY = this.y + HEIGHT,
                height = HEIGHT;
        for (Element element : this.elements) {
            element.setX(this.x);
            element.setY(currentY);
            element.setWidth(this.width);
            int elementHeight = element.draw(screen, guiGraphics, container, mouseX, mouseY, partialTick);
            currentY += elementHeight;
            height += elementHeight;
        }
        return height;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, HEIGHT)) {
            switch (button) {
                case 0 -> this.module.toggle();
                case 1 -> this.expanded = !this.expanded;
                case 2 -> this.binding = !this.binding;
            }
            return true;
        }
        this.binding = false;
        for (Element element : this.elements) {
            if (element.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, HEIGHT)) {
            return true;
        }
        if (this.binding) {
            return true;
        }
        for (Element element : this.elements) {
            if (element.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (this.binding) {
            return true;
        }
        for (Element element : this.elements) {
            if (element.keyReleased(keyCode, scanCode, modifiers)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.binding) {
            switch (keyCode) {
                case InputConstants.KEY_ESCAPE -> {
                    this.binding = false;
                    return true;
                }
                case InputConstants.KEY_BACKSPACE -> {
                    this.module.getBind().setKeyCode(InputConstants.UNKNOWN.getValue());
                    this.binding = false;
                    return true;
                }
                default -> {
                    if (InputConstants.isKeyDown(minecraft.getWindow().getWindow(), keyCode)) { // double-check
                        this.module.getBind().setKeyCode(keyCode);
                        this.binding = false;
                        return true;
                    }
                }
            }
            return true;
        }
        for (Element element : this.elements) {
            if (element.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }
        }
        return false;
    }
}
