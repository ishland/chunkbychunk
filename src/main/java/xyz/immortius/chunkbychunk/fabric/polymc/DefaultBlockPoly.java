package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.api.block.BlockPoly;
import net.minecraft.world.level.block.state.BlockState;

public class DefaultBlockPoly implements BlockPoly {

    @Override
    public BlockState getClientBlock(BlockState input) {
        return null;
    }

}
