package me.darragh.eamfhc;

import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static me.darragh.eamfhc.EntryPoint.MOD_ID;

/**
 * The entry-point for the client.
 *
 * @author darraghd493
 */
@Mod(MOD_ID)
@Slf4j
public class EntryPoint {
    public static final String MOD_ID = "nothacks";

    public EntryPoint() {
        log.info("EAMFHC's entry-point is being initialised...");

        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            log.error("EAMFHC is running on a dedicated server! This is not supported.");
            throw new IllegalStateException("EAMFHC is not supported on dedicated servers.");
        }

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, EntryPoint::setupClient);
    }

    private static Runnable setupClient() {
        //noinspection removal
        return () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(
                (FMLClientSetupEvent event /* must be type specific */) -> Client.INSTANCE.init()
        );
    }
}
