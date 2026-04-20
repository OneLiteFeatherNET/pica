package net.onelitefeather.pica.dialog.input.option;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleOptionTemplateTest {

    private static final String OPTION_KEY = "test";

    @Test
    void testMissingOptionAtCreation() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> SingleOptionTemplate.builder(OPTION_KEY).build(),
                "No options have been added. Required at least one option."
        );
    }

    @Test
    void testSingleOptionCreation() {
        DialogInput input = SingleOptionTemplate.builder(OPTION_KEY)
                .label(Component.text("Label"))
                .labelVisibility(true)
                .with(100)
                .option(new DialogInput.SingleOption.Option("Option 1", Component.text("Option 1"), true))
                .option(new DialogInput.SingleOption.Option("Option 2", Component.text("Option 2"), false))
                .build();

        assertNotNull(input, "The created input must not be null");
        assertInstanceOf(DialogInput.SingleOption.class, input, "The created input must be of type SingleOption");

        DialogInput.SingleOption singleOption = (DialogInput.SingleOption) input;

        assertEquals("test", singleOption.key());
        assertEquals(100, singleOption.width());
        assertTrue(singleOption.labelVisible());
        assertEquals(Component.text("Label"), singleOption.label());
        assertEquals(2, singleOption.options().size());
    }
}
