package dev.madtechs.creativeZone.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        new GoToOverworld().onCommand(sender, command, label, args);

        File worldFolder = new File("c_zones/" + player.getUniqueId().toString());

        if (deleteDirectory(worldFolder)) {
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
