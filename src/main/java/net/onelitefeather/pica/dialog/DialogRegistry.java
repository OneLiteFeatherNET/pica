package net.onelitefeather.pica.dialog;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DialogRegistry {

    /**
     * Creates a new {@link DialogRegistry} instance.
     *
     * @return the created instance
     */
    @Contract(pure = true)
    static @NotNull DialogRegistry of() {
        return new DefaultDialogRegistry();
    }

    /**
     * Adds a new dialog template to the registry.
     *
     * @param template the template to add
     */
    void add(@NotNull DialogTemplate template);

    /**
     * Removes a dialog template from the registry.
     *
     * @param key the key of the template to remove
     * @return the removed template or null if no template was removed
     */
    @Nullable DialogTemplate remove(@NotNull Key key);

    /**
     * Checks if the registry contains a dialog template for the given key.
     *
     * @param key the key to check
     * @return true if the registry contains a template for the given key, false otherwise
     */
    boolean contains(@NotNull Key key);

    /**
     * Returns the dialog template for the given key.
     *
     * @param key the key to search for
     * @return the dialog template or null if no value matches
     */
    default @Nullable DialogTemplate get(@NotNull Key key) {
        return this.get(key, null);
    }

    /**
     * Returns the dialog template for the given key.
     *
     * @param key             the key to search for
     * @param defaultTemplate the default template to return if no template is found for the given key
     * @return the dialog template or the default template if no template is found for the given key
     */
    @Nullable DialogTemplate get(@NotNull Key key, @Nullable DialogTemplate defaultTemplate);
}
