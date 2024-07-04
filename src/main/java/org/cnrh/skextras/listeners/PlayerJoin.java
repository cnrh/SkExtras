package org.cnrh.skextras.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cnrh.skextras.SkExtras;
import org.cnrh.skextras.utils.Utils;

public class PlayerJoin implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        if (SkExtras.getInstance().isLatest()) {
            Utils.sendColMsg(player, Utils.getPrefix() + "&fYou are running the latest version of &fSkExtras&7.");
        } else {
            Utils.sendColMsg(player, Utils.getPrefix() + "&cYou aren't running the latest version.");
        }
    }
}
