package net.theevilreaper.manis.dialog;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DefaultDialogRegistry implements DialogRegistry {

    private final Map<Key, DialogTemplate> dialogMap;

    /**
     * Creates a new {@link DefaultDialogRegistry} instance.
     */
    protected DefaultDialogRegistry() {
        this.dialogMap = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@NotNull DialogTemplate template) {
        this.dialogMap.put(template.key(), template);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable DialogTemplate remove(@NotNull Key key) {
        return this.dialogMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(@NotNull Key key) {
        return this.dialogMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable DialogTemplate get(@NotNull Key key, @Nullable DialogTemplate defaultValue) {
        return this.dialogMap.getOrDefault(key, defaultValue);
    }
}
