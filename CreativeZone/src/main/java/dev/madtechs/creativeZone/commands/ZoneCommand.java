package dev.madtechs.creativeZone.commands;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import dev.madtechs.creativeZone.voidWorld.ZoneMaker;

public class ZoneCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CreativeZone plugin = CreativeZone.getInstance();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }
        
        WorldCreator creativeZone = VoidWorld.getVoidWorld();

        World currentWorld = new WorldCreator("world").createWorld();

        // Generate the creative world
        World creativeWorld = creativeZone.createWorld();

        player.setGameMode(GameMode.CREATIVE);

        Location playerLocation = player.getLocation();

        playerLocation.setWorld(creativeWorld);

        player.teleport(playerLocation);

        // Set the data
        ZoneMaker.makeWorld(creativeWorld, currentWorld, 4, player.getLocation(), plugin);

        return true;

    }
}
