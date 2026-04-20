package net.onelitefeather.pica.dialog.input.range;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;
import net.onelitefeather.pica.dialog.DialogConstants;

/**
 * The {@link RangeTemplateBuilder} represents an implementation of the {@link RangeTemplate} interface.
 * To avoid invalid values, some methods have validation checks.
 *
 * @author theEvilReaper
 * @version 1.0.1
 * @since 1.0.0
 */
public final class RangeTemplateBuilder implements RangeTemplate {

    private final String key;
    private int width;
    private Component label;
    private String labelFormat;
    private float start;
    private float end;
    private float initial;
    private float step;

    /**
     * Creates a new {@link RangeTemplateBuilder} instance.
     */
    RangeTemplateBuilder(String key) {
        this.key = key;
        this.label = Component.empty();
        this.labelFormat = "options.generic_value";
        this.width = DialogConstants.DEFAULT_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate labelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate width(int width) {
        DialogConstants.validateWidth(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate start(float start) {
        DialogConstants.validateRangeStart(start);
        this.start = start;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate end(float end) {
        DialogConstants.validateRangeEnd(end);
        if (end < this.start) {
            throw new IllegalArgumentException("End must be greater than start");
        }
        this.end = end;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate initial(float initial) {
        this.initial = initial;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate step(float step) {
        this.step = step;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTemplate label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogInput build() {
        return new DialogInput.NumberRange(
                this.key,
                this.width,
                this.label,
                this.labelFormat,
                this.start,
                this.end,
                this.initial,
                this.step
        );
    }
}
