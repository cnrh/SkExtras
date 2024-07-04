package org.cnrh.skextras;

import ch.njol.skript.Skript;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.cnrh.skextras.commands.SkExtrasCmd;
import org.cnrh.skextras.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.text.DecimalFormat;

public class SkExtras extends JavaPlugin {

    private static SkExtras instance;
    private boolean latest = true;
    private Loader loader = null;
    private String version;
    private final DecimalFormat df = new DecimalFormat("0.00");

    public SkExtras(){}

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        PluginManager pluginManager = Bukkit.getPluginManager();
        String version = getDescription().getVersion();
        version = this.getDescription().getVersion();

        this.loader = new Loader(this);
        if (!loader.canLoadPlugin()) {
            pluginManager.disablePlugin(this);
            return;
        }

        loadCommands();
        loadMetrics();

        new UpdateChecker(this);
        Utils.log("&aSuccessfully enabled v%s &7in &d%.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
    }

    private void loadCommands() {
        getCommand("skextras").setExecutor(new SkExtrasCmd(this));
    }

    private void loadMetrics() {
        Metrics metrics = new Metrics(this, 22531);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }

    public static SkExtras getInstance() {
        return instance;
    }

    public boolean isLatest() {
        return latest;
    }

    public String getVersion() {
        return version;
    }
}