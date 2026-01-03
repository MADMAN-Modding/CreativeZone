package dev.madtechs.creativeZone.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.dataControl.Control;
import dev.madtechs.creativeZone.voidWorld.VoidWorld;


public class GoToZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        Player zoneOwner;

        Control control = CreativeZone.getControl();

        control.setPreviousGameMode(player);

        if (args.length == 1) {
            String playerString = args[0];


            Player playerOwningZone = Bukkit.getPlayer(playerString);

            var playerData = control.getPlayerData(playerOwningZone);

            if (playerData.isPlayerAllowed(player)) {
                zoneOwner = playerOwningZone;         
                
                control.setPreviousGameMode(player);
            } else {
                player.sendMessage(player.getName() + " are not allowed in that zone, ask "+ playerOwningZone.getName() + " to allow you with /allowPlayer");
                return true;
            };
        } else {
            zoneOwner = player;
        }

        teleportToZone(player, zoneOwner, control);

        return true;
    }

    public static void teleportToZone(Player player, Player zoneOwner, Control control) {
        control.saveSurvival(player);
        
        
        VoidWorld.getVoidWorld(zoneOwner.getUniqueId().toString()).createWorld();

        var zone = new WorldCreator("c_zones/" + zoneOwner.getUniqueId().toString()).createWorld();

        var location = player.getLocation();

        location.setWorld(zone);
        
        player.teleport(location);

        player.setGameMode(GameMode.CREATIVE);

        control.loadCreative(player);
    }
}
