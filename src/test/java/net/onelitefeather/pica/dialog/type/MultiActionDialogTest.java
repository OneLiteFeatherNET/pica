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

class MultiActionDialogTest {

    private static final Key KEY = Key.key("test_key");

    @Test
    @DisplayName("The meta is required, as it is for the other dialog types")
    void testMissingMetaCreation() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.multiAction(KEY).action(button -> button.width(80)).build(),
                "The dialog meta must not be null"
        );
    }

    @Test
    @DisplayName("A multi-action dialog without any action button is rejected")
    void testMissingActionsAreRejected() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> DialogType.multiAction(KEY).meta(meta -> meta.pause(false)).build(),
                "A multi-action dialog needs at least one action button"
        );
    }

    @Test
    @DisplayName("Less than one column is rejected")
    void testInvalidColumnsAreRejected() {
        MultiActionDialog dialog = DialogType.multiAction(KEY);

        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.columns(0));
        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.columns(-1));
    }

    @Test
    @DisplayName("Without further configuration the dialog has no exit button and two columns")
    void testDefaults() {
        MultiActionDialog builder = DialogType.multiAction(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE))
                .action(button -> button.label(Component.text("A")));

        DialogTemplate template = builder.build();
        assertNotNull(template);
        assertEquals(KEY, template.key());

        Dialog.MultiAction dialog = ((MultipleActionDialog) builder).buildDialog();
        assertEquals(1, dialog.actions().size());
        assertNull(dialog.exitAction());
        assertEquals(2, dialog.columns());
    }

    @Test
    @DisplayName("Actions keep their order and the exit button and columns are applied")
    void testConfiguredDialog() {
        MultiActionDialog builder = DialogType.multiAction(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE))
                .action(button -> button.label(Component.text("A")))
                .action(button -> button.label(Component.text("B")))
                .action(button -> button.label(Component.text("C")))
                .exitAction(button -> button.label(Component.text("Back")))
                .columns(3);

        Dialog.MultiAction dialog = ((MultipleActionDialog) builder).buildDialog();
        assertEquals(3, dialog.actions().size());
        assertEquals(Component.text("A"), dialog.actions().get(0).label());
        assertEquals(Component.text("C"), dialog.actions().get(2).label());
        assertNotNull(dialog.exitAction());
        assertEquals(Component.text("Back"), dialog.exitAction().label());
        assertEquals(3, dialog.columns());
    }

    @Test
    @DisplayName("The builder keeps returning itself, so the calls chain in any order")
    void testChaining() {
        MultiActionDialog dialog = DialogType.multiAction(KEY);

        assertEquals(dialog, dialog.action(button -> button.width(80)));
        assertEquals(dialog, dialog.exitAction(button -> button.width(80)));
        assertEquals(dialog, dialog.columns(1));
        assertEquals(dialog, dialog.meta(meta -> meta.pause(false)));
    }
}
