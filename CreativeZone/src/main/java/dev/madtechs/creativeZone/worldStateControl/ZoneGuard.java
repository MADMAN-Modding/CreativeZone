package dev.madtechs.creativeZone.worldStateControl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZoneGuard {
    private final Set<String> busy = ConcurrentHashMap.newKeySet();

    /**
     * Attempts to place a lock on a world
     * @param worldName
     * @return Returns true if the lock was successful
     */
    public boolean tryLock(String worldName) {
        return busy.add(worldName);
    }

    public void unlock(String worldName) {
        busy.remove(worldName);
    }

    public Set<String> getBusy() {
        return busy;
    }
}