package me.darragh.eamfhc.screen.clickgui;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.screen.clickgui.module.ModuleContainer;
import me.darragh.eamfhc.util.ColourUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Handles the rendering and interaction of the ClickGui screen.
 *
 * @author darraghd493
 */
public class ClickGuiScreen extends Screen {
    private final List<Container> containers = new ObjectArrayList<>();

    public ClickGuiScreen(Component title) {
        super(title);

        // Initialise module containers
        for (ModuleType moduleType : ModuleType.values()) {
            this.containers.add(new ModuleContainer(moduleType, 100 + (ModuleContainer.CONTAINER_WIDTH + 10) * this.containers.size(), 25));
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) { // TODO: Clean up - old Fentanyl code
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Draw the background
        guiGraphics.fill(0, 0, this.width, this.height, ColourUtil.getInt(0, 0, 0, 101));

        // Draw the containers
        for (Container container : this.containers) {
            container.draw(this, guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        for (Container container : this.containers) {
            if (container.mouseClicked((int) p_94695_, (int) p_94696_, p_94697_)) {
                return true;
            }
        }
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        for (Container container : this.containers) {
            if (container.mouseReleased((int) p_94722_, (int) p_94723_, p_94724_)) {
                return true;
            }
        }
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        for (Container container : this.containers) {
            if (container.keyPressed(p_96552_, p_96553_, p_96554_)) {
                return true;
            }
        }
        if (p_96552_ == InputConstants.KEY_ESCAPE) { // Escape key, we are doing this ourselves to avoid the default behaviour as it has been unreliable in the past(?)
            this.onClose();
            return true;
        }
        return super.keyPressed(p_96552_, p_96553_, p_96554_);
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        for (Container container : this.containers) {
            if (container.keyReleased(p_94715_, p_94716_, p_94717_)) {
                return true;
            }
        }
        return super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
