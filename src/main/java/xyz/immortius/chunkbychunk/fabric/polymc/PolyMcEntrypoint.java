package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.PolyMc;
import io.github.theepicblock.polymc.api.PolyRegistry;
import io.github.theepicblock.polymc.api.misc.PolyMapProvider;
import xyz.immortius.chunkbychunk.fabric.ChunkByChunkMod;

public class PolyMcEntrypoint implements io.github.theepicblock.polymc.api.PolyMcEntrypoint {

    static {
        PolyMapProvider.EVENT.register(player -> new DelegatingPolyMap(PolyMc.getGeneratedMap(), player));
    }

    @Override
    public void registerPolys(PolyRegistry registry) {
        registry.registerGuiPoly(ChunkByChunkMod.BEDROCK_CHEST_MENU, new BedrockChestGuiPoly());
        registry.registerGuiPoly(ChunkByChunkMod.WORLD_FORGE_MENU, new WorldForgeGuiPoly());
        registry.registerGuiPoly(ChunkByChunkMod.WORLD_SCANNER_MENU, new WorldScannerGuiPoly());
    }
}
