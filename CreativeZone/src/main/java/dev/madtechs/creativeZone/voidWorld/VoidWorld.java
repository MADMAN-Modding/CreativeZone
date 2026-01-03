package dev.madtechs.creativeZone.voidWorld;

import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class VoidWorld {
    public static WorldCreator getVoidWorld(String playerUUID) {
        WorldCreator worldCreator = new WorldCreator("c_zones/" + playerUUID);

        worldCreator.biomeProvider(getBiomeProvider());
        worldCreator.generator(getChunkGenerator());

        return worldCreator;
    }

    /**
     * A biome that is only the void
     *
     * @return BiomeProvider
     */
    private static BiomeProvider getBiomeProvider() {
        return new BiomeProvider() {
            @Override
            public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int i, int i1, int i2) {
                return Biome.THE_VOID;
            }

            @Override
            public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                return List.of(Biome.THE_VOID);
            }
        };
    }

    private static ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public BiomeProvider getDefaultBiomeProvider(final @NotNull WorldInfo worldInfo) {
                return getBiomeProvider();
            }

            @Override
            public boolean shouldGenerateNoise() {
                return false;
            }

            @Override
            public boolean shouldGenerateNoise(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX,
                    final int chunkZ) {
                return false;
            }

            @Override
            public boolean shouldGenerateSurface() {
                return false;
            }

            @Override
            public boolean shouldGenerateSurface(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX, final int chunkZ) {
                return false;
            }

            @Override
            public boolean shouldGenerateCaves() {
                return false;
            }

            @Override
            public boolean shouldGenerateCaves(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX,
                    final int chunkZ) {
                return false;
            }

            @Override
            public boolean shouldGenerateDecorations() {
                return false;
            }

            @Override
            public boolean shouldGenerateDecorations(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX, final int chunkZ) {
                return false;
            }

            @Override
            public boolean shouldGenerateMobs() {
                return true;
            }

            @Override
            public boolean shouldGenerateMobs(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX,
                    final int chunkZ) {
                return true;
            }

            @Override
            public boolean shouldGenerateStructures() {
                return false;
            }

            @Override
            public boolean shouldGenerateStructures(final @NotNull WorldInfo worldInfo, final @NotNull Random random,
                    final int chunkX, final int chunkZ) {
                return false;
            }

        };
    }

}
