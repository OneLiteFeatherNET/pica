package net.theevilreaper.manis.dialog.type;

import net.kyori.adventure.key.Key;
import net.theevilreaper.manis.dialog.DialogTemplate;
import net.theevilreaper.manis.dialog.meta.DialogMeta;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public interface DialogType {

    /**
     * Creates a new {@link ConfirmDialog} builder for building a confirmation dialog.
     *
     * @param key the key identifying the dialog
     * @return a new confirmation dialog builder
     */
    @Contract(pure = true)
    static ConfirmDialog confirm(Key key) {
        return new ConfirmationDialog(key);
    }

    /**
     * Configures the meta-data of the dialog.
     *
     * @param consumer the consumer to configure the meta-data
     * @return this builder for chaining
     */
    DialogType meta(Consumer<DialogMeta> consumer);

    /**
     * Builds an immutable {@link DialogTemplate} from the configured values.
     *
     * @return the created dialog template
     */
    DialogTemplate build();
}
