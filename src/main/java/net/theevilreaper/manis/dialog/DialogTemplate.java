package net.theevilreaper.manis.dialog;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface DialogTemplate {

    /**
     * Creates a new immutable {@link DialogTemplate} from the given key and dialog.
     *
     * @param key    the key identifying this dialog
     * @param dialog the dialog to wrap
     * @return a new dialog template
     */
    @Contract(pure = true)
    static @NotNull DialogTemplate of(@NotNull Key key, @NotNull Dialog dialog) {
        return new MinestomDialogTemplate(key, dialog);
    }

    /**
     * Opens the dialog for the given player.
     *
     * @param player the player to open the dialog for
     */
    void open(@NotNull Player player);

    /**
     * Gets the key of this dialog template.
     *
     * @return the given key
     */
    @NotNull Key key();
}
