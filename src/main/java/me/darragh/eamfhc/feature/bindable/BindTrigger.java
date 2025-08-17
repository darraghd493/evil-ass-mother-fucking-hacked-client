package me.darragh.eamfhc.feature.bindable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An enum representing different triggers for binding actions.
 *
 * @author darraghd493
 */
@RequiredArgsConstructor
public enum BindTrigger implements me.darragh.eamfhc.Identifiable {
    PRESS("press", ((previous, current) -> !previous && current)),
    HOLD("hold", ((previous, current) -> previous != current));

    @Getter
    private final String identifier;
    private final BindInputEvaluator evaluator;

    public boolean evaluate(boolean previous, boolean current) {
        return this.evaluator.evaluate(previous, current);
    }

    /**
     * An interface for evaluating the state of a bind input.
     * It takes the previous and current states and returns a boolean result.
     */
    @FunctionalInterface
    private interface BindInputEvaluator {
        /**
         * Evaluates the state of a bind input based on its previous and current states.
         *
         * @param previous The previous state of the input.
         * @param current The current state of the input.
         * @return True if the evaluation condition is met, false otherwise.
         */
        boolean evaluate(boolean previous, boolean current);
    }
}
