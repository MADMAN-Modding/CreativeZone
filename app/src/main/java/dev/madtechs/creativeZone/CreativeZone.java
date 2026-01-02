package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import dev.madtechs.creativeZone.voidWorld.ZoneMaker;
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

        World currentWorld = new WorldCreator("world").createWorld();

        // Generate the creative world
        World creativeWorld = creativeZone.createWorld();

        Location location = new Location(creativeWorld, 0, 100, 0);

        player.setGameMode(GameMode.CREATIVE);

        player.teleport(location);

        // Set the data
        ZoneMaker.makeWorld(creativeWorld, currentWorld, 4, location, this);

    }
}
