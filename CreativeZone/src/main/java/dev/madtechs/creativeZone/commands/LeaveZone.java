package dev.madtechs.creativeZone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;

public class LeaveZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        leaveCZone(player, sender);

        return true;
    }

    public void leaveCZone(Player player, CommandSender sender) {
        if (!player.getWorld().getName().contains("c_zone")) {
            sender.sendMessage("You aren't in a creative zone!");
            return;
        }

        var control = CreativeZone.getControl();

        var location = control.getPlayerData(player).getSurvivalLocation();

        if (location != null)
            player.teleport(location);
    }
}
