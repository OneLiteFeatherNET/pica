package net.onelitefeather.pica.dialog.input.text;

import net.onelitefeather.pica.dialog.input.InputTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.function.Supplier;

/**
 * The {@link TextInputTemplate} is responsible to create a {@link net.minestom.server.dialog.DialogInput.Text} instances.
 *
 * @author theEvilReaper
 * @version 1.0.1
 * @see TextInputBuilder
 * @see net.minestom.server.dialog.DialogInput.Text
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public sealed interface TextInputTemplate extends InputTemplate<TextInputTemplate> permits TextInputBuilder {

    /**
     * Creates a new builder instance for the {@link TextInputTemplate}.
     *
     * @param key the key of the text input
     * @return the created builder instance
     */

    @Contract(pure = true, value = "_ -> new")
    static TextInputTemplate builder(String key) {
        return new TextInputBuilder(key);
    }

    /**
     * Sets the width of the text input.
     *
     * @param width the width to set
     * @return the builder
     */
    TextInputTemplate width(int width);

    /**
     * Sets the active label of the text input.
     *
     * @param label the label to set
     * @return the builder
     */
    TextInputTemplate activeLabel(boolean label);

    /**
     * Sets the initial value of the text input.
     *
     * @param initial the initial value
     * @return the builder
     */
    TextInputTemplate initial(String initial);

    /**
     * Sets the initial value of the text input.
     *
     * @param initialSupplier the supplier to get the initial value
     * @return the builder
     */
    TextInputTemplate initial(Supplier<String> initialSupplier);

    /**
     * Sets the max length of the text input.
     *
     * @param maxLength the max length
     * @return the builder
     */
    TextInputTemplate maxLength(int maxLength);

    /**
     * Sets the text input to be multi-line.
     *
     * @param maxLines the max lines
     * @param height   the height of the text input
     * @return the builder
     */
    TextInputTemplate multiLine(int maxLines, int height);
}
