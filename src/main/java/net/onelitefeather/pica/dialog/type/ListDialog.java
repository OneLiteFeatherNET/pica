package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;

import java.util.function.Consumer;

/**
 * Builder interface for dialog lists, which show one button per listed dialog that opens it.
 *
 * <p>Use this as an index over other dialogs: a help menu that leads to several pages, or a
 * settings overview that leads to each settings dialog. The button labels come from the listed
 * dialogs' external titles.</p>
 *
 * <p>The listed dialogs are either inline {@link Dialog} instances or keys of dialogs registered in
 * Minestom's dialog registry. Minecraft does not allow mixing both in one list, so a dialog list
 * built from both is rejected.</p>
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public interface ListDialog extends DialogType {

    /**
     * {@inheritDoc}
     */
    @Override
    ListDialog meta(Consumer<DialogMeta> consumer);

    /**
     * Adds an inline dialog to the list.
     *
     * @param dialog the dialog to list
     * @return this builder for chaining
     */
    ListDialog dialog(Dialog dialog);

    /**
     * Adds a dialog registered in Minestom's dialog registry to the list.
     *
     * @param key the key the dialog is registered under
     * @return this builder for chaining
     */
    ListDialog dialog(Key key);

    /**
     * Configures the exit button using a builder consumer.
     *
     * <p>Optional: without it the dialog shows no exit button.</p>
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    ListDialog exitAction(Consumer<ActionButton> buttonBuilder);

    /**
     * Sets the number of columns the dialog buttons are laid out in.
     *
     * <p>Defaults to {@value net.onelitefeather.pica.dialog.DialogConstants#DEFAULT_COLUMNS}, the vanilla default.</p>
     *
     * @param columns the number of columns, at least 1
     * @return this builder for chaining
     * @throws IllegalArgumentException if the number of columns is less than 1
     */
    ListDialog columns(int columns);

    /**
     * Sets the width of each dialog button.
     *
     * <p>Defaults to {@value net.onelitefeather.pica.dialog.DialogConstants#DEFAULT_ACTION_WIDTH}, the vanilla default.</p>
     *
     * @param buttonWidth the width of each button
     * @return this builder for chaining
     * @throws IllegalArgumentException if the width is out of the valid range
     */
    ListDialog buttonWidth(int buttonWidth);
}
