package net.theevilreaper.manis.dialog.display;

import net.minestom.server.dialog.DialogBody;

/**
 * The interface defines the basic template structure of a body component from a Minecraft dialog.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BodyTemplate {

    /**
     * Creates a new instance of the {@link DialogBody} based on the given implementation.
     *
     * @return the created instance
     */
    DialogBody build();
}
