package net.onelitefeather.pica.dialog.action;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAction;
import net.minestom.server.dialog.DialogActionButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ActionButtonTest {

    @ParameterizedTest(name = "Test invalid width {0}")
    @ValueSource(ints = {-1, 0, 1025})
    void testInvalidWidthUsage(int value) {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> ActionButton.builder().width(value).build(),
                "Width must be between 1 and 1024"
        );
    }

    @Test
    void testActionButtonCreation() {
        DialogActionButton actionButton = ActionButton.builder()
                .width(239)
                .label(Component.text("Beautiful label"))
                .tooltip(Component.text("Nice Tooltip :)"))
                .action(new DialogAction.OpenUrl(""))
                .build();

        assertNotNull(actionButton);
        assertEquals(239, actionButton.width());
        assertEquals(Component.text("Beautiful label"), actionButton.label());
        assertEquals(Component.text("Nice Tooltip :)"), actionButton.tooltip());
        assertInstanceOf(DialogAction.OpenUrl.class, actionButton.action());
        assertEquals(new DialogAction.OpenUrl(""), actionButton.action());
    }
}
