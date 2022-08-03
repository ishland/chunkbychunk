package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerChunkCache.class)
public interface ServerChunkCacheAccessor {

    @Invoker
    boolean invokeRunDistanceManagerUpdates();

    @Accessor
    ServerChunkCache.MainThreadExecutor getMainThreadProcessor();

}
