package xyz.immortius.chunkbychunk.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.immortius.chunkbychunk.client.screens.BedrockChestScreen;
import xyz.immortius.chunkbychunk.client.screens.WorldForgeScreen;
import xyz.immortius.chunkbychunk.client.screens.WorldScannerScreen;
import xyz.immortius.chunkbychunk.config.ChunkByChunkConfig;
import xyz.immortius.chunkbychunk.interop.ChunkByChunkConstants;

/**
 * Client-only mod initialization
 */
public class ChunkByChunkClientMod implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(ChunkByChunkConstants.MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client Initializing");
        MenuScreens.register(ChunkByChunkConstants.bedrockChestMenu(), BedrockChestScreen::new);
        MenuScreens.register(ChunkByChunkConstants.worldForgeMenu(), WorldForgeScreen::new);
        MenuScreens.register(ChunkByChunkConstants.worldScannerMenu(), WorldScannerScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(ChunkByChunkMod.CONFIG_PACKET, (client, handler, buf, responseSender) -> {
            LOGGER.info("Receiving config from server");
            ChunkByChunkConfig.get().getGameplayConfig().setBlockPlacementAllowedOutsideSpawnedChunks(buf.readBoolean());
            responseSender.sendPacket(ChunkByChunkMod.CONFIG_PACKET, PacketByteBufs.create());
        });
    }

}
