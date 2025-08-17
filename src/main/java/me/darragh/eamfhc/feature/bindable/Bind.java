package me.darragh.eamfhc.feature.bindable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.darragh.eamfhc.Copyable;
import me.darragh.eamfhc.Serialisable;
import me.darragh.eamfhc.gson.GsonProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Stores the bindable feature data.
 *
 * @author darraghd493
 */
@Data
@AllArgsConstructor
public class Bind implements Serialisable, Copyable<Bind> {
    public static final @NotNull Bind EMPTY = new Bind(
            BindInputDevice.KEYBOARD,
            BindTrigger.PRESS,
            -1
    );

    private @NotNull @SerializedName("device") BindInputDevice device;
    private @NotNull @SerializedName("trigger") BindTrigger trigger;
    private @SerializedName("keycode") int keyCode;

    @Override
    public JsonObject toJson() {
        Gson gson = GsonProvider.getGson();
        return gson.toJsonTree(this).getAsJsonObject();
    }

    @Override
    public void fromJson(JsonObject json) {
        Gson gson = GsonProvider.getGson();
        Bind bind = gson.fromJson(json, Bind.class);
        this.device = bind.getDevice();
        this.trigger = bind.getTrigger();
        this.keyCode = bind.getKeyCode();
    }

    @Override
    public Bind copy() {
        return new Bind(this.device, this.trigger, this.keyCode);
    }
}
