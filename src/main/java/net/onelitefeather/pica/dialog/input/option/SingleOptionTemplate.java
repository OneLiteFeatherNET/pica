package net.onelitefeather.pica.dialog.input.option;

import net.minestom.server.dialog.DialogInput;
import net.onelitefeather.pica.dialog.input.InputTemplate;
import org.jetbrains.annotations.Contract;

/**
 * The {@link SingleOptionTemplate} represents a single option input of a dialog.
 * At least one option must be added to the input, otherwise an exception will be thrown.
 *
 * @author theEvilReaper
 * @version 1.0.1
 * @see DialogInput.SingleOption
 * @since 1.0.0
 */
public sealed interface SingleOptionTemplate extends InputTemplate<SingleOptionTemplate> permits SingleOptionBuilder {

    /**
     * Creates a new instance of the builder to create a {@link SingleOptionTemplate}.
     *
     * @param key the key of the input
     * @return the builder instance
     */
    @Contract(value = "_ -> new", pure = true)
    static SingleOptionTemplate builder(String key) {
        return new SingleOptionBuilder(key);
    }

    /**
     * Sets the width of the input field.
     *
     * @param width the width of the input field
     * @return the builder
     */
    SingleOptionTemplate with(int width);

    /**
     * Sets the visibility of the label.
     *
     * @param visible whether the label should be visible
     * @return the builder
     */
    SingleOptionTemplate labelVisibility(boolean visible);

    /**
     * Adds an option to the input.
     *
     * @param option the option to add
     * @return the builder
     */
    SingleOptionTemplate option(DialogInput.SingleOption.Option option);

}
