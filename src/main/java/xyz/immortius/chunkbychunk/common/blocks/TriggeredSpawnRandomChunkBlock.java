package xyz.immortius.chunkbychunk.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.immortius.chunkbychunk.common.blockEntities.TriggeredSpawnRandomChunkBlockEntity;
import xyz.immortius.chunkbychunk.interop.ChunkByChunkConstants;

import java.util.Random;

/**
 * Spawns a pseudo-random chunk from the generation dimension (position determined from block position).
 * Pseudo-random used so that the same chunk is forced, generated and unforced.
 */
public class TriggeredSpawnRandomChunkBlock extends AbstractTriggeredSpawnChunkBlock {

    public TriggeredSpawnRandomChunkBlock(Properties blockProperties) {
        super(blockProperties, TriggeredSpawnRandomChunkBlock::getSourceChunk);
    }

    public static ChunkPos getSourceChunk(BlockPos targetBlockPos) {
        Random random = new Random(targetBlockPos.asLong());
        return new ChunkPos(random.nextInt(Short.MAX_VALUE * 2) + Short.MIN_VALUE, random.nextInt(Short.MAX_VALUE * 2) + Short.MIN_VALUE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TriggeredSpawnRandomChunkBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide ? null : createTickerHelper(entityType, ChunkByChunkConstants.triggeredSpawnRandomChunkEntity(), TriggeredSpawnRandomChunkBlockEntity::serverTick);
    }
}
