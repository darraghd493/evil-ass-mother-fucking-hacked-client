package me.darragh.eamfhc.module.impl.misc;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
import me.darragh.eamfhc.feature.property.type.enumerable.MultiEnumProperty;
import me.darragh.eamfhc.feature.property.type.number.FloatProperty;
import me.darragh.eamfhc.feature.property.type.number.IntegerProperty;
import me.darragh.eamfhc.feature.property.type.range.FloatRangeProperty;
import me.darragh.eamfhc.feature.property.type.range.IntegerRangeProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.type.Range;

@ModuleIdentifier(
        identifier = "test-module",
        displayName = "Test",
        description = "A test module for development purposes.",
        type = ModuleType.MISC
)
public class TestModule extends Module {
    private final BooleanProperty boolean1 = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("boolean-1", "Boolean 1"))
            .defaultValue(false)
            .build();

    private final BooleanProperty boolean2 = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("boolean-2", "Boolean 2", "Boo!"))
            .defaultValue(true)
            .build();

    private final IntegerProperty integerProperty = PropertyFactory.integerPropertyBuilder(this)
            .metadata(new PropertyMetadata("integer-property", "int"))
            .defaultValue(42)
            .constraints(new NumberPropertyConstraints<>(0, 100, 1))
            .build();

    private final FloatProperty floatProperty = PropertyFactory.floatPropertyBuilder(this)
            .metadata(new PropertyMetadata("float-property", "float"))
            .defaultValue(3.14F)
            .constraints(new NumberPropertyConstraints<>(0.0F, 10.0F, 0.01F))
            .build();

    private final IntegerRangeProperty integerRangeProperty = PropertyFactory.integerRangePropertyBuilder(this)
            .metadata(new PropertyMetadata("integer-range-property", "intrange"))
            .defaultValue(new Range<>(10, 50))
            .constraints(new NumberPropertyConstraints<>(0, 100, 1))
            .build();

    private final FloatRangeProperty floatRangeProperty = PropertyFactory.floatRangePropertyBuilder(this)
            .metadata(new PropertyMetadata("float-range-property", "floatrange"))
            .defaultValue(new Range<>(1.0F, 5.0F))
            .constraints(new NumberPropertyConstraints<>(0.0F, 10.0F, 0.01F))
            .build();

    private final EnumProperty<TestEnum> testEnumProperty = PropertyFactory.enumPropertyBuilder(this, TestEnum.class)
            .metadata(new PropertyMetadata("test-enum-property", "enum"))
            .defaultValue(TestEnum.OPTION_ONE)
            .build();

    private final MultiEnumProperty<TestEnum> testMultiEnumProperty = PropertyFactory.multiEnumPropertyBuilder(this, TestEnum.class)
            .metadata(new PropertyMetadata("test-multi-enum-property", "multienum"))
            .defaultValue(Lists.newArrayList(TestEnum.OPTION_ONE, TestEnum.OPTION_TWO))
            .build();

    @Getter
    @RequiredArgsConstructor
    private enum TestEnum implements PropertyEnum {
        OPTION_ONE("option-1", "Option 1"),
        OPTION_TWO("option-2", "Option 2"),
        OPTION_THREE("option-3", "Option 3");

        private final String identifier, name;
    }
}
