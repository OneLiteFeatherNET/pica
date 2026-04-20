package net.theevilreaper.manis.dialog.type;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmDialogTest {

    @Test
    void testMissingMetaCreation() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.confirm(Key.key("test_key")).build(),
                "The dialog meta must not be null"
        );
    }

    @Test
    void testMissingYesButton() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.confirm(Key.key("test_key"))
                        .meta(metaBuilder -> {
                            metaBuilder.pause(true);
                        }).build(),
                "The yes button must not be null"
        );
    }

    @Test
    void testMissingNoButton() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> DialogType.confirm(Key.key("test_key"))
                        .meta(metaBuilder -> {
                            metaBuilder.pause(true);
                        })
                        .yesButton(actionButton -> {
                            actionButton.width(100);
                        })
                        .build(),
                "The no button must not be null"
        );
    }
}
