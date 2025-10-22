package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Keeps the weather fixed according to configuration.
 */
public class WeatherLockFeature extends ListenerFeature {

    public WeatherLockFeature(VizierUtilsPlugin plugin) {
        super(plugin, "weather-lock", "Weather Lock");
    }

    @Override
    public void enable() {
        super.enable();
        applyWeather();
    }

    private World getWorld() {
        String worldName = plugin.getConfig().getString("weather-lock.world", "world");
        return plugin.getServer().getWorld(worldName);
    }

    private void applyWeather() {
        World world = getWorld();
        if (world == null) {
            return;
        }
        String state = plugin.getConfig().getString("weather-lock.state", "CLEAR").toUpperCase();
        switch (state) {
            case "CLEAR" -> {
                world.setStorm(false);
                world.setThundering(false);
            }
            case "RAIN" -> {
                world.setStorm(true);
                world.setThundering(false);
            }
            case "STORM" -> {
                world.setStorm(true);
                world.setThundering(true);
            }
            default -> {
                // no-op
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        if (!isEnabled()) {
            return;
        }
        World world = getWorld();
        if (world == null || !event.getWorld().equals(world)) {
            return;
        }
        event.setCancelled(true);
        applyWeather();
    }

    @EventHandler
    public void onThunder(ThunderChangeEvent event) {
        if (!isEnabled()) {
            return;
        }
        World world = getWorld();
        if (world == null || !event.getWorld().equals(world)) {
            return;
        }
        event.setCancelled(true);
        applyWeather();
    }
}
