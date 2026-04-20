package net.theevilreaper.manis.dialog.input;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;

/**
 * The {@link InputTemplate} defines which methods an input field of dialog needs to work.
 *
 * @param <T> the generic value
 * @version 1.0.1
 * @since 0.1.0
 * @author theEvilReaper
 */
public interface InputTemplate<T> {

    /**
     * Set the label of the input.
     *
     * @param label the label to set
     * @return the builder
     */
    T label(Component label);

    /**
     * Builds the input.
     *
     * @return the built input
     */
    DialogInput build();
}
