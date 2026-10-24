package net.onelitefeather.pica.dialog.type;

import net.kyori.adventure.key.Key;
import net.onelitefeather.pica.dialog.DialogTemplate;
import net.onelitefeather.pica.dialog.meta.DialogMeta;
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
     * Creates a new {@link NoticeDialog} builder for building a dialog that shows a message behind
     * a single action button.
     *
     * @param key the key identifying the dialog
     * @return a new notice dialog builder
     */
    @Contract(pure = true)
    static NoticeDialog notice(Key key) {
        return new NotificationDialog(key);
    }

    /**
     * Creates a new {@link MultiActionDialog} builder for building a dialog that lays out several
     * action buttons in a grid.
     *
     * @param key the key identifying the dialog
     * @return a new multi-action dialog builder
     */
    @Contract(pure = true)
    static MultiActionDialog multiAction(Key key) {
        return new MultipleActionDialog(key);
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
