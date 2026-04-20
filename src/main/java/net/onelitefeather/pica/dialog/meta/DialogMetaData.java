package net.onelitefeather.pica.dialog.meta;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.dialog.DialogBody;
import net.minestom.server.dialog.DialogInput;
import net.minestom.server.dialog.DialogMetadata;
import net.onelitefeather.pica.dialog.display.component.ComponentTemplate;
import net.onelitefeather.pica.dialog.display.item.ItemTemplate;
import net.onelitefeather.pica.dialog.input.text.TextInputTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The {@link DialogMetaData} is responsible for building the {@link DialogMetadata} object.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 0.1.0
 */
public final class DialogMetaData implements DialogMeta {

    private final List<DialogBody> bodyList;
    private final List<DialogInput> inputList;

    private Component title;
    private Component externalTitle;
    private DialogAfterAction afterAction;

    private boolean closeWithEscape;
    private boolean pause;

    /**
     * Creates a new {@link DialogMetaData} instance.
     */
    public DialogMetaData() {
        this.afterAction = DialogAfterAction.NONE;
        this.title = Component.empty();
        this.externalTitle = Component.empty();
        this.bodyList = new ArrayList<>();
        this.inputList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta title(Component title) {
        this.title = title;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta externalTitle(@Nullable Component externalTitle) {
        this.externalTitle = externalTitle;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta closeWithEscape(boolean closeWithEscape) {
        this.closeWithEscape = closeWithEscape;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta pause(boolean pause) {
        this.pause = pause;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta afterAction(DialogAfterAction action) {
        this.afterAction = action;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta messageBody(Consumer<ComponentTemplate> bodyBuilder) {
        ComponentTemplate body = ComponentTemplate.builder();
        bodyBuilder.accept(body);
        this.bodyList.add(body.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta emptyMessage() {
        this.bodyList.add(EMPTY);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta itemBody(Consumer<ItemTemplate> template) {
        ItemTemplate body = ItemTemplate.builder();
        template.accept(body);
        this.bodyList.add(body.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta text(String key, Consumer<TextInputTemplate> template) {
        TextInputTemplate body = TextInputTemplate.builder(key);
        template.accept(body);
        this.inputList.add(body.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta body(List<DialogBody> bodyList) {
        this.bodyList.addAll(bodyList);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMeta input(List<DialogInput> inputList) {
        this.inputList.addAll(inputList);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DialogMetadata build() {
        return new DialogMetadata(this.title, this.externalTitle, this.closeWithEscape, this.pause, this.afterAction, this.bodyList, this.inputList);
    }
}
