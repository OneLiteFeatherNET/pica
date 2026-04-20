package net.onelitefeather.pica.dialog.input.bool;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogInput;

public final class BooleanInputBuilder implements BooleanTemplate {

    private final String key;
    private Component label;
    private boolean initial;
    private String onTrue;
    private String onFalse;

    BooleanInputBuilder(String key) {
        this.key = key;
        this.label = Component.empty();
        this.onTrue = "true";
        this.onFalse = "false";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanInputBuilder label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanTemplate initialValue(boolean initialValue) {
        this.initial = initialValue;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanTemplate onTrue(String onTrue) {
        this.onTrue = onTrue;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanTemplate onFalse(String onFalse) {
        this.onFalse = onFalse;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogInput build() {
        return new DialogInput.Boolean(
                this.key, this.label, this.initial, this.onTrue, this.onFalse
        );
    }
}
