package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.onelitefeather.pica.dialog.DialogConstants;
import net.onelitefeather.pica.dialog.DialogTemplate;
import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;
import net.onelitefeather.pica.dialog.meta.DialogMetaData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Default implementation of a {@link ServerLinksDialog}.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public final class ServerLinkDialog implements ServerLinksDialog {

    private final Key key;
    private DialogMeta dialogMeta;
    private @Nullable ActionButton exitAction;
    private int columns;
    private int buttonWidth;

    ServerLinkDialog(Key key) {
        this.key = key;
        this.columns = DialogConstants.DEFAULT_COLUMNS;
        this.buttonWidth = DialogConstants.DEFAULT_ACTION_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerLinksDialog exitAction(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.exitAction = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerLinksDialog columns(int columns) {
        DialogConstants.validateColumns(columns);
        this.columns = columns;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerLinksDialog buttonWidth(int buttonWidth) {
        DialogConstants.validateWidth(buttonWidth);
        this.buttonWidth = buttonWidth;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerLinksDialog meta(Consumer<DialogMeta> consumer) {
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
        return DialogTemplate.of(key, buildDialog());
    }

    /**
     * Validates the configured values and creates the underlying Minestom dialog.
     *
     * @return the created server links dialog
     */
    Dialog.ServerLinks buildDialog() {
        Objects.requireNonNull(this.dialogMeta, "The dialog meta must not be null");
        return new Dialog.ServerLinks(
                dialogMeta.build(),
                exitAction == null ? null : exitAction.build(),
                columns,
                buttonWidth
        );
    }
}
