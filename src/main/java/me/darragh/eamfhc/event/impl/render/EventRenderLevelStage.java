package me.darragh.eamfhc.event.impl.render;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.ClientEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

/**
 * Called when world rendering should occur.
 *
 * @see me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor
 * @see net.minecraftforge.client.event.RenderLevelStageEvent
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public class EventRenderLevelStage extends ClientEvent {
    private final RenderLevelStageEvent.Stage stage;
    private final LevelRenderer levelRenderer;
    private final PoseStack poseStack;
    private final Matrix4f projectionMatrix;
    private final int renderTick;
    private final float partialTick;
    private final Camera camera;
    private final Frustum frustum;
}
