package dev.madtechs.creativeZone.worldStateControl;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;

import dev.madtechs.creativeZone.Helper;

public class ZoneUnloader {
    public static void checkToUnload(ZoneGuard guard) {
        for (World world : Bukkit.getWorlds()) {
            if (!Helper.isZone(world))
                continue;
            if (!world.getPlayers().isEmpty())
                continue;
            if (!guard.tryLock(world.getName()))
                continue; // Lock is already owned

            try {
                Bukkit.unloadWorld(world, true);
                Bukkit.getLogger().log(Level.INFO, "UNLOADED C_ZONE: " + world);
            } finally {
                guard.unlock(world.getName());
            }
        }
    }
}
