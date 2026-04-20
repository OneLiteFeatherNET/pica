package net.onelitefeather.pica.dialog.event;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Collector;
import net.minestom.testing.Env;
import net.minestom.testing.TestConnection;
import net.minestom.testing.extension.MicrotusExtension;
import net.onelitefeather.pica.dialog.DialogTemplateBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class PlayerOpenDialogEventIntegrationTest extends DialogTemplateBase {

    @Test
    void testEventLifecycle(Env env) {
        Instance instance = env.createFlatInstance();
        TestConnection connection = env.createConnection();
        Player player = connection.connect(instance);

        env.listen(PlayerOpenDialogEvent.class);
        Collector<PlayerOpenDialogEvent> eventTracker = env.trackEvent(PlayerOpenDialogEvent.class, EventFilter.PLAYER, player);

        testTemplate.open(player);

        eventTracker.assertSingle();
        eventTracker.assertSingle(event -> {
            assertEquals(player, event.getPlayer(), "Player mismatch in event");
            assertEquals(Key.key("test:test"), event.getKey(), "Dialog key mismatch in event");
        });
        env.destroyInstance(instance, true);
    }

    @Test
    void testCancelledEventLifecycle(Env env) {
        Instance instance = env.createFlatInstance();
        TestConnection connection = env.createConnection();
        Player player = connection.connect(instance);

        env.process().eventHandler().addListener(PlayerOpenDialogEvent.class, event -> event.setCancelled(true));

        env.listen(PlayerOpenDialogEvent.class);
        Collector<PlayerOpenDialogEvent> eventTracker = env.trackEvent(PlayerOpenDialogEvent.class, EventFilter.PLAYER, player);

        testTemplate.open(player);

        eventTracker.assertEmpty();
        env.destroyInstance(instance, true);
    }
}
