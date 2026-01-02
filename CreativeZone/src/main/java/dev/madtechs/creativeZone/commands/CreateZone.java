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
        
        int chunks = Integer.parseInt(args[0]);

        Helper.buildChunks(player, plugin, chunks);

        return true;

    }
}
