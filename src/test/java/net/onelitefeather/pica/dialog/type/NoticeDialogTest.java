package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.DialogAfterAction;
import net.onelitefeather.pica.dialog.DialogTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class NoticeDialogTest {

    private static final Key KEY = Key.key("test_key");

    @Test
    @DisplayName("The meta is required, as it is for a confirmation")
    void testMissingMetaCreation() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.notice(KEY).build(),
                "The dialog meta must not be null"
        );
    }

    @Test
    @DisplayName("A paused dialog needs an after-action, or Minestom rejects it")
    void testPausedWithoutAfterActionIsRejected() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> DialogType.notice(KEY).meta(meta -> meta.pause(true)).build(),
                "Dialog may not have pause=true and afterAction=NONE"
        );
    }

    @Test
    @DisplayName("A notice builds without an action button, unlike a confirmation")
    void testBuildsWithoutAnActionButton() {
        DialogTemplate template = DialogType.notice(KEY)
                .meta(meta -> meta.pause(true).afterAction(DialogAfterAction.CLOSE))
                .build();

        assertNotNull(template);
        assertEquals(KEY, template.key());
    }

    @Test
    @DisplayName("A configured action button is accepted")
    void testBuildsWithAnActionButton() {
        DialogTemplate template = DialogType.notice(KEY)
                .meta(meta -> meta.pause(true).afterAction(DialogAfterAction.CLOSE))
                .actionButton(button -> button.width(120))
                .build();

        assertNotNull(template);
        assertEquals(KEY, template.key());
    }

    @Test
    @DisplayName("The builder keeps returning itself, so the calls chain in any order")
    void testChaining() {
        NoticeDialog dialog = DialogType.notice(KEY);

        assertEquals(dialog, dialog.actionButton(button -> button.width(80)));
        assertEquals(dialog, dialog.meta(meta -> meta.pause(false)));
    }
}
