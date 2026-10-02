package dev.madtechs.creativeZone.commands;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.Helper;
import dev.madtechs.creativeZone.dataControl.Control;
import dev.madtechs.creativeZone.voidWorld.VoidWorld;

public class ReturnToZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        Player zoneOwner;
        if (args.length >= 1) {
            zoneOwner = Helper.getPlayerFromName(args[0]);
        } else {
            zoneOwner = player;
        }

        returnToZone(player, sender, zoneOwner);

        return true;
    }

    public void returnToZone(Player player, CommandSender sender, Player zoneOwner) {
        Control control = CreativeZone.getControl();

        control.setPreviousGameMode(player);

        var playerData = control.getPlayerData(zoneOwner);

        if (!playerData.isPlayerAllowed(player)) {
            player.sendMessage(player.getName() + " are not allowed in that zone, ask " + zoneOwner.getName()
                    + " to allow you with /allowPlayer");

            return;
        }

        player.setGameMode(GameMode.CREATIVE);

        final Location location;

        if (player.equals(zoneOwner)) {
            location = control.getPlayerData(player).getZoneLocation();
        } else {
            player.sendMessage("You don't own this zone, your position will not be restored");
            location = player.getLocation();
        }

        Helper.teleportToZone(player, zoneOwner.getUniqueId().toString(), VoidWorld.getSuffix(player.getWorld()), location);
    }
}
