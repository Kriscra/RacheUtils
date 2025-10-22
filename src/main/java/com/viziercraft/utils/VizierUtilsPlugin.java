package com.viziercraft.utils;

import com.viziercraft.utils.commands.CommandManager;
import com.viziercraft.utils.commands.InfoCommand;
import com.viziercraft.utils.commands.ListCommand;
import com.viziercraft.utils.commands.ReloadCommand;
import com.viziercraft.utils.commands.ToggleCommand;
import com.viziercraft.utils.feature.AntiFireSpreadFeature;
import com.viziercraft.utils.feature.AntiPhantomFeature;
import com.viziercraft.utils.feature.AutoClearWeatherFeature;
import com.viziercraft.utils.feature.AutoDayFeature;
import com.viziercraft.utils.feature.AutoMessageFeature;
import com.viziercraft.utils.feature.BackFeature;
import com.viziercraft.utils.feature.BanFeature;
import com.viziercraft.utils.feature.BroadcastFeature;
import com.viziercraft.utils.feature.ChatSpyFeature;
import com.viziercraft.utils.feature.ClearLagFeature;
import com.viziercraft.utils.feature.DisableEndermanGriefFeature;
import com.viziercraft.utils.feature.EnderChestFeature;
import com.viziercraft.utils.feature.FeedFeature;
import com.viziercraft.utils.feature.FeatureManager;
import com.viziercraft.utils.feature.FreezeFeature;
import com.viziercraft.utils.feature.HatFeature;
import com.viziercraft.utils.feature.HealFeature;
import com.viziercraft.utils.feature.HomeFeature;
import com.viziercraft.utils.feature.InvseeFeature;
import com.viziercraft.utils.feature.JoinQuitFeature;
import com.viziercraft.utils.feature.KickFeature;
import com.viziercraft.utils.feature.MotdFeature;
import com.viziercraft.utils.feature.MuteFeature;
import com.viziercraft.utils.feature.NoCreeperGriefFeature;
import com.viziercraft.utils.feature.SetHomeFeature;
import com.viziercraft.utils.feature.TempBanFeature;
import com.viziercraft.utils.feature.TimeLockFeature;
import com.viziercraft.utils.feature.TpaFeature;
import com.viziercraft.utils.feature.TpAcceptFeature;
import com.viziercraft.utils.feature.TpDenyFeature;
import com.viziercraft.utils.feature.TpahereFeature;
import com.viziercraft.utils.feature.TrashFeature;
import com.viziercraft.utils.feature.TpsMonitorFeature;
import com.viziercraft.utils.feature.VanishFeature;
import com.viziercraft.utils.feature.WeatherLockFeature;
import com.viziercraft.utils.feature.WorkbenchFeature;
import com.viziercraft.utils.listener.PlayerLifecycleListener;
import com.viziercraft.utils.storage.PlayerStorage;
import com.viziercraft.utils.storage.TeleportRequestManager;
import com.viziercraft.utils.util.MessageService;
import com.viziercraft.utils.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Entry point for the VizierUtils plugin.
 */
public class VizierUtilsPlugin extends JavaPlugin {

    private FeatureManager featureManager;
    private PlayerStorage playerStorage;
    private MessageService messages;
    private CommandManager commandManager;
    private TeleportRequestManager teleportRequestManager;
    private boolean placeholderApiEnabled;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResourceSafely("messages.yml");
        messages = new MessageService(this);
        playerStorage = new PlayerStorage(this);
        playerStorage.load();
        teleportRequestManager = new TeleportRequestManager();
        placeholderApiEnabled = PluginUtil.hasPlaceholderApi();

        featureManager = new FeatureManager(this);
        registerFeatures();
        featureManager.initialise();

        commandManager = new CommandManager(this);
        commandManager.register(new ReloadCommand(this));
        commandManager.register(new ToggleCommand(this));
        commandManager.register(new ListCommand(this));
        commandManager.register(new InfoCommand(this));
        if (getCommand("utils") != null) {
            getCommand("utils").setExecutor(commandManager);
            getCommand("utils").setTabCompleter(commandManager);
        } else {
            getLogger().severe("/utils command is not defined in plugin.yml");
        }

        Bukkit.getPluginManager().registerEvents(new PlayerLifecycleListener(this), this);

        getLogger().info("VizierUtils enabled. PlaceholderAPI support: " + placeholderApiEnabled);
    }

    @Override
    public void onDisable() {
        if (featureManager != null) {
            featureManager.getFeatures().forEach(feature -> {
                if (feature.isEnabled()) {
                    try {
                        feature.disable();
                    } catch (Exception ex) {
                        getLogger().log(Level.SEVERE, "Failed to disable feature " + feature.getKey(), ex);
                    }
                }
            });
        }
        teleportRequestManager.clear();
        if (playerStorage != null) {
            playerStorage.save();
        }
        getLogger().info("VizierUtils disabled.");
    }

    private void registerFeatures() {
        featureManager.registerFeature(new HealFeature(this));
        featureManager.registerFeature(new FeedFeature(this));
        featureManager.registerFeature(new BackFeature(this));
        featureManager.registerFeature(new HomeFeature(this));
        featureManager.registerFeature(new SetHomeFeature(this));
        featureManager.registerFeature(new HatFeature(this));
        featureManager.registerFeature(new WorkbenchFeature(this));
        featureManager.registerFeature(new TrashFeature(this));
        featureManager.registerFeature(new EnderChestFeature(this));

        featureManager.registerFeature(new VanishFeature(this));
        featureManager.registerFeature(new FreezeFeature(this));
        featureManager.registerFeature(new InvseeFeature(this));
        featureManager.registerFeature(new MuteFeature(this));
        featureManager.registerFeature(new KickFeature(this));
        featureManager.registerFeature(new BanFeature(this));
        featureManager.registerFeature(new TempBanFeature(this));
        featureManager.registerFeature(new ChatSpyFeature(this));
        featureManager.registerFeature(new TpaFeature(this));
        featureManager.registerFeature(new TpahereFeature(this));
        featureManager.registerFeature(new TpAcceptFeature(this));
        featureManager.registerFeature(new TpDenyFeature(this));
        featureManager.registerFeature(new BroadcastFeature(this));

        featureManager.registerFeature(new AntiPhantomFeature(this));
        featureManager.registerFeature(new NoCreeperGriefFeature(this));
        featureManager.registerFeature(new AntiFireSpreadFeature(this));
        featureManager.registerFeature(new DisableEndermanGriefFeature(this));
        featureManager.registerFeature(new WeatherLockFeature(this));
        featureManager.registerFeature(new TimeLockFeature(this));
        featureManager.registerFeature(new AutoDayFeature(this));
        featureManager.registerFeature(new AutoClearWeatherFeature(this));

        featureManager.registerFeature(new ClearLagFeature(this));
        featureManager.registerFeature(new TpsMonitorFeature(this));
        featureManager.registerFeature(new AutoMessageFeature(this));
        featureManager.registerFeature(new MotdFeature(this));
        featureManager.registerFeature(new JoinQuitFeature(this));
    }

    private void saveResourceSafely(String path) {
        try {
            saveResource(path, false);
        } catch (IllegalArgumentException ignored) {
            // already present
        }
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public PlayerStorage getPlayerStorage() {
        return playerStorage;
    }

    public MessageService getMessages() {
        return messages;
    }

    public TeleportRequestManager getTeleportRequestManager() {
        return teleportRequestManager;
    }

    public boolean isPlaceholderApiEnabled() {
        return placeholderApiEnabled;
    }
}
