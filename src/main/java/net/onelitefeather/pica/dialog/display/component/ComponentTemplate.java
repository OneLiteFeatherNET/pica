package net.onelitefeather.pica.dialog.display.component;

import net.kyori.adventure.text.Component;
import net.onelitefeather.pica.dialog.display.BodyTemplate;
import org.jetbrains.annotations.Contract;

/**
 * The {@link ComponentTemplate} represents the component of a dialog which displays a component with a specific width.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public sealed interface ComponentTemplate extends BodyTemplate permits PlainMessageBuilder {

    /**
     * Creates a new builder for the {@link ComponentTemplate}.
     *
     * @return the created instance
     */
    @Contract(pure = true)
    static ComponentTemplate builder() {
        return new PlainMessageBuilder();
    }

    /**
     * Sets the message of the body.
     *
     * @param message the message to set
     * @return the builder
     */
    ComponentTemplate contents(Component message);

    /**
     * Sets the width of the body.
     *
     * @param width the width to set
     * @return the builder
     */
    ComponentTemplate width(int width);
}
