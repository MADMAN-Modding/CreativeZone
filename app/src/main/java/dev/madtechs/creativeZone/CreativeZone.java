package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CreativeZone extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName()));

        Player player = event.getPlayer();

        WorldCreator creativeZone = VoidWorld.getVoidWorld();

        World currentWorld = player.getWorld();

        Chunk chunk = currentWorld.getChunkAt(0,0);

        World creativeWorld = creativeZone.createWorld();

        assert creativeWorld != null;
        creativeWorld.loadChunk(chunk);

        Location location = new Location(creativeWorld, 0, 100, 0);

        player.teleport(location);
    }
}
