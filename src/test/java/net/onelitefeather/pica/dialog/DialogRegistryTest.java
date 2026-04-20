package net.onelitefeather.pica.dialog;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DialogRegistryTest extends DialogTemplateBase {

    private DialogRegistry registry;

    @BeforeEach
    void initRegistry() {
        registry = DialogRegistry.of();
        assertNotNull(registry, "The registry can not be null");
    }

    @Test
    void testTemplateRegistration() {
        this.registry.add(testTemplate);
        assertTrue(registry.contains(testTemplate.key()));
        assertEquals(testTemplate, registry.get(testTemplate.key()));
        assertTrue(registry.contains(Key.key("test:test")));
    }

    @Test
    void testTemplateRemoval() {
        assertNull(this.registry.remove(Key.key("test:test")));
        this.registry.add(testTemplate);
        assertNotNull(this.registry.remove(testTemplate.key()));
    }

    @Test
    void testTemplateGetOrDefault() {
        assertNotNull(this.registry.get(Key.key("test:test2"), testTemplate));
        assertNull(this.registry.get(Key.key("test:test2"), null));

    }
}
