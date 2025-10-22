package com.viziercraft.utils.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

/**
 * Utility methods for formatting chat messages and translating color codes.
 */
public final class TextUtil {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private TextUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Translates legacy color codes using '&'.
     *
     * @param input raw message containing legacy formatting
     * @return colored message
     */
    public static String color(String input) {
        if (input == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Parses MiniMessage formatted text to a component.
     *
     * @param input mini message string
     * @return component result
     */
    public static Component miniToComponent(String input) {
        return MINI_MESSAGE.deserialize(input);
    }

    /**
     * Serialises a component back to legacy text for backwards compatibility.
     *
     * @param component input component
     * @return legacy string representation
     */
    public static String componentToLegacy(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
}
