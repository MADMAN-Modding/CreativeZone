package dev.madtechs.creativeZone.voidWorld;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;

import java.util.List;

public class VoidWorld {
    public static WorldCreator getVoidWorld(String playerUUID, World source) {
        WorldCreator worldCreator = new WorldCreator("c_zones/" + playerUUID + getSuffix(source));

        // Pull the dimension data from the source world
        worldCreator.environment(source.getEnvironment());
        worldCreator.seed(source.getSeed());

        worldCreator.biomeProvider(getBiomeProvider(source));
        worldCreator.generator(getChunkGenerator(source));

        return worldCreator;
    }

    /**
     * Biomes are read from the source world
     *
     * @return BiomeProvider
     */
    private static BiomeProvider getBiomeProvider(World source) {
        return new BiomeProvider() {
            @Override
            public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
                return source.getBiome(x, y, z);
            }

            @Override
            public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                
                return  RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).stream().toList();
            }
        };
    }

    private static ChunkGenerator getChunkGenerator(World source) {
        return new ChunkGenerator() {
            @Override
            public BiomeProvider getDefaultBiomeProvider(final @NotNull WorldInfo worldInfo) {
                return getBiomeProvider(source);
            }

            @Override
            public boolean shouldGenerateNoise() {
                return false;
            }

            @Override
            public boolean shouldGenerateSurface() {
                return false;
            }

            @Override
            public boolean shouldGenerateCaves() {
                return false;
            }

            @Override
            public boolean shouldGenerateDecorations() {
                return false;
            }

            @Override
            public boolean shouldGenerateMobs() {
                return true;
            }

            @Override
            public boolean shouldGenerateStructures() {
                return false;
            }
        };
    }

    /**
     * Keep the dimension separated
     * The overworld has no suffix
     * @param source
     * @return String
     */
    public static String getSuffix(World source) {
        // Keep the dimensions separated
        String suffix = "";

        if (source.getEnvironment() == World.Environment.NETHER) {
            suffix = "_nether";
        } else if (source.getEnvironment() == World.Environment.THE_END) {
            suffix = "_end";
        }

        return suffix;
    }
}