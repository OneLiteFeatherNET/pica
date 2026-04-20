package net.onelitefeather.pica.dialog.type;

import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;

import java.util.function.Consumer;

/**
 * Builder interface for confirmation dialogs with yes/no action buttons.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ConfirmDialog extends DialogType {

    /**
     * {@inheritDoc}
     */
    @Override
    ConfirmDialog meta(Consumer<DialogMeta> consumer);

    /**
     * Configures the "yes" action button using a builder consumer.
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    ConfirmDialog yesButton(Consumer<ActionButton> buttonBuilder);

    /**
     * Configures the "no" action button using a builder consumer.
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    ConfirmDialog noButton(Consumer<ActionButton> buttonBuilder);
}
