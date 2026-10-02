package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import dev.madtechs.creativeZone.Helper;

public class ZoneChunkListener implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {        
        if (!event.isNewChunk()) {
            return;
        }

        World zone = event.getWorld();

        // Return if not in a zone
        if (!zone.getName().contains("c_zones/")) {
            return;
        }

        // Source World
        World source = Helper.getSourceWorld(zone);

        Chunk zoneChunk = event.getChunk();
        Chunk sourceChunk = source.getChunkAt(zoneChunk.getX(), zoneChunk.getZ());

        ChunkSnapshot snapshot = sourceChunk.getChunkSnapshot(true, true, true);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = zone.getMinHeight(); y < zone.getMaxHeight(); y++) {
                    BlockData data = snapshot.getBlockData(x, y, z);

                    // The zone chunk is already void, nothing to place
                    if (data.getMaterial().isAir()) {
                        continue;
                    }

                    zoneChunk.getBlock(x, y, z).setBlockData(data, false);
                }
            }
        }
    }
}
