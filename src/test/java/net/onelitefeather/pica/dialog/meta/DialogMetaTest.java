package net.onelitefeather.pica.dialog.meta;

import net.kyori.adventure.text.Component;
import net.minestom.server.dialog.DialogAfterAction;
import net.minestom.server.dialog.DialogBody;
import net.minestom.server.dialog.DialogInput;
import net.minestom.server.dialog.DialogMetadata;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.onelitefeather.pica.dialog.display.component.ComponentTemplate;
import net.onelitefeather.pica.dialog.input.text.TextInputTemplate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DialogMetaTest {

    @Test
    void testDefaultValues() {
        DialogMetadata metadata = new DialogMetaData().build();

        assertEquals(Component.empty(), metadata.title());
        assertEquals(Component.empty(), metadata.externalTitle());
        assertEquals(DialogAfterAction.NONE, metadata.afterAction());
        assertFalse(metadata.canCloseWithEscape());
        assertFalse(metadata.pause());
        assertTrue(metadata.body().isEmpty());
        assertTrue(metadata.inputs().isEmpty());
    }

    @Test
    void testSimpleValuesAreApplied() {
        DialogMetadata metadata = new DialogMetaData()
                .title(Component.text("Title"))
                .externalTitle(Component.text("External"))
                .closeWithEscape(true)
                .pause(true)
                .afterAction(DialogAfterAction.CLOSE)
                .build();

        assertEquals(Component.text("Title"), metadata.title());
        assertEquals(Component.text("External"), metadata.externalTitle());
        assertTrue(metadata.canCloseWithEscape());
        assertTrue(metadata.pause());
        assertEquals(DialogAfterAction.CLOSE, metadata.afterAction());
    }

    @Test
    void testExternalTitleCanBeCleared() {
        DialogMetadata metadata = new DialogMetaData().externalTitle(null).build();

        assertNull(metadata.externalTitle());
    }

    @Test
    void testEmptyMessageAddsEmptyBody() {
        DialogMetadata metadata = new DialogMetaData().emptyMessage().build();

        assertEquals(List.of(DialogMeta.EMPTY), metadata.body());
    }

    @Test
    void testBodiesKeepInsertionOrder() {
        DialogMetadata metadata = new DialogMetaData()
                .messageBody(body -> body.contents(Component.text("first")))
                .itemBody(item -> item.item(ItemStack.of(Material.STONE)))
                .messageBody(body -> body.contents(Component.text("last")))
                .build();

        List<DialogBody> bodies = metadata.body();
        assertEquals(3, bodies.size());
        assertEquals(Component.text("first"), assertInstanceOf(DialogBody.PlainMessage.class, bodies.get(0)).contents());
        assertEquals(ItemStack.of(Material.STONE), assertInstanceOf(DialogBody.Item.class, bodies.get(1)).itemStack());
        assertEquals(Component.text("last"), assertInstanceOf(DialogBody.PlainMessage.class, bodies.get(2)).contents());
    }

    @Test
    void testInputsKeepKeyTypeAndOrder() {
        DialogMetadata metadata = new DialogMetaData()
                .text("text", input -> input.label(Component.text("Text")))
                .option("option", input -> input
                        .label(Component.text("Option"))
                        .option(new DialogInput.SingleOption.Option("a", Component.text("A"), true)))
                .range("range", input -> input.label(Component.text("Range")).start(0).end(10))
                .bool("bool", input -> input.label(Component.text("Bool")))
                .build();

        List<DialogInput> inputs = metadata.inputs();
        assertEquals(4, inputs.size());
        assertEquals("text", assertInstanceOf(DialogInput.Text.class, inputs.get(0)).key());
        assertEquals("option", assertInstanceOf(DialogInput.SingleOption.class, inputs.get(1)).key());
        assertEquals("range", assertInstanceOf(DialogInput.NumberRange.class, inputs.get(2)).key());
        assertEquals("bool", assertInstanceOf(DialogInput.Boolean.class, inputs.get(3)).key());
    }

    @Test
    void testBodyListIsAppended() {
        DialogBody external = ComponentTemplate.builder().contents(Component.text("external")).build();

        DialogMetadata metadata = new DialogMetaData()
                .messageBody(body -> body.contents(Component.text("own")))
                .body(List.of(external))
                .build();

        assertEquals(2, metadata.body().size());
        assertEquals(Component.text("own"), assertInstanceOf(DialogBody.PlainMessage.class, metadata.body().getFirst()).contents());
        assertSame(external, metadata.body().get(1));
    }

    @Test
    void testInputListIsAppended() {
        DialogInput external = TextInputTemplate.builder("external").label(Component.text("External")).build();

        DialogMetadata metadata = new DialogMetaData()
                .text("own", input -> input.label(Component.text("Own")))
                .input(List.of(external))
                .build();

        assertEquals(2, metadata.inputs().size());
        assertEquals("own", assertInstanceOf(DialogInput.Text.class, metadata.inputs().getFirst()).key());
        assertSame(external, metadata.inputs().get(1));
    }

    @Test
    void testBuiltMetadataIsNotAffectedByLaterChanges() {
        DialogMeta meta = new DialogMetaData()
                .messageBody(body -> body.contents(Component.text("first")))
                .text("first", input -> input.label(Component.text("First")));

        DialogMetadata first = meta.build();
        meta.emptyMessage().bool("second", input -> input.label(Component.text("Second")));
        DialogMetadata second = meta.build();

        assertEquals(1, first.body().size());
        assertEquals(1, first.inputs().size());
        assertEquals(2, second.body().size());
        assertEquals(2, second.inputs().size());
        assertThrows(UnsupportedOperationException.class, () -> first.body().add(DialogMeta.EMPTY));
    }
}
