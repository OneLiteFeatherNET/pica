package net.theevilreaper.manis.dialog;

/**
 * Contains some constant values and validation checks.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DialogConstants {

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_ACTION_WIDTH = 150;

    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 1024;

    private static final int MAX_HEIGHT = 512;
    private static final int ITEM_TEMPLATE_MAX_DIMENSION = 256;

    private DialogConstants() {
        // No instances allowed
    }

    /**
     * Validates the dimensions of an item template component (i.e., width and height).
     *
     * @param value the height to validate
     * @throws IllegalArgumentException if the height is out of the valid range
     */
    public static void validateItemTemplateDimension(int value) {
        if (value < MIN_WIDTH || value > ITEM_TEMPLATE_MAX_DIMENSION) {
            throw new IllegalArgumentException(
                    String.format(
                            "Item template height / width must be between %d and %d",
                            MIN_WIDTH,
                            ITEM_TEMPLATE_MAX_DIMENSION
                    )
            );
        }
    }

    /**
     * Validates the height of a dialog component.
     *
     * @param height the height to validate
     * @throws IllegalArgumentException if the height is out of the valid range
     */
    public static void validateHeight(int height) {
        if (height < MIN_WIDTH || height > MAX_HEIGHT) {
            throw new IllegalArgumentException(String.format("Height must be between %d and %d", MIN_WIDTH, MAX_HEIGHT));
        }
    }


    /**
     * Validates the width of a dialog component.
     *
     * @param width the width to validate
     * @throws IllegalArgumentException if the width is out of the valid range
     */
    public static void validateWidth(int width) {
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            throw new IllegalArgumentException(String.format("Width must be between %d and %d", MIN_WIDTH, MAX_WIDTH));
        }
    }

    /**
     * Validate the start value of a range component.
     *
     * @param start the start value to validate
     * @throws IllegalArgumentException if the start value is less than 0
     */
    public static void validateRangeStart(float start) {
        if (start < 0) {
            throw new IllegalArgumentException("Start must be greater than or equal to 0");
        }
    }

    /**
     * Validate the end value of a range component.
     *
     * @param end the end value to validate
     * @throws IllegalArgumentException if the end value is less than 0 or greater than {@link Float#MAX_VALUE}
     */
    public static void validateRangeEnd(float end) {
        if (end < 0) {
            throw new IllegalArgumentException("End greater than or equal to 0");
        }
    }
}
