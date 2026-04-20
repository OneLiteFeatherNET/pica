package net.theevilreaper.manis.dialog.input.text;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import net.theevilreaper.manis.dialog.DialogConstants;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation of the {@link TextInputTemplate} interface.
 *
 * @author theEvilReaper
 * @version 1.0.2
 * @see TextInputTemplate
 * @since 1.0.0
 */
public final class TextInputBuilder implements TextInputTemplate {

    private static final int MAX_LENGTH = 32;

    private final String key;

    private int width;
    private int maxLength;
    private boolean labelVisibility;
    private String initial;
    private DialogInput.Text.Multiline multiline;
    private Component label;

    /**
     * Creates a new {@link TextInputBuilder} instance.
     *
     * @param key the key of the text input
     */
    TextInputBuilder(String key) {
        this.key = key;
        this.width = DialogConstants.DEFAULT_WIDTH;
        this.maxLength = MAX_LENGTH;
        this.labelVisibility = true;
        this.label = Component.empty();
        this.initial = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate width(int width) {
        DialogConstants.validateWidth(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate activeLabel(boolean label) {
        this.labelVisibility = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate initial(String initial) {
        this.initial = initial;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate initial(Supplier<String> initialSupplier) {
        this.initial = initialSupplier.get();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate multiLine(int maxLines, int height) {
        DialogConstants.validateHeight(height);
        this.multiline = new DialogInput.Text.Multiline(maxLines, height);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextInputTemplate label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogInput build() {
        return new DialogInput.Text(
                this.key,
                this.width,
                this.label,
                this.labelVisibility,
                this.initial,
                this.maxLength,
                this.multiline
        );
    }
}
