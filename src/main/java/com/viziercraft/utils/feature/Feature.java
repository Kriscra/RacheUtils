package com.viziercraft.utils.feature;

/**
 * Represents a modular feature that can be toggled at runtime.
 */
public interface Feature {

    /**
     * @return unique key used in configuration.
     */
    String getKey();

    /**
     * @return display name for UI/listing purposes.
     */
    String getDisplayName();

    /**
     * Enables the feature, registering listeners, commands or tasks.
     */
    void enable();

    /**
     * Disables the feature and releases all resources.
     */
    void disable();

    /**
     * @return whether this feature is currently active.
     */
    boolean isEnabled();
}
