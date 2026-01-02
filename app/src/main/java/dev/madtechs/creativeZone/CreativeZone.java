package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
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

        assert currentWorld != null;
        Chunk chunk = currentWorld.getChunkAt(0,0);

        // Generate the creative world
        World creativeWorld = creativeZone.createWorld();

        // Set the data
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 16; y++) {
                    Block block = chunk.getBlock(x, y, z);

                    assert creativeWorld != null;
                    creativeWorld.getChunkAt(0,0).getBlock(x,y,z).setBlockData(block.getBlockData());
                }
            }
        }

        creativeWorld.loadChunk(chunk);

        Location location = new Location(creativeWorld, 0, 100, 0);

        player.teleport(location);
    }
}
