package me.darragh.eamfhc.module;

import lombok.Getter;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.GameInstance;
import me.darragh.eamfhc.feature.Bindable;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.Feature;
import me.darragh.eamfhc.feature.Toggleable;
import me.darragh.eamfhc.feature.bindable.Bind;
import me.darragh.eamfhc.feature.property.PropertyManager;

/**
 * A toggleable module designed to be interacted with by the user.
 *
 * @author darragh
 */
public class Module implements Feature<ModuleMetadata>, Bindable, Configurable, Toggleable, GameInstance {
    @Getter // Undocumented method
    private final ModuleIdentifier identifier;
    private final ModuleMetadata metadata;
    @SuppressWarnings("FieldMayBeFinal")
    @Getter
    private Bind bind = Bind.EMPTY.copy();
    @Getter
    private final PropertyManager propertyManager = new PropertyManager();
    @Getter
    private boolean enabled;

    public Module() {
        this.identifier = this.getClass().getAnnotation(ModuleIdentifier.class);
        if (this.identifier == null) {
            throw new IllegalStateException("ModuleIdentifier annotation is missing on " + this.getClass().getName());
        }
        this.metadata = ModuleMetadata.of(
                this.identifier.identifier(),
                this.identifier.displayName(),
                this.identifier.description(),
                this.identifier.type()
        );
        if (this.identifier.defaultKeybind() != -1) {
            this.bind.setKeyCode(this.identifier.defaultKeybind());
        }
    }

    @Override
    public void init() {
        // no-op
    }

    @Override
    public void destroy() {
        if (this.isEnabled()) {
            this.disable();
            this.propertyManager.clear();
        }
    }

    protected void onEnable() {
        Client.INSTANCE.getEventDispatcher().register(this);
    }

    protected void onDisable() {
        Client.INSTANCE.getEventDispatcher().unregister(this);
    }

    @Override
    public ModuleMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }
}
