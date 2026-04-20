## Pica

A lightweight Dialog API for [Minestom](https://minestom.net/), built around Minecraft's native dialog system.

### Usage

Dialogs are built via `DialogType` and opened directly on a `Player`. The template is associated via a key from Adventure.

```java
DialogTemplate dialog = DialogType.confirm(MY_KEY)
    .meta(meta -> {
        meta.title(Component.text("Confirm deletion"));
        meta.closeWithEscape(false);
        meta.afterAction(DialogAfterAction.CLOSE);
        meta.messageBody(body -> body.width(400).contents(Component.text("This action cannot be undone!", NamedTextColor.RED)));
    })
    .yesButton(button -> button.label(Component.text("Yes")).action(myAction))
    .noButton(button -> button.label(Component.text("No")))
    .build();

dialog.open(player);
```

Dialogs can optionally be tracked via a `DialogRegistry`:

```java
DialogRegistry registry = DialogRegistry.of();
registry.add(dialog);
registry.get(MY_KEY).open(player);
```

### Limitations

Currently only confirmation dialogs (`DialogType.confirm`) are supported. Support for further Minecraft dialog types is planned.