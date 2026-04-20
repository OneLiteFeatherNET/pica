package net.theevilreaper.manis.dialog.input.bool;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanTemplateTest {

    private static final String BOOLEAN_KEY = "boolean_supplier";

    @Test
    void testBooleanCreationWithDefaults() {
        DialogInput booleanInput = BooleanTemplate.builder(BOOLEAN_KEY)
                .build();


        assertNotNull(booleanInput, "The boolean input can't be null");
        assertInstanceOf(DialogInput.Boolean.class, booleanInput, "The boolean input must be of type Boolean");
        DialogInput.Boolean booleanInputInstance = (DialogInput.Boolean) booleanInput;

        assertFalse(booleanInputInstance.initial());
        assertEquals(BOOLEAN_KEY, booleanInputInstance.key());
        assertEquals("true", booleanInputInstance.onTrue());
        assertEquals("false", booleanInputInstance.onFalse());
    }

    @Test
    void testBooleanCreation() {
        DialogInput booleanInput = BooleanTemplate.builder(BOOLEAN_KEY)
                .initialValue(true)
                .onTrue("true_value")
                .onFalse("false_value")
                .label(Component.text("Test Label"))
                .build();

        assertNotNull(booleanInput, "The boolean input can't be null");
        assertInstanceOf(DialogInput.Boolean.class, booleanInput, "The boolean input must be of type Boolean");
        DialogInput.Boolean booleanInputInstance = (DialogInput.Boolean) booleanInput;

        assertTrue(booleanInputInstance.initial());
        assertEquals(Component.text("Test Label"), booleanInputInstance.label());
        assertEquals(BOOLEAN_KEY, booleanInputInstance.key());
        assertEquals("true_value", booleanInputInstance.onTrue());
        assertEquals("false_value", booleanInputInstance.onFalse());
    }
}
