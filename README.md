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

### Quick actions and pause screen dialogs

Dialogs can be listed in the Quick Actions menu and on the pause screen. Minestom sends these dialog tags as empty
lists and does not allow changing them, so use `DialogTagRegistry` instead. It registers the dialogs and sends the
tags to players in the play state:

```java
DialogTagRegistry tags = DialogTagRegistry.of(MinecraftServer.getDialogRegistry());
tags.addQuickAction(Key.key("myserver:menu"), quickActionDialog);
tags.addPauseScreenAddition(Key.key("myserver:pause"), pauseDialog);

MinecraftServer.getGlobalEventHandler().addChild(tags.eventNode());
```

Register all dialogs before the server starts. Minestom freezes the dialog registry once the server is running, and
players receive the registry data only during the configuration phase. The tags are sent to each player on their
first spawn. If a player is reconfigured, the client forgets its tags, so call `tags.sendTags(player)` again.

### Limitations

Currently only confirmation dialogs (`DialogType.confirm`) are supported. Support for further Minecraft dialog types is planned.