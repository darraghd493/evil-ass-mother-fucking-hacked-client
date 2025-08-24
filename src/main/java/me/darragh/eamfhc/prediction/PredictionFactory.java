package me.darragh.eamfhc.prediction;

import me.darragh.eamfhc.prediction.impl.SimplePredictionEngine;

/**
 * A factory for creating various prediction engines.
 * Provides static methods to instantiate different types of prediction engines based on specific requirements or configurations.
 *
 * @author darraghd493
 */
public class PredictionFactory {
    /**
     * Creates a simple prediction engine, modeled after vanilla 1.8 without any collision detection.
     *
     * @see SimplePredictionEngine
     *
     * @return A new instance of {@link SimplePredictionEngine}.
     */
    public static PredictionEngine createSimplePredictionEngine() {
        return new SimplePredictionEngine();
    }
}
