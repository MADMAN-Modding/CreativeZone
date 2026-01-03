package dev.madtechs.creativeZone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;

public class CreateZone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CreativeZone plugin = CreativeZone.getInstance();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        var playerWorld = player.getLocation().getWorld();

        // If the player is already in a zone, pull the chunks instead
        if ((playerWorld.getName().contains("c_zone"))) {
            return new PullChunks().onCommand(sender, command, label, args);
        } else if (!playerWorld.getName().equals("world")) {
            player.sendMessage("You need to be in the overworld when creating a zone");
            return true;
        }

        int chunks = Integer.parseInt(args[0]);

        return Helper.buildChunks(player, plugin, chunks);
    }
}
