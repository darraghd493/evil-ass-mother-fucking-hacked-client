package me.darragh.eamfhc;

import net.minecraft.client.Minecraft;

/**
 * An interface that provides access to the Minecraft instance and other game-related functionalities.
 *
 * @author darraghd493
 */
public interface GameInstance {
    Minecraft minecraft = Minecraft.getInstance();
}
