package net.theevilreaper.manis.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.theevilreaper.manis.dialog.DialogTemplate;
import net.theevilreaper.manis.dialog.action.ActionButton;
import net.theevilreaper.manis.dialog.meta.DialogMetaData;
import net.theevilreaper.manis.dialog.meta.DialogMeta;

import java.util.Objects;
import java.util.function.Consumer;

public final class ConfirmationDialog implements ConfirmDialog {

    private final Key key;
    private DialogMeta dialogMeta;
    private ActionButton yesButton;
    private ActionButton noButton;

    ConfirmationDialog(Key key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfirmDialog yesButton(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.yesButton = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfirmDialog noButton(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.noButton = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfirmDialog meta(Consumer<DialogMeta> consumer) {
        DialogMeta builder = new DialogMetaData();
        consumer.accept(builder);
        this.dialogMeta = builder;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogTemplate build() {
        Objects.requireNonNull(this.dialogMeta, "The dialog meta must not be null");
        Objects.requireNonNull(this.yesButton, "The yes button must not be null");
        Objects.requireNonNull(this.noButton, "The no button must not be null");
        Dialog dialog = new Dialog.Confirmation(
                dialogMeta.build(),
                yesButton.build(),
                noButton.build()
        );
        return DialogTemplate.of(key, dialog);
    }
}
