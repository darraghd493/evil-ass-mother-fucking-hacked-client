package me.darragh.eamfhc;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.darragh.eamfhc.event.ClientEvent;
import me.darragh.eamfhc.event.impl.game.EventLevelLoad;
import me.darragh.eamfhc.event.impl.render.EventRenderOverlay;
import me.darragh.eamfhc.feature.FeatureRepositoryService;
import me.darragh.eamfhc.feature.SimpleFeatureRepositoryService;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleMetadata;
import me.darragh.eamfhc.module.impl.render.Watermark;
import me.darragh.eamfhc.processor.Processor;
import me.darragh.eamfhc.processor.ProcessorMetadata;
import me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor;
import me.darragh.eamfhc.util.FontHandler;
import me.darragh.event.bus.EventDispatcher;
import me.darragh.event.bus.Listener;
import me.darragh.event.bus.SimpleEventDispatcher;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * The main client class for EAMFHC.
 *
 * @author darraghd493
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Slf4j
public class Client implements GameInstance, Initialisable, Destroyable {
    public static final Client INSTANCE = new Client();

    public static final String MOD_NAME = "Evil Ass Mother Fucking Client";
    public static final String MOD_ABBREVIATED_NAME = "EAMFClient";

    @Getter
    private final EventDispatcher<ClientEvent> eventDispatcher = new SimpleEventDispatcher<>();

    private @Nullable FeatureRepositoryService<Processor, ProcessorMetadata> processorRepositoryService;
    private @Nullable FeatureRepositoryService<Module, ModuleMetadata> moduleRepositoryService;

    @Override
    public void init() {
        log.info("{} ({}) is loading...", MOD_ABBREVIATED_NAME, MOD_NAME);

        // Create the processor repository
        this.processorRepositoryService = SimpleFeatureRepositoryService.create(
                "processor-repository",
                service -> {
                    log.info("Initialising processor repository...");
                    // Register all processors
                    // Note: reflection failed to work in development - gist w/ ReflectionUtil: https://gist.github.com/darraghd493/f120ee5c02e1a86d5bb0205fb63875e9
                    service.registerFeature(new EventWrapperProcessor());

                    // Initialise all processors
                    service.getFeatures().forEach(Processor::init);
                }
        );

        // Create the module repository
        this.moduleRepositoryService = SimpleFeatureRepositoryService.create(
                "module-repository",
                service -> {
                    log.info("Initialising module repository...");
                    // Register all modules
                    // Note: reflection failed to work in development - gist w/ ReflectionUtil: https://gist.github.com/darraghd493/f120ee5c02e1a86d5bb0205fb63875e9
                    service.registerFeature(new Watermark());

                    // Initialise all modules
                    service.getFeatures().forEach(Module::init);
                }
        );

        // Register the client to the event bus for debugging
        // TODO: Remove in production
        this.eventDispatcher.register(this);
    }

    @Override
    public void destroy() {
        // TODO: Hook and implement
    }

    // TODO: Remove in production
    //region Debug
    @Listener
    public void onRenderOverlay(EventRenderOverlay event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        FontHandler.draw("evil ass mother fucking hacked client", 2.0F, 2.0F, Color.PINK.getRGB(), true, guiGraphics, guiGraphics.pose(), guiGraphics.bufferSource());
        FontHandler.draw("1.20.1 Forge", 2.0F, 2.0F + FontHandler.getHeight(), Color.GRAY.getRGB(), true, guiGraphics, guiGraphics.pose(), guiGraphics.bufferSource());
    }
    //endregion
}
