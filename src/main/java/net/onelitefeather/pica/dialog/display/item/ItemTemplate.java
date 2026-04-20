package net.onelitefeather.pica.dialog.display.item;

import net.minestom.server.item.ItemStack;
import net.onelitefeather.pica.dialog.DialogConstants;
import net.onelitefeather.pica.dialog.display.BodyTemplate;
import net.onelitefeather.pica.dialog.display.component.ComponentTemplate;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

/**
 * The {@link ItemTemplate} represents the item display option of a dialog.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.0.0
 */
public interface ItemTemplate extends BodyTemplate {

    @Contract(pure = true)
    static ItemTemplate builder() {
        return new ItemTemplateBuilder();
    }

    /**
     * Sets the item of the body.
     *
     * @param item the item to set
     * @return the builder
     */
    ItemTemplate item(ItemStack item);

    /**
     * Sets the description of the body.
     *
     * @param messageBuilder the message builder
     * @return the builder
     */
    ItemTemplate description(Consumer<ComponentTemplate> messageBuilder);

    /**
     * Sets the decoration of the body.
     *
     * @param decoration true if decoration should be applied, false otherwise
     * @return the builder
     */
    ItemTemplate showDecoration(boolean decoration);

    /**
     * Sets the tool tip of the body.
     *
     * @param toolTip the tool tip
     * @return the builder
     */
    ItemTemplate showToolTip(boolean toolTip);

    /**
     * Sets the width of the body.
     * If the value is lower than one, it will throw an exception
     *
     * @param width the width to set
     * @return the builder
     * @throws IllegalArgumentException if the width is incorrect
     * @see DialogConstants#validateItemTemplateDimension
     */
    ItemTemplate width(int width);

    /**
     * Sets the height of the body.
     * If the value is lower than one, it will throw an exception
     *
     * @param height the height to set
     * @return the builder
     * @throws IllegalArgumentException if the width is incorrect
     * @see DialogConstants#validateItemTemplateDimension
     */
    ItemTemplate height(int height);
}
