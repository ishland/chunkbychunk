package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.immortius.chunkbychunk.interop.ChunkByChunkConstants;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level {

    protected MixinServerLevel(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l) {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l);
    }

    @Shadow @Final private MinecraftServer server;

    @Shadow protected abstract void runBlockEvents();

    @Shadow @Final private PersistentEntitySectionManager<Entity> entityManager;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void beforeTick(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        //noinspection ConstantConditions
        if (this.server.getLevel(ChunkByChunkConstants.SKY_CHUNK_GENERATION_LEVEL) == (Object) this ||
        this.server.getLevel(ChunkByChunkConstants.NETHER_CHUNK_GENERATION_LEVEL) == (Object) this) {
            ci.cancel();
            this.updateSkyBrightness();
            this.getChunkSource().tick(booleanSupplier, true);
            this.runBlockEvents();
            this.entityManager.tick();
        }
    }

}
