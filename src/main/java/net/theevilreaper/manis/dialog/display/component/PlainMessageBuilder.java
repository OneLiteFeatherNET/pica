package net.theevilreaper.manis.dialog.display.component;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogBody;
import net.theevilreaper.manis.dialog.DialogConstants;

import java.util.Objects;

/**
 * The {@link PlainMessageBuilder} represents an implementation of the {@link ComponentTemplate} interface.
 * It provides the default structure to create a {@link DialogBody.PlainMessage} for a dialog.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PlainMessageBuilder implements ComponentTemplate {

    private Component contents;
    private int width;

    PlainMessageBuilder() {
        this.width = DialogConstants.DEFAULT_WIDTH;
        this.contents = Component.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentTemplate contents(Component message) {
        this.contents = message;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentTemplate width(int width) {
        DialogConstants.validateWidth(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogBody build() {
        return new DialogBody.PlainMessage(this.contents, this.width);
    }
}
