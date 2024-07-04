package org.cnrh.skextras.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cnrh.skextras.SkExtras;
import org.cnrh.skextras.utils.Utils;

import static org.cnrh.skextras.utils.Utils.colored;

public class PlayerJoin implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        if (SkExtras.getInstance().isLatest()) {
            player.sendMessage(colored(Utils.getPrefix() + "&fYou are running the latest version of &fSkExtras&7."));
        } else {
            player.sendMessage(colored(Utils.getPrefix() + "&fYou are not running the latest version!"));
            Component message = colored(Utils.getPrefix() + "&7&oClick me to go to the latest version!");
            Component hoverText = colored("&7&oClick here!");
            Component finalMessage = message.hoverEvent(HoverEvent.showText(hoverText))
                    .clickEvent(ClickEvent.openUrl("https://www.github.com/cnrh/SkExtras/releases/latest"));
            player.sendMessage(finalMessage);
        }
    }
}
