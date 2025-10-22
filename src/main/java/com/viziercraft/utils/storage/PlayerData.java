package com.viziercraft.utils.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;

/**
 * Runtime container for per-player persistent data.
 */
public class PlayerData {

    private final UUID uniqueId;
    private final Map<String, Location> homes = new HashMap<>();
    private Location backLocation;
    private boolean vanished;
    private boolean frozen;
    private boolean muted;
    private boolean chatSpy;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Map<String, Location> getHomes() {
        return homes;
    }

    public Location getHome(String name) {
        return homes.get(name.toLowerCase());
    }

    public void setHome(String name, Location location) {
        homes.put(name.toLowerCase(), location);
    }

    public void removeHome(String name) {
        homes.remove(name.toLowerCase());
    }

    public Location getBackLocation() {
        return backLocation;
    }

    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isChatSpy() {
        return chatSpy;
    }

    public void setChatSpy(boolean chatSpy) {
        this.chatSpy = chatSpy;
    }
}
