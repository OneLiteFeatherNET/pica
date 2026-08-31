package net.onelitefeather.pica.dialog.type;

import net.onelitefeather.pica.dialog.action.ActionButton;
import net.onelitefeather.pica.dialog.meta.DialogMeta;

import java.util.function.Consumer;

/**
 * Builder interface for notice dialogs, which show a message behind a single action button.
 *
 * <p>Use this where a dialog tells the player something rather than asking them: a disclaimer, a
 * warning, a rule they have to read. A {@link ConfirmDialog} always renders two buttons, so a
 * message with nothing to decide would have to invent a second answer for the player to give.</p>
 *
 * <p>The button is optional. Left unset, the dialog uses the client's own default action, which is
 * what vanilla shows for a notice with no button of its own.</p>
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.2.0
 */
public interface NoticeDialog extends DialogType {

    /**
     * {@inheritDoc}
     */
    @Override
    NoticeDialog meta(Consumer<DialogMeta> consumer);

    /**
     * Configures the single action button using a builder consumer.
     *
     * <p>Optional: without it the dialog falls back to the client's default action.</p>
     *
     * @param buttonBuilder the consumer to configure the button
     * @return this builder for chaining
     */
    NoticeDialog actionButton(Consumer<ActionButton> buttonBuilder);
}
