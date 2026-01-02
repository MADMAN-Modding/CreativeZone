package dev.madtechs.creativeZone.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;

public class PullChunks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CreativeZone plugin = CreativeZone.getInstance();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }
        
        World world = player.getWorld();

        if (!world.getName().contains(player.getUniqueId().toString())) {
            player.sendMessage("You aren't in a creative zone!");
            return true;
        }

        int chunks = Integer.parseInt(args[0]);

        return Helper.buildChunks(player, plugin, chunks, false);
    }
}
