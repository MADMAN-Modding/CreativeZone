package dev.madtechs.creativeZone.commands;

import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GoToZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        var zone = new WorldCreator("c_zones/void").createWorld();

        var location = player.getLocation();

        location.setWorld(zone);
        
        player.teleport(location);

        return true;

    }
}
