package me.darragh.eamfhc.processor.impl.forge;

import me.darragh.eamfhc.event.impl.game.EventLevelLoad;
import me.darragh.eamfhc.event.impl.game.EventLevelUnload;
import me.darragh.eamfhc.event.impl.input.EventKeyInput;
import me.darragh.eamfhc.event.impl.input.EventMouseInput;
import me.darragh.eamfhc.event.impl.render.EventRenderLevelStage;
import me.darragh.eamfhc.event.impl.render.EventRenderOverlay;
import me.darragh.eamfhc.processor.Processor;
import me.darragh.eamfhc.processor.ProcessorIdentifier;
import me.darragh.eamfhc.processor.ProcessorType;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * A processor that wraps Forge events to allow for easier handling and processing.
 * This processor is used to manage and manipulate events within the Forge modding framework.
 * It provides a structured way to handle events, making it easier for developers to work with them.
 *
 * @author darraghd493
 */
@ProcessorIdentifier(
        identifier = "event-wrapper",
        displayName = "Event Wrapper",
        description = "Wraps Forge events to allow for easier handling and processing.",
        type = ProcessorType.FORGE
)
public class EventWrapperProcessor extends Processor {
    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onMouseButtonInputEvent(InputEvent.MouseButton.Pre event) {
        EventMouseInput clientEvent = new EventMouseInput(
                event.getButton(),
                event.getAction(),
                event.getModifiers()
        ).post();
        if (clientEvent.isCancelled()) {
            event.setCanceled(true);
        }
    }

//    @SubscribeEvent
//    public void onKeyInputEvent(InputEvent.Key event) { // TODO: Shift to earlier handling - maybe use mixins?
//        new EventKeyInput(
//                event.getKey(),
//                event.getScanCode(),
//                event.getAction(),
//                event.getModifiers()
//        ).post();
//    }

    @SubscribeEvent
    public void onLevelUnload(LevelEvent.Load event) {
        new EventLevelLoad().post();
    }

    @SubscribeEvent
    public void onLevelUnload(LevelEvent.Unload event) {
        new EventLevelUnload().post();
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        new EventRenderOverlay(
                event.getWindow(),
                event.getGuiGraphics(),
                event.getPartialTick(),
                event.getOverlay()
        ).post();
    }

    @SubscribeEvent
    public void onRenderLevelLast(RenderLevelStageEvent event) {
        new EventRenderLevelStage(
                event.getStage(),
                event.getLevelRenderer(),
                event.getPoseStack(),
                event.getProjectionMatrix(),
                event.getRenderTick(),
                event.getPartialTick(),
                event.getCamera(),
                event.getFrustum()
        ).post();
    }
}
