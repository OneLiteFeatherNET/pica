package net.onelitefeather.pica.dialog.type;

import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;

import java.util.function.Consumer;

/**
 * Builder interface for server links dialogs, which show the links the server sent to the client.
 *
 * <p>The buttons are not configured here: the client renders one per server link, so the links
 * themselves come from Minestom's server links packet. This builder only controls how they are laid
 * out and the optional exit button below them.</p>
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public interface ServerLinksDialog extends DialogType {

    /**
     * {@inheritDoc}
     */
    @Override
    ServerLinksDialog meta(Consumer<DialogMeta> consumer);

    /**
     * Configures the exit button using a builder consumer.
     *
     * <p>Optional: without it the dialog shows no exit button.</p>
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    ServerLinksDialog exitAction(Consumer<ActionButton> buttonBuilder);

    /**
     * Sets the number of columns the link buttons are laid out in.
     *
     * <p>Defaults to {@value net.onelitefeather.pica.dialog.DialogConstants#DEFAULT_COLUMNS}, the vanilla default.</p>
     *
     * @param columns the number of columns, at least 1
     * @return this builder for chaining
     * @throws IllegalArgumentException if the number of columns is less than 1
     */
    ServerLinksDialog columns(int columns);

    /**
     * Sets the width of each link button.
     *
     * <p>Defaults to {@value net.onelitefeather.pica.dialog.DialogConstants#DEFAULT_ACTION_WIDTH}, the vanilla default.</p>
     *
     * @param buttonWidth the width of each button
     * @return this builder for chaining
     * @throws IllegalArgumentException if the width is out of the valid range
     */
    ServerLinksDialog buttonWidth(int buttonWidth);
}
