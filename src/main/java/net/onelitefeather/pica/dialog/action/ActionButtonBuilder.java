package net.onelitefeather.pica.dialog.action;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAction;
import net.minestom.server.dialog.DialogActionButton;
import net.onelitefeather.pica.dialog.DialogConstants;
import org.jetbrains.annotations.Nullable;

public final class ActionButtonBuilder implements ActionButton {

    private Component label;
    private Component toolTip;
    private int width;
    private @Nullable DialogAction action;

    ActionButtonBuilder() {
        this.label = Component.empty();
        this.toolTip = Component.empty();
        this.width = DialogConstants.DEFAULT_ACTION_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton tooltip(Component tooltip) {
        this.toolTip = tooltip;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton width(int width) {
        DialogConstants.validateWidth(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton action(DialogAction action) {
        this.action = action;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogActionButton build() {
        return new DialogActionButton(this.label, this.toolTip, this.width, this.action);
    }
}
