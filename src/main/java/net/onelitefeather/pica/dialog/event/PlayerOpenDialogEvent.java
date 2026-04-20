package net.onelitefeather.pica.dialog.event;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;

/**
 * The {@link PlayerOpenDialogEvent} is called when a player opens a dialog.
 * It can be canceled to prevent the dialog from opening.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.1.0
 */
public class PlayerOpenDialogEvent implements PlayerEvent, CancellableEvent {

    private final Player player;
    private final Key key;
    private boolean cancelled;

    /**
     * Creates a new {@link PlayerOpenDialogEvent} instance.
     *
     * @param player the player who opened the dialog
     * @param key    the key of the dialog template
     */
    public PlayerOpenDialogEvent(Player player, Key key) {
        this.player = player;
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns the key which is associated with the dialog template.
     *
     * @return the dialog template key
     */
    public Key getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }
}
