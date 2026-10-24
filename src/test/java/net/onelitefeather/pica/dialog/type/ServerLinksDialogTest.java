package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.dialog.DialogAfterAction;
import net.onelitefeather.pica.dialog.DialogTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ServerLinksDialogTest {

    private static final Key KEY = Key.key("test_key");

    @Test
    @DisplayName("The meta is required, as it is for the other dialog types")
    void testMissingMetaCreation() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.serverLinks(KEY).build(),
                "The dialog meta must not be null"
        );
    }

    @Test
    @DisplayName("Less than one column and an invalid button width are rejected")
    void testInvalidLayoutIsRejected() {
        ServerLinksDialog dialog = DialogType.serverLinks(KEY);

        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.columns(0));
        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.buttonWidth(0));
        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.buttonWidth(1025));
    }

    @Test
    @DisplayName("Without further configuration the vanilla defaults are used")
    void testDefaults() {
        ServerLinksDialog builder = DialogType.serverLinks(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE));

        DialogTemplate template = builder.build();
        assertNotNull(template);
        assertEquals(KEY, template.key());

        Dialog.ServerLinks dialog = ((ServerLinkDialog) builder).buildDialog();
        assertNull(dialog.exitAction());
        assertEquals(2, dialog.columns());
        assertEquals(150, dialog.buttonWidth());
    }

    @Test
    @DisplayName("The exit button, columns and button width are applied")
    void testConfiguredDialog() {
        ServerLinksDialog builder = DialogType.serverLinks(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE))
                .exitAction(button -> button.label(Component.text("Back")))
                .columns(1)
                .buttonWidth(300);

        Dialog.ServerLinks dialog = ((ServerLinkDialog) builder).buildDialog();
        assertNotNull(dialog.exitAction());
        assertEquals(Component.text("Back"), dialog.exitAction().label());
        assertEquals(1, dialog.columns());
        assertEquals(300, dialog.buttonWidth());
    }

    @Test
    @DisplayName("The builder keeps returning itself, so the calls chain in any order")
    void testChaining() {
        ServerLinksDialog dialog = DialogType.serverLinks(KEY);

        assertEquals(dialog, dialog.exitAction(button -> button.width(80)));
        assertEquals(dialog, dialog.columns(1));
        assertEquals(dialog, dialog.buttonWidth(100));
        assertEquals(dialog, dialog.meta(meta -> meta.pause(false)));
    }
}
