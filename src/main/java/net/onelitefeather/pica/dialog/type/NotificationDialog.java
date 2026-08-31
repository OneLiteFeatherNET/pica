package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.onelitefeather.pica.dialog.DialogTemplate;
import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;
import net.onelitefeather.pica.dialog.meta.DialogMetaData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Default implementation of a {@link NoticeDialog}.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public final class NotificationDialog implements NoticeDialog {

    private final Key key;
    private DialogMeta dialogMeta;
    private @Nullable ActionButton actionButton;

    NotificationDialog(Key key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoticeDialog actionButton(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.actionButton = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoticeDialog meta(Consumer<DialogMeta> consumer) {
        DialogMeta builder = new DialogMetaData();
        consumer.accept(builder);
        this.dialogMeta = builder;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Unlike a confirmation dialog this does not demand a button: an unset one leaves
     * {@link Dialog.Notice#DEFAULT_ACTION} in place, which is the action vanilla shows for a notice
     * that brings none of its own.</p>
     */
    @Override
    public DialogTemplate build() {
        Objects.requireNonNull(this.dialogMeta, "The dialog meta must not be null");
        Dialog dialog = new Dialog.Notice(
                dialogMeta.build(),
                actionButton == null ? Dialog.Notice.DEFAULT_ACTION : actionButton.build()
        );
        return DialogTemplate.of(key, dialog);
    }
}
