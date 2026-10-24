package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.registry.HolderSet;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.registry.RegistryTag;
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
 * Default implementation of a {@link ListDialog}.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public final class DialogListing implements ListDialog {

    private final Key key;
    private final List<Dialog> dialogs;
    private final List<RegistryKey<Dialog>> dialogKeys;
    private DialogMeta dialogMeta;
    private @Nullable ActionButton exitAction;
    private int columns;
    private int buttonWidth;

    DialogListing(Key key) {
        this.key = key;
        this.dialogs = new ArrayList<>();
        this.dialogKeys = new ArrayList<>();
        this.columns = DialogConstants.DEFAULT_COLUMNS;
        this.buttonWidth = DialogConstants.DEFAULT_ACTION_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog dialog(Dialog dialog) {
        this.dialogs.add(dialog);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog dialog(Key key) {
        this.dialogKeys.add(RegistryKey.of(key));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog exitAction(Consumer<ActionButton> buttonBuilder) {
        ActionButton button = ActionButton.builder();
        buttonBuilder.accept(button);
        this.exitAction = button;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog columns(int columns) {
        DialogConstants.validateColumns(columns);
        this.columns = columns;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog buttonWidth(int buttonWidth) {
        DialogConstants.validateWidth(buttonWidth);
        this.buttonWidth = buttonWidth;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListDialog meta(Consumer<DialogMeta> consumer) {
        DialogMeta builder = new DialogMetaData();
        consumer.accept(builder);
        this.dialogMeta = builder;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>A {@link HolderSet} holds either inline dialogs or registry keys, never both, so a list
     * built from both kinds is rejected here rather than by the client.</p>
     */
    @Override
    public DialogTemplate build() {
        return DialogTemplate.of(key, buildDialog());
    }

    /**
     * Validates the configured values and creates the underlying Minestom dialog.
     *
     * @return the created dialog list
     */
    Dialog.DialogList buildDialog() {
        Objects.requireNonNull(this.dialogMeta, "The dialog meta must not be null");
        if (this.dialogs.isEmpty() && this.dialogKeys.isEmpty()) {
            throw new IllegalStateException("A dialog list needs at least one dialog");
        }
        if (!this.dialogs.isEmpty() && !this.dialogKeys.isEmpty()) {
            throw new IllegalStateException("A dialog list can not mix inline dialogs and registry keys");
        }
        HolderSet<Dialog> holders = this.dialogs.isEmpty()
                ? RegistryTag.direct(this.dialogKeys)
                : new HolderSet.Direct<>(this.dialogs);
        return new Dialog.DialogList(
                dialogMeta.build(),
                holders,
                exitAction == null ? null : exitAction.build(),
                columns,
                buttonWidth
        );
    }
}
