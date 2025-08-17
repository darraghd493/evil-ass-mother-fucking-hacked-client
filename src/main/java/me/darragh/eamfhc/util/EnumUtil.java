package me.darragh.eamfhc.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.Identifiable;

import java.util.Arrays;

@UtilityClass
public class EnumUtil {
    public static <T extends Enum<T> & Identifiable> T getEnum(Class<T> enumClass, String id) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(v -> v.getIdentifier().equals(id))
                .findFirst()
                .orElse(null);
    }
}
