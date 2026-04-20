package net.theevilreaper.manis.dialog.input.text;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextInputTemplateTest {

    private static final String KEY = "test_key";

    @Test
    void testInvalidWidthUsage() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> TextInputTemplate.builder(KEY).width(-1).build(),
                "Width must be between 1 and 1024"
        );
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> TextInputTemplate.builder(KEY).width(1025).build(),
                "Width must be between 1 and 1024"
        );
    }

    @Test
    void testInvalidMultiLine() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> TextInputTemplate.builder(KEY).multiLine(1, 0).build(),
                "Width must be between 1 and 512"
        );
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> TextInputTemplate.builder(KEY).multiLine(1, 513).build(),
                "Width must be between 1 and 512"
        );
    }

    @Test
    void testTextInputCreation() {
        DialogInput input = TextInputTemplate.builder(KEY)
                .width(10)
                .activeLabel(true)
                .initial("Input value")
                .maxLength(100)
                .build();
        assertNotNull(input);

        assertNotNull(input);
        assertInstanceOf(DialogInput.Text.class, input);

        DialogInput.Text textInput = (DialogInput.Text) input;

        assertEquals("test_key", textInput.key());
        assertEquals(10, textInput.width());
        assertTrue(textInput.labelVisible());
        assertEquals("Input value", textInput.initial());
        assertEquals(100, textInput.maxLength());
        assertNull(textInput.multiline());
    }

    @Test
    void testDialogWithMultiLine() {
        DialogInput input = TextInputTemplate.builder(KEY)
                .width(10)
                .activeLabel(true)
                .label(Component.text("Test label"))
                .initial(() -> "Input value")
                .maxLength(100)
                .multiLine(2, 10)
                .build();
        assertNotNull(input);
        assertInstanceOf(DialogInput.Text.class, input);

        DialogInput.Text textInput = (DialogInput.Text) input;

        assertEquals("test_key", textInput.key());
        assertEquals(10, textInput.width());
        assertTrue(textInput.labelVisible());
        assertEquals(Component.text("Test label"), textInput.label());
        assertEquals("Input value", textInput.initial());
        assertEquals(100, textInput.maxLength());
        assertNotNull(textInput.multiline());

        DialogInput.Text.Multiline multiline = textInput.multiline();

        assertEquals(2, multiline.maxLines());
        assertEquals(10, multiline.height());
    }
}
