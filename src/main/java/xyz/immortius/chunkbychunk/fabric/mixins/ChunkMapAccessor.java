package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChunkMap.class)
public interface ChunkMapAccessor {

    @Invoker
    void invokeUpdateChunkTracking(ServerPlayer serverPlayer, ChunkPos chunkPos, MutableObject<ClientboundLevelChunkWithLightPacket> mutableObject, boolean bl, boolean bl2);

    @Invoker
    ChunkHolder invokeGetUpdatingChunkIfPresent(long l);

}
