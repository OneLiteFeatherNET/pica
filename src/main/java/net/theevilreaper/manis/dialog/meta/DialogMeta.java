package net.theevilreaper.manis.dialog.meta;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.dialog.DialogBody;
import net.minestom.server.dialog.DialogInput;
import net.minestom.server.dialog.DialogMetadata;
import net.theevilreaper.manis.dialog.display.component.ComponentTemplate;
import net.theevilreaper.manis.dialog.display.item.ItemTemplate;
import net.theevilreaper.manis.dialog.input.text.TextInputTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * The {@link DialogMeta} is responsible for building the {@link DialogMetadata} object for a {@link net.minestom.server.dialog.Dialog}.
 *
 * @author theEvilReaper
 * @version 1.0.1
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public sealed interface DialogMeta permits DialogMetaData {

    DialogBody EMPTY = ComponentTemplate.builder().contents(Component.empty()).build();

    /**
     * Sets the title of the dialog.
     *
     * @param title the title to set
     * @return the builder
     */
    DialogMeta title(Component title);

    /**
     * Sets the external title of the dialog.
     *
     * @param externalTitle the external title
     * @return the builder
     */
    DialogMeta externalTitle(@Nullable Component externalTitle);

    /**
     * Set if the dialog should close with escape.
     *
     * @param closeWithEscape toggle escape close
     * @return the builder
     */
    DialogMeta closeWithEscape(boolean closeWithEscape);

    /**
     * Set the pause state of the dialog.
     *
     * @param pause the pause state
     * @return the builder
     */
    DialogMeta pause(boolean pause);

    /**
     * Set the after action of the dialog.
     *
     * @param action the action to set
     * @return the builder
     */
    DialogMeta afterAction(DialogAfterAction action);

    /**
     * Add a {@link ComponentTemplate} to the dialog meta.
     *
     * @param bodyBuilder the builder to configure the body
     * @return the builder
     */
    DialogMeta messageBody(Consumer<ComponentTemplate> bodyBuilder);

    /**
     * Sets an empty message body to the dialog meta.
     */
    DialogMeta emptyMessage();

    /**
     * Add an {@link ItemTemplate} to the dialog meta.
     *
     * @param template the item template to add
     * @return the builder
     */
    DialogMeta itemBody(Consumer<ItemTemplate> template);

    /**
     * Set a list of {@link DialogBody}s to the dialog meta.
     *
     * @param bodyList the list of {@link DialogBody}s to set
     * @return the builder
     */
    DialogMeta body(List<DialogBody> bodyList);

    /**
     * Add a {@link TextInputTemplate} to the dialog meta.
     *
     * @param key      the key of the text input
     * @param template the template to add
     * @return the builder
     */
    DialogMeta text(String key, Consumer<TextInputTemplate> template);

    /**
     * Set a list of {@link DialogInput}s to the dialog meta
     *
     * @param inputList the input list
     * @return the builder
     */
    DialogMeta input(List<DialogInput> inputList);

    /**
     * Returns a new {@link DialogMetadata} instance based on the configured values.
     *
     * @return the built {@link DialogMetadata} instance
     */
    DialogMetadata build();
}
