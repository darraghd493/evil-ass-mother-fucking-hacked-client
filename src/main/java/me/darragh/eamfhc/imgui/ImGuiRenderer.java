package me.darragh.eamfhc.imgui;

import imgui.ImGuiIO;

/**
 * A functional interface for rendering ImGui UIs.
 *
 * @author darraghd493
 */
@FunctionalInterface
public interface ImGuiRenderer {
    /**
     * Renders the ImGui UI.
     *
     * @param io The ImGui IO object that contains input and configuration data.
     */
    void render(ImGuiIO io);
}