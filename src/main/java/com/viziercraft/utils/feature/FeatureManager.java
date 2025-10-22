package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Registers and toggles features based on configuration values.
 */
public class FeatureManager {

    private final VizierUtilsPlugin plugin;
    private final Map<String, Feature> features = new LinkedHashMap<>();

    public FeatureManager(VizierUtilsPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerFeature(Feature feature) {
        features.put(feature.getKey().toLowerCase(), feature);
    }

    public Optional<Feature> getFeature(String key) {
        return Optional.ofNullable(features.get(key.toLowerCase()));
    }

    public Collection<Feature> getFeatures() {
        return features.values();
    }

    /**
     * Initialises features according to the configuration.
     */
    public void initialise() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("features");
        if (section == null) {
            plugin.getLogger().warning("No features section found in config.yml");
            return;
        }
        for (Feature feature : features.values()) {
            boolean enabled = section.getBoolean(feature.getKey(), true);
            toggleFeature(feature, enabled, false);
        }
    }

    public boolean toggleFeature(String key, boolean announce) {
        Optional<Feature> featureOptional = getFeature(key);
        if (featureOptional.isEmpty()) {
            return false;
        }
        Feature feature = featureOptional.get();
        boolean newState = !feature.isEnabled();
        toggleFeature(feature, newState, announce);
        plugin.getConfig().set("features." + feature.getKey(), newState);
        plugin.saveConfig();
        return true;
    }

    public void toggleFeature(Feature feature, boolean state, boolean announce) {
        if (state == feature.isEnabled()) {
            return;
        }
        try {
            if (state) {
                feature.enable();
            } else {
                feature.disable();
            }
            if (announce) {
                plugin.getLogger().log(Level.INFO, "Feature {0} is now {1}", new Object[]{feature.getKey(), state ? "enabled" : "disabled"});
            }
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to toggle feature " + feature.getKey(), ex);
        }
    }

    public void reloadAll() {
        initialise();
    }
}
