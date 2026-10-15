package net.onelitefeather.pica.dialog.tag;

import net.kyori.adventure.key.Key;
import net.minestom.server.dialog.Dialog;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.common.TagsPacket;
import net.minestom.server.registry.RegistryKey;
import net.minestom.testing.Collector;
import net.minestom.testing.Env;
import net.minestom.testing.TestConnection;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class DialogTagRegistryIntegrationTest {

    private static final String DIALOG_REGISTRY = "minecraft:dialog";
    private static final String QUICK_ACTIONS = "minecraft:quick_actions";
    private static final String PAUSE_SCREEN_ADDITIONS = "minecraft:pause_screen_additions";

    @Test
    void firstSpawnSendsQuickActionIdAsLastDialogTagsPacket(Env env) {
        DialogTagRegistry tagRegistry = DialogTagRegistry.of(env.process().dialog());
        RegistryKey<Dialog> key = tagRegistry.addQuickAction(Key.key("test:quick"), DialogFixtures.confirmation("Quick"));
        env.process().eventHandler().addChild(tagRegistry.eventNode());

        int expectedId = env.process().dialog().getId(key);
        List<TagsPacket> dialogTags = joinAndCollectDialogTags(env);

        assertLastDialogTagsContain(dialogTags, QUICK_ACTIONS, expectedId);
    }

    @Test
    void firstSpawnSendsPauseScreenIdAsLastDialogTagsPacket(Env env) {
        DialogTagRegistry tagRegistry = DialogTagRegistry.of(env.process().dialog());
        RegistryKey<Dialog> key = tagRegistry.addPauseScreenAddition(Key.key("test:pause"), DialogFixtures.confirmation("Pause"));
        env.process().eventHandler().addChild(tagRegistry.eventNode());

        int expectedId = env.process().dialog().getId(key);
        List<TagsPacket> dialogTags = joinAndCollectDialogTags(env);

        assertLastDialogTagsContain(dialogTags, PAUSE_SCREEN_ADDITIONS, expectedId);
    }

    /**
     * Connects a player and returns every received {@link TagsPacket} that carries the dialog registry, in arrival order.
     */
    private static List<TagsPacket> joinAndCollectDialogTags(Env env) {
        Instance instance = env.createFlatInstance();
        TestConnection connection = env.createConnection();
        Collector<TagsPacket> tags = connection.trackIncoming(TagsPacket.class);
        connection.connect(instance);
        env.tick();

        List<TagsPacket> dialogTags = tags.collect().stream()
                .filter(DialogTagRegistryIntegrationTest::hasDialogRegistry)
                .toList();
        env.destroyInstance(instance, true);
        return dialogTags;
    }

    /**
     * Asserts that the last dialog tags packet received in play state lists the expected id.
     *
     * <p>The client applies tags in arrival order, so this packet must follow Minestom's configuration packet with
     * empty dialog tags. The testing module does not expose configuration-phase packets (its connection never
     * reports the configuration server state, so they are dropped before tracking), which is why only the play-state
     * packets can be inspected here.</p>
     */
    private static void assertLastDialogTagsContain(List<TagsPacket> dialogTags, String identifier, int expectedId) {
        assertFalse(dialogTags.isEmpty(), "Player should receive a dialog tags packet after the first spawn");
        assertTrue(
                containsEntry(dialogTags.getLast(), identifier, expectedId),
                "The last dialog tags packet should list id " + expectedId + " in " + identifier
        );
    }

    private static boolean hasDialogRegistry(TagsPacket packet) {
        return packet.registries().stream().anyMatch(registry -> registry.registry().equals(DIALOG_REGISTRY));
    }

    private static boolean containsEntry(TagsPacket packet, String identifier, int id) {
        return packet.registries().stream()
                .filter(registry -> registry.registry().equals(DIALOG_REGISTRY))
                .flatMap(registry -> registry.tags().stream())
                .filter(tag -> tag.identifier().equals(identifier))
                .anyMatch(tag -> {
                    for (int entry : tag.entries()) {
                        if (entry == id) return true;
                    }
                    return false;
                });
    }
}
