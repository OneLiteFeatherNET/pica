package net.theevilreaper.manis.dialog.display.item;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogBody;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class ItemTemplateTest {

    @Test
    void testInvalidWidthAndHeightUsage() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> ItemTemplate.builder().width(-1).build(),
                "Width and Height must be greater than 0"
        );
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> ItemTemplate.builder().height(0).build(),
                "Width and Height must be greater than 0"
        );
    }

    @Test
    void testItemTemplateWithoutAItem() {
        assertThrowsExactly(
                NullPointerException.class,
                () -> ItemTemplate.builder().build(),
                "Item must not be null"
        );
    }

    @Test
    void testItemTemplateCreation(Env env) {
        DialogBody body = ItemTemplate.builder()
                .width(10)
                .height(20)
                .showToolTip(false)
                .showDecoration(true)
                .description(componentTemplate -> {
                    componentTemplate.contents(Component.text("Test Item"));
                    componentTemplate.width(100);
                })
                .item(ItemStack.builder(Material.ACACIA_BUTTON).build())
                .build();


        assertNotNull(body, "The body must not be null");
        assertInstanceOf(DialogBody.Item.class, body, "The body must be of type Item");
        DialogBody.Item template = (DialogBody.Item) body;

        assertEquals(10, template.width());
        assertEquals(20, template.height());
        assertTrue(template.showDecoration());
        assertFalse(template.showTooltip());
        assertEquals(Material.ACACIA_BUTTON, template.itemStack().material());

        assertNotNull(template.description(), "The description must not be null");

        DialogBody.PlainMessage message = template.description();
        assertEquals(100, message.width());
        assertEquals(Component.text("Test Item"), message.contents());

    }
}
