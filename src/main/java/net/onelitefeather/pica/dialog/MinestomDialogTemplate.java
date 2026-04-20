package net.onelitefeather.pica.dialog;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.network.packet.server.common.ShowDialogPacket;
import net.onelitefeather.pica.dialog.event.PlayerOpenDialogEvent;
import org.jetbrains.annotations.NotNull;

public class MinestomDialogTemplate implements DialogTemplate {

    protected final Key key;
    protected final Dialog dialog;

    /**
     * Creates a new {@link MinestomDialogTemplate} instance.
     *
     * @param key    the key of the dialog template
     * @param dialog the dialog to open
     */
    protected MinestomDialogTemplate(
            @NotNull Key key,
            @NotNull Dialog dialog
    ) {
        this.key = key;
        this.dialog = dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open(@NotNull Player player) {
        EventDispatcher.callCancellable(new PlayerOpenDialogEvent(player, key), () -> {
            ShowDialogPacket packet = new ShowDialogPacket(dialog);
            player.sendPacket(packet);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Key key() {
        return this.key;
    }
}
