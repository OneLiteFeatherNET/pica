package net.theevilreaper.manis.dialog;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAction;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.theevilreaper.manis.dialog.type.DialogType;
import org.junit.jupiter.api.BeforeAll;

public abstract class DialogTemplateBase {

    protected static DialogTemplate testTemplate;

    @BeforeAll
    static void init() {
        testTemplate = DialogType.confirm(Key.key("test:test"))
                .meta(metaBuilder ->
                        metaBuilder.title(Component.text("Test title"))
                                .afterAction(DialogAfterAction.CLOSE)
                                .pause(true)
                                .itemBody(itemTemplate -> itemTemplate.item(ItemStack.of(Material.ACACIA_LEAVES)))
                )
                .yesButton(actionButton ->
                        actionButton.label(Component.text("Yes"))
                                .action(new DialogAction.Custom(Key.key("test:test"), CompoundBinaryTag.empty())))
                .noButton(actionButton ->
                        actionButton.label(Component.text("No"))
                                .action(new DialogAction.Custom(Key.key("test:test"), CompoundBinaryTag.empty()))
                )
                .build();
    }
}
