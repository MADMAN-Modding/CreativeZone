package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.madtechs.creativeZone.CreativeZone;

public class Death implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        var worldName = player.getWorld().getName();

        if (worldName.contains("c_zone")) {
            var control = CreativeZone.getControl();

            control.loadSurvival(player);
            player.setGameMode(Bukkit.getDefaultGameMode());
        }    
    }
}
