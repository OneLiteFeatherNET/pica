package net.onelitefeather.pica.dialog.input.range;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTemplateTest {

    private static final String KEY = "test_key";

    @Test
    void testInvalidWidth() {
        IllegalArgumentException ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RangeTemplate.builder(KEY).width(-1).build()
        );
        assertEquals("Width must be between 1 and 1024", ex.getMessage());
    }

    @Test
    void testInvalidStart() {
        IllegalArgumentException ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RangeTemplate.builder(KEY).start(-1).build()
        );
        assertEquals("Start must be greater than or equal to 0", ex.getMessage());
    }

    @Test
    void testInvalidEndWithMissingStart() {
        IllegalArgumentException exception = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RangeTemplate.builder(KEY).end(-1).build()
        );
        assertEquals("End greater than or equal to 0", exception.getMessage());
    }

    @Test
    void testInvalidEndWithStartValue() {
        IllegalArgumentException exception = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RangeTemplate.builder(KEY).start(1).end(0).build()
        );
        assertEquals("End must be greater than start", exception.getMessage());
    }

    @Test
    void testBuildWithAllFields() {
        DialogInput input = RangeTemplate.builder(KEY)
                .width(7)
                .label(Component.text("My Label"))
                .labelFormat("my.format")
                .start(1.5f)
                .end(9.5f)
                .initial(2.5f)
                .step(0.5f)
                .build();

        assertNotNull(input);
        assertInstanceOf(DialogInput.NumberRange.class, input);

        DialogInput.NumberRange numberRange = (DialogInput.NumberRange) input;

        assertEquals("test_key", numberRange.key());
        assertEquals(7, numberRange.width());
        assertEquals(Component.text("My Label"), numberRange.label());
        assertEquals("my.format", numberRange.labelFormat());
        assertEquals(1.5f, numberRange.start(), 0.0001f);
        assertEquals(9.5f, numberRange.end(), 0.0001f);
        assertNotNull(numberRange.initial());
        assertEquals(2.5f, numberRange.initial(), 0.0001f);
        assertNotNull(numberRange.step());
        assertEquals(0.5f, numberRange.step(), 0.0001f);
    }
}
