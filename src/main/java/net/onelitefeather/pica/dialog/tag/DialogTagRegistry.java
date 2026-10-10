package net.onelitefeather.pica.dialog.tag;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.network.packet.server.common.TagsPacket;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.registry.RegistryKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Registers dialogs for the Quick Actions menu and the pause screen and sends the matching dialog tags to players.
 *
 * <p>Minestom sends the dialog registry tags {@code minecraft:quick_actions} and
 * {@code minecraft:pause_screen_additions} during the configuration phase, but always empty, and the tags
 * exposed by {@link net.minestom.server.registry.Registry#getOrCreateTag} are read-only. This registry keeps its own
 * membership lists and sends them as a {@link TagsPacket} in the play state, after the player has spawned. The
 * packet only replaces the dialog tags it contains, so the rest of the client's registry data stays untouched.</p>
 *
 * <p>Dialogs must be added before the server starts. Minestom freezes the dialog registry once the server is
 * running, and the registry data (which contains the dialogs) is only sent to players during the configuration
 * phase. Dialogs added to a running server are therefore unknown to the client.</p>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * DialogTagRegistry tags = DialogTagRegistry.of(MinecraftServer.getDialogRegistry());
 * tags.addQuickAction(Key.key("myserver:menu"), dialog);
 * MinecraftServer.getGlobalEventHandler().addChild(tags.eventNode());
 * }</pre>
 */
public interface DialogTagRegistry {

    /**
     * Creates a new {@link DialogTagRegistry} which operates on the given dialog registry.
     *
     * @param dialogRegistry the dialog registry the dialogs are registered in
     * @return the created instance
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull DialogTagRegistry of(@NotNull DynamicRegistry<Dialog> dialogRegistry) {
        return new DefaultDialogTagRegistry(dialogRegistry);
    }

    /**
     * Registers a dialog and adds it to the {@code minecraft:quick_actions} tag.
     *
     * <p>If the key is already registered, the dialog replaces the previous value. Adding the same key twice to the
     * same tag keeps a single entry.</p>
     *
     * @param key    the key the dialog is registered under
     * @param dialog the dialog to register
     * @return the key of the registered dialog
     * @throws UnsupportedOperationException if the dialog registry is already frozen (the server has started)
     */
    @NotNull RegistryKey<Dialog> addQuickAction(@NotNull Key key, @NotNull Dialog dialog);

    /**
     * Registers a dialog and adds it to the {@code minecraft:pause_screen_additions} tag.
     *
     * <p>If the key is already registered, the dialog replaces the previous value. Adding the same key twice to the
     * same tag keeps a single entry.</p>
     *
     * @param key    the key the dialog is registered under
     * @param dialog the dialog to register
     * @return the key of the registered dialog
     * @throws UnsupportedOperationException if the dialog registry is already frozen (the server has started)
     */
    @NotNull RegistryKey<Dialog> addPauseScreenAddition(@NotNull Key key, @NotNull Dialog dialog);

    /**
     * Creates a {@link TagsPacket} which contains only the {@code minecraft:dialog} registry with the
     * {@code minecraft:quick_actions} and {@code minecraft:pause_screen_additions} tags.
     *
     * <p>The tag entries are the numeric registry ids of the dialogs, in the order they were added.</p>
     *
     * @return the tags packet
     */
    @NotNull TagsPacket createTagsPacket();

    /**
     * Sends the dialog tags to the given player again.
     *
     * <p>Players receive the tags automatically on their first spawn through {@link #eventNode()}. Call this method
     * after a player was reconfigured: the registry data sent during reconfiguration makes the client forget all tags,
     * and the first-spawn listener does not run again. Dialogs cannot be added at runtime, so the tags never change
     * while the server runs.</p>
     *
     * @param player the player to send the tags to
     */
    void sendTags(@NotNull Player player);

    /**
     * Returns the event node which sends the dialog tags when a player spawns for the first time.
     *
     * <p>The node must be added as a child of the global event handler, for example with
     * {@code MinecraftServer.getGlobalEventHandler().addChild(tagRegistry.eventNode())}. The same node is returned on
     * every call.</p>
     *
     * @return the event node
     */
    @NotNull EventNode<PlayerEvent> eventNode();
}
