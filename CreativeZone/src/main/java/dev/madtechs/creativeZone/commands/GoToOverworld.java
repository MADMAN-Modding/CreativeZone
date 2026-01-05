package dev.madtechs.creativeZone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;

public class GoToOverworld implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        teleportToOverworld(player);

        return true;
    }

    public void teleportToOverworld(Player player) {
        if (player.getWorld().getName().equals("world")) {
            return;
        }

        var control = CreativeZone.getControl();

        var location = control.getPlayerData(player).getSurvivalLocation();

        if (location != null)
            player.teleport(location);
    }
}
