package xyz.immortius.chunkbychunk.common.world;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

public class WrappingImposterProtoChunk extends ImposterProtoChunk {

    private ChunkStatus status;

    public WrappingImposterProtoChunk(LevelChunk levelChunk, ChunkStatus initialStatus) {
        super(levelChunk, true);
        this.status = initialStatus;
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        System.out.println("ChunkByChunk: Adding entity %s".formatted(entity));
        this.getWrapped().getLevel().addFreshEntity(entity);
    }

    @Override
    public void setStatus(ChunkStatus chunkStatus) {
        this.status = chunkStatus;
    }

    @Override
    public ChunkStatus getStatus() {
        return this.status;
    }
}
