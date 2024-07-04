package org.cnrh.skextras.commands;

import ch.njol.skript.Skript;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.StringUtil;
import org.cnrh.skextras.SkExtras;
import org.cnrh.skextras.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SkExtrasCmd implements TabExecutor {

    private final PluginDescriptionFile desc;

    @SuppressWarnings("deprecation")
    public SkExtrasCmd(SkExtras plugin) {
        this.desc = plugin.getDescription();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            Utils.sendColMsg(sender, "&8&m     &r " + Utils.getPrefix() + "&7Info &8&m     ");
            Utils.sendColMsg(sender, "  &7Server Version: " + Utils.mainColor() + Bukkit.getVersion());
            Utils.sendColMsg(sender, "  &7Skript Version: " + Utils.mainColor() + Skript.getVersion());
            Utils.sendColMsg(sender, "  &7SkExtras Version: " + Utils.mainColor() + desc.getVersion());
            Utils.sendColMsg(sender, "  &7Skript Addons:");
            Skript.getAddons().forEach(addon -> {
                String name = addon.getName();
                if (!name.contains("SkExtras")) {
                    Utils.sendColMsg(sender, "   " + Utils.mainColor() + name + " v" + addon.plugin.getDescription().getVersion());
                }
            });
            Utils.sendColMsg(sender, "&8&m     &r " + Utils.getPrefix() + "&7Info &8&m     ");
        }
        return true;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0],
                    List.of("info"),
                    new ArrayList<>());
        }
        return null;
    }
}
