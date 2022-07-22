package xyz.immortius.chunkbychunk.interop;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.PortalInfo;
import xyz.immortius.chunkbychunk.fabric.ChangeDimensionImpl;
import xyz.immortius.chunkbychunk.fabric.mixins.BucketFluidAccessor;
import xyz.immortius.chunkbychunk.fabric.mixins.ChunkGeneratorStructureAccessor;
import xyz.immortius.chunkbychunk.fabric.mixins.NoiseBasedChunkGeneratorMixin;

import java.util.Optional;

/**
 * Static methods whose implementation varies by mod system
 */
public final class CBCInteropMethods {

    private CBCInteropMethods() {

    }

    /**
     * Change the dimension of an entity.
     * For fabric, we use a mixin to enable dimension changes
     *
     * @param entity     The entity to move
     * @param level      The level to move the entity to
     * @param portalInfo Portal information for the move
     * @return The moved entity
     */
    public static Entity changeDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        return ChangeDimensionImpl.changeDimension(entity, level, portalInfo);
    }

    /**
     * @param bucket The bucket to get the contents of
     * @return The fluid contents of the bucket (may be null)
     */
    public static Fluid getBucketContents(BucketItem bucket) {
        return ((BucketFluidAccessor) bucket).getFluid();
    }

    /**
     * @param generator
     * @return The structure sets for a given generator
     */
    public static Registry<StructureSet> getStructureSets(ChunkGenerator generator) {
        return ((ChunkGeneratorStructureAccessor) generator).getStructureSet();
    }

    /**
     * @param generator
     * @return The structure overrides for a given generator
     */
    public static Optional<HolderSet<StructureSet>> getStructureOverrides(ChunkGenerator generator) {
        return ((ChunkGeneratorStructureAccessor) generator).getStructureOverrides();
    }

    /**
     * @param parent
     * @return The NoiseGeneratorSettings from a chunk generator
     */
    public static Holder<NoiseGeneratorSettings> getNoiseGeneratorSettings(ChunkGenerator parent) {
        if (parent instanceof NoiseBasedChunkGenerator noiseParent) {
            return ((NoiseBasedChunkGeneratorMixin) noiseParent).getSettings();
        } else {
            return NoiseGeneratorSettings.bootstrap();
        }
    }

    /**
     * @param parent
     * @return The NoiseParameters from a chunk generator
     */
    public static Registry<NormalNoise.NoiseParameters> getNoiseParamsRegistry(ChunkGenerator parent) {
        if (parent instanceof NoiseBasedChunkGenerator noiseParent) {
            return ((NoiseBasedChunkGeneratorMixin)noiseParent).getNoises();
        } else {
            return BuiltinRegistries.NOISE;
        }
    }
}
