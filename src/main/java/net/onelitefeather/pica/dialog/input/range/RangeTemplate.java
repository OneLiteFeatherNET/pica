package net.onelitefeather.pica.dialog.input.range;

import net.onelitefeather.pica.dialog.DialogConstants;
import net.onelitefeather.pica.dialog.input.InputTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * The {@link RangeTemplate} represents the template for a range input in a dialog. It provides methods to set the properties of the range input, such as the label format, width, start and end values, initial value, and step size.
 *
 * @author theEvilReaper
 * @version 1.0.1
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public sealed interface RangeTemplate extends InputTemplate<RangeTemplate> permits RangeTemplateBuilder {

    /**
     * Create a new builder instance for the {@link RangeTemplate} to create a {@link net.minestom.server.dialog.DialogInput.NumberRange}.
     *
     * @param key the key of the input
     * @return the builder instance
     */
    @Contract(pure = true, value = "_ -> new")
    static RangeTemplate builder(String key) {
        return new RangeTemplateBuilder(key);
    }

    /**
     * Sets the label format of the slider.
     *
     * @param labelFormat the format string
     * @return the builder
     */
    RangeTemplate labelFormat(String labelFormat);

    /**
     * Sets the width of the slider.
     *
     * @param width the width
     * @return the builder
     */
    RangeTemplate width(int width);

    /**
     * Sets the start value of the slider.
     *
     * @param start the start value
     * @return the builder
     * @throws IllegalArgumentException if the star value is invalid
     * @see DialogConstants#validateRangeStart
     */
    RangeTemplate start(float start);

    /**
     * Sets the end value of the slider.
     *
     * @param end the end value
     * @return the builder
     * @throws IllegalArgumentException if the end value is invalid
     * @see DialogConstants#validateRangeEnd(float)
     */
    RangeTemplate end(float end);

    /**
     * Sets the initial value of the slider.
     *
     * @param initial the initial value
     * @return the builder
     */
    RangeTemplate initial(float initial);

    /**
     * Sets the step size of the slider.
     *
     * @param step the step size
     * @return the builder
     */
    RangeTemplate step(float step);
}
