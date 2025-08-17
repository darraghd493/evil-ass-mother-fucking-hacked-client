package me.darragh.eamfhc.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

/**
 * Handles the initialisation and rendering of ImGui UIs.
 *
 * @author darraghd493
 */
public class ImGuiHandler {
    private final static ImGuiImplGlfw imGuiImplGlfw = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 imGuiImplGl3 = new ImGuiImplGl3();

    /**
     * Creates the ImGui context and initializes the GLFW and OpenGL bindings.
     * This method should be called once at the start of the mod, typically in the main thread.
     *
     * @param handle The GLFW window handle.
     */
    public static void create(long handle) {
        ImGui.createContext();
        ImPlot.createContext();

        ImGuiIO data = ImGui.getIO();
        data.setWantSaveIniSettings(false);
        data.setFontGlobalScale(1.5F);
        data.setConfigFlags(ImGuiConfigFlags.DockingEnable);

        imGuiImplGlfw.init(handle, true);
        imGuiImplGl3.init();
    }

    /**
     * Draws the ImGui UI.
     *
     * @param renderer The functional interface that contains the rendering logic for the ImGui UI.
     */
    public static void draw(ImGuiRenderer renderer) {
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
        renderer.render(ImGui.getIO());
        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            long pointer = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(pointer);
        }
    }
}