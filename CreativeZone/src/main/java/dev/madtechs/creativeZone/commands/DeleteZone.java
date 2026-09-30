package dev.madtechs.creativeZone.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;

public class DeleteZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Control Checks

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        if (!player.getWorld().getName().toString().contains("c_zone")) {
            player.sendMessage("You aren't in a creative zone!");
            return true;
        }

        if (!player.getWorld().getName().toString().contains(player.getUniqueId().toString())) {
            player.sendMessage("This isn't your world!");
            return true;
        }

        // Message to send to the user
        String responseMessage = "";

        // Overworld has no extension
        String[] worlds = { "", "_nether", "_end" };

        for (String world : worlds) {
            World zone = VoidWorld.getVoidWorld(player.getUniqueId().toString(), player.getWorld()).createWorld();

            zone.setAutoSave(false);

            for (Player zonePlayer : zone.getPlayers()) {
                new LeaveZone().leaveCZone(zonePlayer, sender);
            }

            boolean worldUnload = Bukkit.unloadWorld(zone, false);

            String playerUUID = player.getUniqueId().toString();

            File folder = new File(String.format("world/dimensions/minecraft/c_zones/%s%s", playerUUID, world));

            if (deleteDirectory(folder) && worldUnload) {
                responseMessage += "Successfully deleted world" + world; 
            } else {
                responseMessage += "Failed to delete world" + world;
            }
        }

        player.sendMessage(responseMessage);

        return true;

    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
