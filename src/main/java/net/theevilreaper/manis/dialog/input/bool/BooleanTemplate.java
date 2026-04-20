package net.theevilreaper.manis.dialog.input.bool;

import net.theevilreaper.manis.dialog.input.InputTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * The interface defines all relevant methods to create a boolean input of a dialog.
 *
 * @author theEvilReaper
 * @author 1.0.1
 * @see net.minestom.server.dialog.DialogInput.Boolean
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public sealed interface BooleanTemplate extends InputTemplate<BooleanInputBuilder> permits BooleanInputBuilder {

    /**
     * Creates a new builder reference of the {@link BooleanTemplate} class.
     *
     * @param key the key of the input
     * @return the created builder instance.
     */
    @Contract(pure = true)
    static BooleanTemplate builder(String key) {
        return new BooleanInputBuilder(key);
    }

    /**
     * Sets the initial value of the input.
     *
     * @param initialValue the initial value
     * @return the builder
     */
    BooleanTemplate initialValue(boolean initialValue);

    /**
     * Set the value which is used when the option is true.
     *
     * @param onTrue the value to set
     * @return the builder
     */
    BooleanTemplate onTrue(String onTrue);

    /**
     * Set the value which is used when the option is false.
     *
     * @param onFalse the value to set
     * @return the builder
     */
    BooleanTemplate onFalse(String onFalse);
}
