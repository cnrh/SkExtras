package org.cnrh.skextras.utils;

import ch.njol.skript.util.Version;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cnrh.skextras.SkExtras;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class UpdateChecker implements Listener {
    private final SkExtras plugin;
    private final Version pluginVersion;
    private Version currentUpdateVersion;

    public UpdateChecker(SkExtras plugin) {
        this.plugin = plugin;
        this.pluginVersion = new Version(plugin.getDescription().getVersion());
    }

    private void setupJoinListener() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            private void onJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                if (!player.hasPermission("skextras.admin")) return;
                Bukkit.getScheduler().runTaskLater(UpdateChecker.this.plugin, () -> getUpdateVersion(true).thenApply(version -> {
                    Utils.sendColMsg(player, Utils.getPrefix() + "&7This version is outdated.");
                    Utils.sendColMsg(player, Utils.getPrefix() + "&7Download version " + version + " at &dhttps://github.com/cnrh/SkExtras/releases");
                    return true;
                }), 30);
            }
        }, this.plugin);
    }

    private void checkUpdate(boolean async) {
        Utils.log("Checking for update...");
        getUpdateVersion(async).thenApply(version -> {
            Utils.log("&cPlugin is not up to date!");
            Utils.log(" - Current version: &cv%s", this.pluginVersion);
            Utils.log(" - Available version: &av%s", version);
            Utils.log(" - Download at: https://github.com/cnrh/SkExtras/releases");
            return true;
        }).exceptionally(throwable -> {
            Utils.log("&aPlugin is up to date!");
            return true;
        });
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private CompletableFuture<Version> getUpdateVersion(boolean async) {
        CompletableFuture<Version> future = new CompletableFuture<>();
        if (this.currentUpdateVersion != null) {
            future.complete(this.currentUpdateVersion);
        } else {
            getLatestReleaseVersion(async).thenApply(version -> {
                if (version.compareTo(this.pluginVersion) <= 0) {
                    future.cancel(true);
                } else {
                    this.currentUpdateVersion = version;
                    future.complete(this.currentUpdateVersion);
                }
                return true;
            });
        }
        return future;
    }

    private CompletableFuture<Version> getLatestReleaseVersion(boolean async) {
        CompletableFuture<Version> future = new CompletableFuture<>();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                Version latest2 = getLatestVersionFromGithub();
                if (latest2 == null) future.cancel(true);
            });
        } else {
            Version latest = getLatestVersionFromGithub();
            if (latest == null) future.cancel(true);
            future.complete(latest);
        }
        return future;
    }

    private @Nullable Version getLatestVersionFromGithub() {
        try {
            URL url = new URL("https://api.github.com/repos/cnrh/SkExtras/releases/latest");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
            String tag_name = jsonObject.get("tag_name").getAsString();
            return new Version(tag_name);
        } catch (IOException e) {
            Utils.log("Checking for update failed!");
            e.printStackTrace();
        }
        return null;
    }
}
