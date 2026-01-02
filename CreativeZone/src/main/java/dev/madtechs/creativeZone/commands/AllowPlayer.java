package dev.madtechs.creativeZone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.dataControl.Control;


public class AllowPlayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Must provide a player name!");
            return true;
        }

        Control control = CreativeZone.getControl();

        var data = control.getPlayerData(player);

        data.allowPlayer(args[0]);

        player.sendMessage("Allowed: " + args[0]);
        return true;
    }
}
