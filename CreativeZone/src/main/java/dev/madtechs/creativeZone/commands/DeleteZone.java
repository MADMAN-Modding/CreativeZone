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
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        if (!player.getWorld().getName().toString().contains(player.getUniqueId().toString())) {
            player.sendMessage("This isn't your world!");
            return true;
        }

        World zone = VoidWorld.getVoidWorld(player.getUniqueId().toString()).createWorld();

        zone.setAutoSave(false);

        for (Player zonePlayers : zone.getPlayers()) {
            new GoToOverworld().teleportToOverworld(zonePlayers);
        }

        boolean worldUnload = Bukkit.unloadWorld(zone, false);

        File worldFolder = new File("c_zones/" + player.getUniqueId().toString());

        if (deleteDirectory(worldFolder) && worldUnload) {
            player.sendMessage("Zone deleted!");
        } else {
            player.sendMessage("Zone wasn't able to be deleted, does it exist?");
        }

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
