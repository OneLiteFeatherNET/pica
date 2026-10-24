package net.onelitefeather.pica.dialog.type;

import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;

import java.util.function.Consumer;

/**
 * Builder interface for multi-action dialogs, which lay out any number of action buttons in a grid.
 *
 * <p>Use this where the player picks one of several choices: a menu, a mode selection, a list of
 * destinations. A {@link ConfirmDialog} is limited to two answers and a {@link NoticeDialog} to one,
 * so anything beyond that belongs here.</p>
 *
 * <p>At least one action button is required. The buttons fill the grid row by row, and the optional
 * exit button sits below it and also runs when the dialog is closed with escape.</p>
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public interface MultiActionDialog extends DialogType {

    /**
     * {@inheritDoc}
     */
    @Override
    MultiActionDialog meta(Consumer<DialogMeta> consumer);

    /**
     * Adds an action button to the grid using a builder consumer.
     *
     * <p>Buttons appear in the order they are added.</p>
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    MultiActionDialog action(Consumer<ActionButton> buttonBuilder);

    /**
     * Configures the exit button using a builder consumer.
     *
     * <p>Optional: without it the dialog shows no exit button.</p>
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    MultiActionDialog exitAction(Consumer<ActionButton> buttonBuilder);

    /**
     * Sets the number of columns the action buttons are laid out in.
     *
     * <p>Defaults to {@value net.onelitefeather.pica.dialog.DialogConstants#DEFAULT_COLUMNS}, the vanilla default.</p>
     *
     * @param columns the number of columns, at least 1
     * @return this builder for chaining
     * @throws IllegalArgumentException if the number of columns is less than 1
     */
    MultiActionDialog columns(int columns);
}
