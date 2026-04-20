package net.theevilreaper.manis.dialog.display.item;

import net.minestom.server.dialog.DialogBody;
import net.minestom.server.item.ItemStack;
import net.theevilreaper.manis.dialog.DialogConstants;
import net.theevilreaper.manis.dialog.display.component.ComponentTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * The class represents the implementation of the {@link ItemTemplate} interface.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ItemTemplateBuilder implements ItemTemplate {

    private static final int DEFAULT_WIDTH = 16;
    private static final int DEFAULT_HEIGHT = 16;

    private @Nullable ItemStack item;
    private @Nullable DialogBody message;
    private boolean decoration;
    private boolean toolTip;
    private int width;
    private int height;

    ItemTemplateBuilder() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplateBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplateBuilder description(Consumer<ComponentTemplate> messageBuilder) {
        ComponentTemplate builder = ComponentTemplate.builder();
        messageBuilder.accept(builder);
        this.message = builder.build();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplateBuilder showDecoration(boolean decoration) {
        this.decoration = decoration;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplateBuilder showToolTip(boolean toolTip) {
        this.toolTip = toolTip;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplate width(int width) {
        DialogConstants.validateItemTemplateDimension(width);
        this.width = width;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemTemplate height(int height) {
        DialogConstants.validateItemTemplateDimension(height);
        this.height = height;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogBody build() {
        Objects.requireNonNull(this.item, "Item must not be null");
        return new DialogBody.Item(
                this.item, ((DialogBody.PlainMessage) this.message), this.decoration, this.toolTip, this.width, this.height
        );
    }
}
