package org.cnrh.skextras.utils;

import org.bukkit.Bukkit;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
    private Utils(){}

    private static final boolean SKRIPT_IS_THERE = Bukkit.getPluginManager().getPlugin("Skript") != null;
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");
    private static final String PREFIX = "&d[SkExtras] ";

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
        return "<#694EFF>";
    }

    public static String getPrefix() {
        return getColString(mainColor() + "&lꜱᴋᴇxᴛʀᴀꜱ &8| ");
    }

    @SuppressWarnings("deprecation")
    public static String getColString(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        if (SKRIPT_IS_THERE) {
            while (matcher.find()) {
                final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                final String before = string.substring(0, matcher.start());
                final String after = string.substring(matcher.end());
                string = before + hexColor + after;
                matcher = HEX_PATTERN.matcher(string);
            }
        } else {
            string = HEX_PATTERN.matcher(string).replaceAll("");
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendColMsg(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(getColString(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
    }

}
