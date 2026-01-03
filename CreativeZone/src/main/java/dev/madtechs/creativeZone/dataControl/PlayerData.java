package dev.madtechs.creativeZone.dataControl;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerData {

    private final UUID playerUUID;
    private final ArrayList<UUID> allowedPlayers;

    private String previousWorld;
    private GameMode previousGameMode;

    // Inventory snapshots
    private ItemStack[] survivalContents;
    private ItemStack[] survivalArmor;
    private ItemStack survivalOffhand;

    private ItemStack[] creativeContents;
    private ItemStack[] creativeArmor;
    private ItemStack creativeOffhand;

    // XP snapshots
    private int survivalLevel;
    private float survivalExp;
    private int survivalTotalExp;

    private int creativeLevel;
    private float creativeExp;
    private int creativeTotalExp;

    public PlayerData(UUID playerUUID, ArrayList<UUID> allowedPlayers,
            String previousWorld, GameMode previousGameMode) {
        this.playerUUID = playerUUID;
        this.allowedPlayers = allowedPlayers;
        this.previousWorld = previousWorld;
        this.previousGameMode = previousGameMode;
    }

    /* ===================== */
    /* BASIC GET / SET */
    /* ===================== */

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public ArrayList<UUID> getAllowedPlayers() {
        return allowedPlayers;
    }

    public String getPreviousWorld() {
        return previousWorld;
    }

    public void setPreviousWorld(String previousWorld) {
        this.previousWorld = previousWorld;
    }

    public GameMode getPreviousGameMode() {
        var defaultGameMode = Bukkit.getDefaultGameMode();

        // If no previous gameMode was set, get the world default and set gameMode to that
        if (previousGameMode == null)
            return defaultGameMode;
        else
            return previousGameMode;
    }

    public void setPreviousGameMode(GameMode previousGameMode) {
        this.previousGameMode = previousGameMode;
    }

    /* ===================== */
    /* INVENTORY + XP SAVE */
    /* ===================== */

    public void saveSurvival(Player player) {
        PlayerInventory inv = player.getInventory();
        this.survivalContents = copy(inv.getContents());
        this.survivalArmor = copy(inv.getArmorContents());
        this.survivalOffhand = cloneItem(inv.getItemInOffHand());

        this.survivalLevel = player.getLevel();
        this.survivalExp = player.getExp();
        this.survivalTotalExp = player.getTotalExperience();
    }

    public void saveCreative(Player player) {
        PlayerInventory inv = player.getInventory();
        this.creativeContents = copy(inv.getContents());
        this.creativeArmor = copy(inv.getArmorContents());
        this.creativeOffhand = cloneItem(inv.getItemInOffHand());

        this.creativeLevel = player.getLevel();
        this.creativeExp = player.getExp();
        this.creativeTotalExp = player.getTotalExperience();
    }

    public void loadSurvival(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        if (survivalContents != null)
            inv.setContents(copy(survivalContents));
        if (survivalArmor != null)
            inv.setArmorContents(copy(survivalArmor));
        inv.setItemInOffHand(cloneItem(survivalOffhand));

        restoreXP(player, survivalLevel, survivalExp, survivalTotalExp);
    }

    public void loadCreative(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        if (creativeContents != null)
            inv.setContents(copy(creativeContents));
        if (creativeArmor != null)
            inv.setArmorContents(copy(creativeArmor));
        inv.setItemInOffHand(cloneItem(creativeOffhand));

        restoreXP(player, creativeLevel, creativeExp, creativeTotalExp);
    }

    /* ===================== */
    /* XP RESTORE */
    /* ===================== */

    private void restoreXP(Player player, int level, float exp, int total) {
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);

        player.setLevel(level);
        player.setExp(exp);
        player.setTotalExperience(total);
    }

    /* ===================== */
    /* ALLOWED PLAYERS */
    /* ===================== */

    public boolean isPlayerAllowed(Player player) {
        return isPlayerAllowed(player.getUniqueId());
    }

    public boolean isPlayerAllowed(UUID player) {
        return allowedPlayers.contains(player);
    }

    public void allowPlayer(String playerName) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            allowedPlayers.add(player.getUniqueId());
        }
    }

    /* ===================== */
    /* CLONE UTILITIES */
    /* ===================== */

    private ItemStack[] copy(ItemStack[] contents) {
        if (contents == null)
            return null;

        ItemStack[] clone = new ItemStack[contents.length];
        for (int i = 0; i < contents.length; i++) {
            clone[i] = cloneItem(contents[i]);
        }
        return clone;
    }

    private ItemStack cloneItem(ItemStack item) {
        return item == null ? null : item.clone();
    }
}
