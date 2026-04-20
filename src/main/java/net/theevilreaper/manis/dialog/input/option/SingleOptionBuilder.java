package net.theevilreaper.manis.dialog.input.option;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import net.theevilreaper.manis.dialog.DialogConstants;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SingleOptionBuilder implements SingleOptionTemplate {

    private final String key;
    private Component label;
    private int width;
    private boolean labelVisibility;
    private final List<DialogInput.SingleOption.Option> options;

    SingleOptionBuilder(String key) {
        this.key = key;
        this.label = Component.empty();
        this.width = DialogConstants.DEFAULT_WIDTH;
        this.labelVisibility = true;
        this.options = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleOptionTemplate with(int width) {
        DialogConstants.validateWidth(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleOptionTemplate labelVisibility(boolean visible) {
        this.labelVisibility = visible;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleOptionTemplate option(DialogInput.SingleOption.Option option) {
        this.options.add(option);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleOptionTemplate label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogInput build() {
        if (this.options.isEmpty()) {
            throw new IllegalStateException("No options have been added. Required at least one option.");
        }
        return new DialogInput.SingleOption(
                this.key,
                this.width,
                this.options,
                this.label,
                this.labelVisibility
        );
    }
}
