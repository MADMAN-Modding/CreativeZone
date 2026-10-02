package dev.madtechs.creativeZone.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.madtechs.creativeZone.Constants;
import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import dev.madtechs.creativeZone.worldStateControl.ZoneGuard;

public class DeleteZone implements CommandExecutor {
    private final ZoneGuard guard;
    private final Plugin plugin;

    public DeleteZone(ZoneGuard guard, Plugin plugin) {
        this.guard = guard;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Control Checks

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        if (!player.getWorld().getName().toString().contains("c_zone")) {
            player.sendMessage("You aren't in a creative zone!");
            return true;
        }

        if (!player.getWorld().getName().toString().contains(player.getUniqueId().toString())) {
            player.sendMessage("This isn't your world!");
            return true;
        }

        // Message to send to the user
        String responseMessage = "";

        // Overworld has no extension
        String[] worlds = { "", "_nether", "_end" };

        for (String world : worlds) {
            attemptWorldDelete(sender, world, Constants.WORLD_DELETE_ATTEMPTS, player);
        }

        player.sendMessage(responseMessage);

        return true;

    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private void attemptWorldDelete(CommandSender sender, String worldName, int attemptsRemaining, Player player) {
        if (!guard.tryLock(worldName)) {
            if (attemptsRemaining <= 0) {
                sender.sendMessage("Zone still busy, giving up.");
                return;
            }
            Bukkit.getScheduler().runTaskLater(plugin,
                    () -> attemptWorldDelete(sender, worldName, attemptsRemaining - 1, player), 20L);
            return;
        } else {
            World zone = VoidWorld.getVoidWorld(player.getUniqueId().toString(), player.getWorld()).createWorld();

            zone.setAutoSave(false);

            for (Player zonePlayer : zone.getPlayers()) {
                new LeaveZone().leaveCZone(zonePlayer, sender);
            }

            boolean worldUnload = Bukkit.unloadWorld(zone, false);

            String playerUUID = player.getUniqueId().toString();

            File folder = new File(String.format("world/dimensions/minecraft/c_zones/%s%s", playerUUID, worldName));

            if (deleteDirectory(folder) && worldUnload) {
                player.sendMessage("Successfully deleted world" + worldName);
            } else {
                player.sendMessage("Failed to delete world" + worldName);
            }
        }
    }
}
