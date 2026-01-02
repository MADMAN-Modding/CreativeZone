package dev.madtechs.creativeZone.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import dev.madtechs.creativeZone.CreativeZone;

public class GetAllowedPlayers implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        var control = CreativeZone.getControl();

        var data = control.getPlayerData(player);

        for (UUID uuid : data.getAllowedPlayers()) {
            player.sendMessage(Bukkit.getPlayer(uuid).getName());
        }

        return true;
    }

}
