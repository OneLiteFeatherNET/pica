package net.onelitefeather.pica.dialog.tag;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.network.packet.server.common.TagsPacket;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.registry.RegistryKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DialogTagRegistryTest {

    private static final String DIALOG_REGISTRY = "minecraft:dialog";
    private static final String QUICK_ACTIONS = "minecraft:quick_actions";
    private static final String PAUSE_SCREEN_ADDITIONS = "minecraft:pause_screen_additions";

    private DynamicRegistry<Dialog> dialogRegistry;
    private DialogTagRegistry tagRegistry;

    @BeforeEach
    void setUp() {
        this.dialogRegistry = DynamicRegistry.create(Key.key("minecraft:dialog"));
        this.tagRegistry = DialogTagRegistry.of(dialogRegistry);
    }

    @Test
    void createTagsPacketContainsOnlyTheDialogRegistry() {
        TagsPacket packet = tagRegistry.createTagsPacket();

        assertEquals(1, packet.registries().size(), "Tags packet should contain exactly one registry");
        assertEquals(DIALOG_REGISTRY, packet.registries().getFirst().registry(), "Tags packet should target the dialog registry");
    }

    @Test
    void createTagsPacketHasEmptyEntriesForBothTagsWhenNoDialogWasAdded() {
        TagsPacket packet = tagRegistry.createTagsPacket();

        assertEquals(0, entries(packet, QUICK_ACTIONS).length, "Quick actions tag should be empty without dialogs");
        assertEquals(0, entries(packet, PAUSE_SCREEN_ADDITIONS).length, "Pause screen tag should be empty without dialogs");
    }

    @Test
    void quickActionTagContainsIdOfAddedDialog() {
        RegistryKey<Dialog> key = tagRegistry.addQuickAction(Key.key("test:quick"), DialogFixtures.confirmation("Quick"));

        int[] quickActions = entries(tagRegistry.createTagsPacket(), QUICK_ACTIONS);

        assertArrayEquals(new int[]{dialogRegistry.getId(key)}, quickActions, "Quick actions tag should contain the registry id of the added dialog");
    }

    @Test
    void pauseScreenTagContainsIdOfAddedDialog() {
        RegistryKey<Dialog> key = tagRegistry.addPauseScreenAddition(Key.key("test:pause"), DialogFixtures.confirmation("Pause"));

        int[] pauseScreen = entries(tagRegistry.createTagsPacket(), PAUSE_SCREEN_ADDITIONS);

        assertArrayEquals(new int[]{dialogRegistry.getId(key)}, pauseScreen, "Pause screen tag should contain the registry id of the added dialog");
    }

    @Test
    void dialogAddedToQuickActionsIsNotInPauseScreenTag() {
        tagRegistry.addQuickAction(Key.key("test:quick"), DialogFixtures.confirmation("Quick"));

        int[] pauseScreen = entries(tagRegistry.createTagsPacket(), PAUSE_SCREEN_ADDITIONS);

        assertEquals(0, pauseScreen.length, "A dialog added to quick actions must not appear in the pause screen tag");
    }

    @Test
    void addQuickActionRegistersDialogInDialogRegistry() {
        Dialog dialog = DialogFixtures.confirmation("Quick");
        RegistryKey<Dialog> key = tagRegistry.addQuickAction(Key.key("test:quick"), dialog);

        assertSame(dialog, dialogRegistry.get(key), "addQuickAction should register the dialog in the dialog registry");
    }

    @Test
    void entriesFollowInsertionOrder() {
        RegistryKey<Dialog> second = tagRegistry.addQuickAction(Key.key("test:b"), DialogFixtures.confirmation("B"));
        RegistryKey<Dialog> first = tagRegistry.addQuickAction(Key.key("test:a"), DialogFixtures.confirmation("A"));

        int[] quickActions = entries(tagRegistry.createTagsPacket(), QUICK_ACTIONS);

        assertArrayEquals(
                new int[]{dialogRegistry.getId(second), dialogRegistry.getId(first)},
                quickActions,
                "Entries should be listed in the order the dialogs were added"
        );
    }

    @Test
    void addingSameDialogTwiceListsItOnce() {
        Key key = Key.key("test:quick");
        RegistryKey<Dialog> registryKey = tagRegistry.addQuickAction(key, DialogFixtures.confirmation("Quick"));
        tagRegistry.addQuickAction(key, DialogFixtures.confirmation("Quick again"));

        int[] quickActions = entries(tagRegistry.createTagsPacket(), QUICK_ACTIONS);

        assertArrayEquals(new int[]{dialogRegistry.getId(registryKey)}, quickActions, "A dialog must appear only once in a tag");
    }

    private static int[] entries(TagsPacket packet, String identifier) {
        List<TagsPacket.Tag> tags = packet.registries().getFirst().tags();
        return tags.stream()
                .filter(tag -> tag.identifier().equals(identifier))
                .findFirst()
                .map(TagsPacket.Tag::entries)
                .orElseThrow(() -> new AssertionError("Dialog tags packet is missing the tag " + identifier));
    }
}
