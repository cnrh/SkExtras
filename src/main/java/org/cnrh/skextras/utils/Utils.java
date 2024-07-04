package org.cnrh.skextras.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private Utils(){}

    private static final TreeMap<Integer, String> ROMAN_NUMERALS = new TreeMap<>();

    static {
        ROMAN_NUMERALS.put(1000, "M");
        ROMAN_NUMERALS.put(900, "CM");
        ROMAN_NUMERALS.put(500, "D");
        ROMAN_NUMERALS.put(400, "CD");
        ROMAN_NUMERALS.put(100, "C");
        ROMAN_NUMERALS.put(90, "XC");
        ROMAN_NUMERALS.put(50, "L");
        ROMAN_NUMERALS.put(40, "XL");
        ROMAN_NUMERALS.put(10, "X");
        ROMAN_NUMERALS.put(9, "IX");
        ROMAN_NUMERALS.put(5, "V");
        ROMAN_NUMERALS.put(4, "IV");
        ROMAN_NUMERALS.put(1, "I");
    }

    public static String toRoman(int number) {
        int l = ROMAN_NUMERALS.floorKey(number);
        if (number == 1) return ROMAN_NUMERALS.get(number);
        return ROMAN_NUMERALS.get(l) + toRoman(number - l);
    }

    public static String mainColor() {
        return "&#694EFF";
    }

    public static String getPrefix() {
        return colored(mainColor() + "&lꜱᴋᴇxᴛʀᴀꜱ &8| ");
    }

    public static Component toComponent(String text) {
        return LegacyComponentSerializer.legacySection().deserialize(text);
    }

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public static String colored(String text) {
        text = text.replace("&", "§");
        StringBuilder sb = new StringBuilder();
        Matcher matcher = HEX_COLOR_PATTERN.matcher(text);

        int lastEnd = 0;
        while (matcher.find()) {
            sb.append(text, lastEnd, matcher.start());
            sb.append('§').append('x');
            char[] hexChars = matcher.group(1).toCharArray();
            for (char hexChar : hexChars) {
                sb.append('§').append(hexChar);
            }
            lastEnd = matcher.end();
        }
        sb.append(text.substring(lastEnd));
        return toString(LegacyComponentSerializer.legacySection().deserialize(sb.toString()));
    }

    public static String toString(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static void log(String format) {
        String log = String.format(format);
        Bukkit.getConsoleSender().sendMessage(log);
    }
}
