package net.onelitefeather.pica.dialog.tag;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.dialog.DialogAction;
import net.minestom.server.dialog.DialogActionButton;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.dialog.DialogMetadata;

import java.util.List;

/**
 * Creates minimal Minestom {@link Dialog} instances for the dialog tag tests.
 */
final class DialogFixtures {

    private DialogFixtures() {
        // No instances allowed
    }

    static Dialog confirmation(String title) {
        DialogMetadata metadata = new DialogMetadata(
                Component.text(title),
                Component.text(title),
                true,
                false,
                DialogAfterAction.CLOSE,
                List.of(),
                List.of()
        );
        DialogAction action = new DialogAction.Custom(Key.key("test:action"), CompoundBinaryTag.empty());
        DialogActionButton button = new DialogActionButton(
                Component.text("Ok"),
                null,
                DialogActionButton.DEFAULT_WIDTH,
                action
        );
        return new Dialog.Confirmation(metadata, button, button);
    }
}
