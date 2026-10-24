package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.dialog.DialogMetadata;
import net.minestom.server.registry.HolderSet;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.registry.RegistryTag;
import net.onelitefeather.pica.dialog.DialogTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListDialogTest {

    private static final Key KEY = Key.key("test_key");
    private static final Key FIRST = Key.key("test:first");
    private static final Key SECOND = Key.key("test:second");

    private static Dialog notice() {
        DialogMetadata metadata = new DialogMetadata(
                Component.text("First"),
                null,
                true,
                false,
                DialogAfterAction.CLOSE,
                List.of(),
                List.of()
        );
        return new Dialog.Notice(metadata, Dialog.Notice.DEFAULT_ACTION);
    }

    @Test
    @DisplayName("The meta is required, as it is for the other dialog types")
    void testMissingMetaCreation() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.dialogList(KEY).dialog(FIRST).build(),
                "The dialog meta must not be null"
        );
    }

    @Test
    @DisplayName("A dialog list without any dialog is rejected")
    void testMissingDialogsAreRejected() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> DialogType.dialogList(KEY).meta(meta -> meta.pause(false)).build(),
                "A dialog list needs at least one dialog"
        );
    }

    @Test
    @DisplayName("Inline dialogs and registry keys can not be mixed")
    void testMixedDialogsAreRejected() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> DialogType.dialogList(KEY)
                        .meta(meta -> meta.pause(false))
                        .dialog(notice())
                        .dialog(SECOND)
                        .build(),
                "A dialog list can not mix inline dialogs and registry keys"
        );
    }

    @Test
    @DisplayName("Less than one column and an invalid button width are rejected")
    void testInvalidLayoutIsRejected() {
        ListDialog dialog = DialogType.dialogList(KEY);

        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.columns(0));
        assertThrowsExactly(IllegalArgumentException.class, () -> dialog.buttonWidth(0));
    }

    @Test
    @DisplayName("Registry keys end up in a registry tag and the vanilla defaults are used")
    void testRegistryKeys() {
        ListDialog builder = DialogType.dialogList(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE))
                .dialog(FIRST)
                .dialog(SECOND);

        DialogTemplate template = builder.build();
        assertNotNull(template);
        assertEquals(KEY, template.key());

        Dialog.DialogList dialog = ((DialogListing) builder).buildDialog();
        assertInstanceOf(RegistryTag.class, dialog.dialogs());
        RegistryTag<Dialog> tag = (RegistryTag<Dialog>) dialog.dialogs();
        assertTrue(tag.contains(RegistryKey.of(FIRST)));
        assertTrue(tag.contains(RegistryKey.of(SECOND)));
        assertNull(dialog.exitAction());
        assertEquals(2, dialog.columns());
        assertEquals(150, dialog.buttonWidth());
    }

    @Test
    @DisplayName("Inline dialogs end up in a direct holder set and the layout is applied")
    void testInlineDialogs() {
        Dialog listed = notice();
        ListDialog builder = DialogType.dialogList(KEY)
                .meta(meta -> meta.afterAction(DialogAfterAction.CLOSE))
                .dialog(listed)
                .exitAction(button -> button.label(Component.text("Back")))
                .columns(3)
                .buttonWidth(200);

        Dialog.DialogList dialog = ((DialogListing) builder).buildDialog();
        HolderSet.Direct<?> direct = assertInstanceOf(HolderSet.Direct.class, dialog.dialogs());
        assertEquals(1, direct.values().size());
        assertEquals(listed, direct.values().getFirst());
        assertNotNull(dialog.exitAction());
        assertEquals(3, dialog.columns());
        assertEquals(200, dialog.buttonWidth());
    }

    @Test
    @DisplayName("The builder keeps returning itself, so the calls chain in any order")
    void testChaining() {
        ListDialog dialog = DialogType.dialogList(KEY);

        assertEquals(dialog, dialog.dialog(FIRST));
        assertEquals(dialog, dialog.exitAction(button -> button.width(80)));
        assertEquals(dialog, dialog.columns(1));
        assertEquals(dialog, dialog.buttonWidth(100));
        assertEquals(dialog, dialog.meta(meta -> meta.pause(false)));
    }
}
