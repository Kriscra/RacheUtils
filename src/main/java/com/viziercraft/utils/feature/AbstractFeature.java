package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;

/**
 * Basic implementation that stores the enabled flag and plugin reference.
 */
public abstract class AbstractFeature implements Feature {

    protected final VizierUtilsPlugin plugin;
    private final String key;
    private final String displayName;
    private boolean enabled;

    protected AbstractFeature(VizierUtilsPlugin plugin, String key, String displayName) {
        this.plugin = plugin;
        this.key = key;
        this.displayName = displayName;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    protected void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
