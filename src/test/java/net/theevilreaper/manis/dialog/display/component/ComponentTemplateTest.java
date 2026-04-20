package net.theevilreaper.manis.dialog.display.component;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTemplateTest {

    @ParameterizedTest(name = "Test invalid width {0}")
    @ValueSource(ints = {-1, 0, 1025})
    void testInvalidWidthUsage(int width) {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> ComponentTemplate.builder().width(width).build(),
                "Width must be between 1 and 1024"
        );
    }

    @Test
    void testComponentCreation() {
        DialogBody body = ComponentTemplate
                .builder()
                .width(100)
                .contents(Component.text("Test Component"))
                .build();
        assertNotNull(body, "The body can't be null");
        assertInstanceOf(DialogBody.PlainMessage.class, body, "The body must be of type PlainMessage");

        DialogBody.PlainMessage message = (DialogBody.PlainMessage) body;
        assertEquals(100, message.width());
        assertEquals(Component.text("Test Component"), message.contents());
    }
}
