package me.darragh.eamfhc.screen.clickgui.module;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.util.ColourUtil;
import me.darragh.eamfhc.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A container for modules in the ClickGui.
 * <p>
 * Order of elements:
 * <pre>
 *     ModuleContainer -> ModuleButtonElement(s) -> PropertyElement(s)
 * </pre>
 *
 * @author darraghd493
 */
public class ModuleContainer extends Container {
    protected static final int BACKGROUND_COLOUR = ColourUtil.getInt(18, 18, 18);

    public static final int CONTAINER_WIDTH = 250;

    private final List<ModuleButtonElement> moduleButtonElements = new ObjectArrayList<>();
    private final ModuleType moduleType;

    private boolean dragging, expanded;
    private int dragX, dragY;

    public ModuleContainer(ModuleType moduleType, int x, int y) {
        super(x, y, CONTAINER_WIDTH, 16);
        this.moduleType = moduleType;

        // Generate the module button elements
        Set<Module> modules = Client.INSTANCE.getModuleRepositoryService().getFeatures()
                .stream()
                .filter(module -> module.getMetadata().getType() == moduleType)
                .collect(Collectors.toUnmodifiableSet());

        for (Module module : modules) {
            ModuleButtonElement moduleButtonElement = new ModuleButtonElement(module);
            this.moduleButtonElements.add(moduleButtonElement);
        }
    }

    @Override
    public void draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.dragging) { // Handle dragging early
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }

        guiGraphics.fill(
                this.x, this.y,
                this.x + this.width, this.y + this.height,
                BACKGROUND_COLOUR
        );
        guiGraphics.drawString(
                minecraft.font,
                this.moduleType.getDisplayName(),
                this.x + 4, this.y + (this.height - 1 - minecraft.font.lineHeight) / 2,
                -1
        );
        guiGraphics.fill(
                this.x, this.y + this.height - 1,
                this.x + this.width, this.y + this.height,
                this.moduleType.getColour()
        );

        if (!this.expanded) return;

        int currentY = this.y + this.height;
        for (ModuleButtonElement element : this.moduleButtonElements) {
            element.setX(this.x);
            element.setY(currentY);
            element.setWidth(this.width);
            currentY += element.draw(screen, guiGraphics, this, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        // Handle clicks on the container itself
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
            switch (button) {
                case 0 -> {
                    this.dragging = true;
                    this.dragX = mouseX - this.x;
                    this.dragY = mouseY - this.y;
                }
                case 1 -> this.expanded = !this.expanded;
            }
            return true;
        }
        // Pass the remaining clicks to the module button elements
        for (ModuleButtonElement element : this.moduleButtonElements) {
            if (element.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        // Ignore clicks on the container itself
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
            return false;
        }
        // Pass the remaining clicks to the module button elements
        for (ModuleButtonElement element : this.moduleButtonElements) {
            if (element.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (ModuleButtonElement element : this.moduleButtonElements) {
            if (element.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }
        }
        return false;
    }
}
