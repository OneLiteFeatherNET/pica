package net.onelitefeather.pica.dialog.tag;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.dialog.DialogTags;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.network.packet.server.common.TagsPacket;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.registry.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Default implementation of {@link DialogTagRegistry}.
 *
 * <p>Membership is kept in insertion-ordered sets. Registry ids are resolved when the packet is created, so the
 * packet always reflects the current ids of the dialog registry.</p>
 */
final class DefaultDialogTagRegistry implements DialogTagRegistry {

    private static final String DIALOG_REGISTRY = "minecraft:dialog";

    private final DynamicRegistry<Dialog> dialogRegistry;
    private final Set<RegistryKey<Dialog>> quickActions;
    private final Set<RegistryKey<Dialog>> pauseScreenAdditions;
    private final EventNode<PlayerEvent> eventNode;

    /**
     * Creates a new {@link DefaultDialogTagRegistry} instance.
     *
     * @param dialogRegistry the dialog registry the dialogs are registered in
     */
    DefaultDialogTagRegistry(@NotNull DynamicRegistry<Dialog> dialogRegistry) {
        this.dialogRegistry = Objects.requireNonNull(dialogRegistry, "The dialog registry must not be null");
        this.quickActions = new CopyOnWriteArraySet<>();
        this.pauseScreenAdditions = new CopyOnWriteArraySet<>();
        this.eventNode = EventNode.type("pica-dialog-tags", EventFilter.PLAYER);
        this.eventNode.addListener(PlayerSpawnEvent.class, event -> {
            if (event.isFirstSpawn()) {
                this.sendTags(event.getPlayer());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull RegistryKey<Dialog> addQuickAction(@NotNull Key key, @NotNull Dialog dialog) {
        return this.addToTag(this.quickActions, key, dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull RegistryKey<Dialog> addPauseScreenAddition(@NotNull Key key, @NotNull Dialog dialog) {
        return this.addToTag(this.pauseScreenAdditions, key, dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull TagsPacket createTagsPacket() {
        List<TagsPacket.Tag> tags = List.of(
                this.createTag(DialogTags.QUICK_ACTIONS, this.quickActions),
                this.createTag(DialogTags.PAUSE_SCREEN_ADDITIONS, this.pauseScreenAdditions)
        );
        return new TagsPacket(List.of(new TagsPacket.Registry(DIALOG_REGISTRY, tags)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTags(@NotNull Player player) {
        player.sendPacket(this.createTagsPacket());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull EventNode<PlayerEvent> eventNode() {
        return this.eventNode;
    }

    private RegistryKey<Dialog> addToTag(Set<RegistryKey<Dialog>> tag, Key key, Dialog dialog) {
        Objects.requireNonNull(key, "The key must not be null");
        Objects.requireNonNull(dialog, "The dialog must not be null");
        RegistryKey<Dialog> registryKey = this.dialogRegistry.register(key, dialog);
        tag.add(registryKey);
        return registryKey;
    }

    private TagsPacket.Tag createTag(TagKey<Dialog> tagKey, Collection<RegistryKey<Dialog>> keys) {
        // Keys that were removed from the dialog registry in the meantime have no id and are skipped
        int[] entries = keys.stream()
                .mapToInt(this.dialogRegistry::getId)
                .filter(id -> id >= 0)
                .toArray();
        return new TagsPacket.Tag(tagKey.key().asString(), entries);
    }
}
