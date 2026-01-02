package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.commands.ZoneCommand;
import org.bukkit.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CreativeZone extends JavaPlugin implements Listener {
    private static CreativeZone instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        instance = this;

        PluginCommand zoneCommand = getCommand("makeZone");

        zoneCommand.setExecutor(new ZoneCommand());
    }

    public static CreativeZone getInstance() {
        return instance;
    }
}
