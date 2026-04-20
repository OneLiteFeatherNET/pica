package net.theevilreaper.manis.dialog.action;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAction;
import net.minestom.server.dialog.DialogActionButton;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * The {@link ActionButton} interface represents a builder for {@link DialogActionButton} instances.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public sealed interface ActionButton permits ActionButtonBuilder {

    /**
     * Creates a new builder instance for the {@link ActionButton}.
     *
     * @return the created builder instance
     */
    @Contract(pure = true)
    static ActionButton builder() {
        return new ActionButtonBuilder();
    }

    /**
     * Sets the label of the action button.
     *
     * @param label the label to set
     * @return the builder
     */
    ActionButton label(Component label);

    /**
     * Sets the tool tip of the action button.
     *
     * @param tooltip the tool tip to set
     * @return the builder
     */
    ActionButton tooltip(Component tooltip);

    /**
     * Sets the width of the action button.
     *
     * @param width the width to set
     * @return the builder
     */
    ActionButton width(int width);

    /**
     * Sets the action of the action button.
     *
     * @param action the action to set
     * @return the builder
     */
    ActionButton action(DialogAction action);

    /**
     * Builds the action button.
     *
     * @return the built action button
     */
    DialogActionButton build();
}