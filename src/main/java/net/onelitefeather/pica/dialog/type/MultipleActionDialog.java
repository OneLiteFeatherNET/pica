package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.dialog.DialogActionButton;
import net.onelitefeather.pica.dialog.DialogConstants;
import net.onelitefeather.pica.dialog.DialogTemplate;
import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;
import net.onelitefeather.pica.dialog.meta.DialogMetaData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Default implementation of a {@link MultiActionDialog}.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public final class MultipleActionDialog implements MultiActionDialog {

    private final Key key;
    private final List<ActionButton> actions;
    private DialogMeta dialogMeta;
    private @Nullable ActionButton exitAction;
    private int columns;

    MultipleActionDialog(Key key) {
        this.key = key;
        this.actions = new ArrayList<>();
        this.columns = DialogConstants.DEFAULT_COLUMNS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiActionDialog action(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.actions.add(button);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiActionDialog exitAction(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.exitAction = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiActionDialog columns(int columns) {
        DialogConstants.validateColumns(columns);
        this.columns = columns;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiActionDialog meta(Consumer<DialogMeta> consumer) {
        DialogMeta builder = new DialogMetaData();
        consumer.accept(builder);
        this.dialogMeta = builder;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Minestom accepts an empty button list, but the client rejects such a dialog, so this
     * demands at least one action button.</p>
     */
    @Override
    public DialogTemplate build() {
        return DialogTemplate.of(key, buildDialog());
    }

    /**
     * Validates the configured values and creates the underlying Minestom dialog.
     *
     * @return the created multi-action dialog
     */
    Dialog.MultiAction buildDialog() {
        Objects.requireNonNull(this.dialogMeta, "The dialog meta must not be null");
        if (this.actions.isEmpty()) {
            throw new IllegalStateException("A multi-action dialog needs at least one action button");
        }
        List<DialogActionButton> buttons = this.actions.stream().map(ActionButton::build).toList();
        return new Dialog.MultiAction(
                dialogMeta.build(),
                buttons,
                exitAction == null ? null : exitAction.build(),
                columns
        );
    }
}
