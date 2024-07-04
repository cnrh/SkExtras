package org.cnrh.skextras.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.cnrh.skextras.SkExtras;
import org.cnrh.skextras.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SkExtrasCmd {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && (sender.isOp() || sender.hasPermission("skextras.admin"))) {
            sender.sendMessage(Utils.colored(Utils.getPrefix() + "&cThis command won't do anything since there's no config."));
        } else {
            sender.sendMessage(Utils.colored(Utils.getPrefix() + " &fYou're currently running " + Utils.mainColor() + "v" + (SkExtras.getInstance().getVersion())));
        }
        return true;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0],
                    List.of("reload"),
                    new ArrayList<>());
        }
        return null;
    }
}
