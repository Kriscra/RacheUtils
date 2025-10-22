package com.viziercraft.utils.storage;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores pending teleport requests for /tpa and /tpahere.
 */
public class TeleportRequestManager {

    public enum Type {
        TO_TARGET,
        HERE
    }

    public record TeleportRequest(UUID requester, UUID target, Type type, Instant createdAt) {
    }

    private final Map<UUID, TeleportRequest> requests = new ConcurrentHashMap<>();

    public void createRequest(UUID requester, UUID target, Type type) {
        requests.put(target, new TeleportRequest(requester, target, type, Instant.now()));
    }

    public Optional<TeleportRequest> getRequest(UUID target) {
        return Optional.ofNullable(requests.get(target));
    }

    public Optional<TeleportRequest> removeRequest(UUID target) {
        return Optional.ofNullable(requests.remove(target));
    }

    public void clear() {
        requests.clear();
    }
}
