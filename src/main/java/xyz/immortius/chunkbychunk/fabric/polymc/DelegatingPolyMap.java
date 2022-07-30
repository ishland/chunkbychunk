package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.api.PolyMap;
import io.github.theepicblock.polymc.api.block.BlockPoly;
import io.github.theepicblock.polymc.api.entity.EntityPoly;
import io.github.theepicblock.polymc.api.gui.GuiPoly;
import io.github.theepicblock.polymc.api.item.ItemLocation;
import io.github.theepicblock.polymc.api.item.ItemPoly;
import io.github.theepicblock.polymc.api.resource.PolyMcResourcePack;
import io.github.theepicblock.polymc.impl.misc.logging.SimpleLogger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.immortius.chunkbychunk.fabric.IServerPlayer;
import xyz.immortius.chunkbychunk.interop.ChunkByChunkConstants;

public class DelegatingPolyMap implements PolyMap {

    private final PolyMap delegate;
    private final ServerPlayer player;

    public DelegatingPolyMap(PolyMap delegate, ServerPlayer player) {
        this.delegate = delegate;
        this.player = player;
    }

    @Override
    public ItemStack getClientItem(ItemStack serverItem, @Nullable ServerPlayer player, @Nullable ItemLocation location) {
        if (player != null) {
            final ResourceLocation key = Registry.ITEM.getKey(serverItem.getItem());
            if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
                return serverItem;
            }
        }
        return this.delegate.getClientItem(serverItem, player, location);
    }

    @Override
    public ItemPoly getItemPoly(Item item) {
        final ResourceLocation key = Registry.ITEM.getKey(item);
        if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
            return null;
        }
        return this.delegate.getItemPoly(item);
    }

    @Override
    public BlockPoly getBlockPoly(Block block) {
        final ResourceLocation key = Registry.BLOCK.getKey(block);
        if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
            return null;
        }
        return this.delegate.getBlockPoly(block);
    }

    @Override
    public GuiPoly getGuiPoly(MenuType<?> serverGuiType) {
        final ResourceLocation key = Registry.MENU.getKey(serverGuiType);
        if (key != null && key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
            return null;
        }
        return this.delegate.getGuiPoly(serverGuiType);
    }

    @Override
    public <T extends Entity> EntityPoly<T> getEntityPoly(EntityType<T> entity) {
        final ResourceLocation key = Registry.ENTITY_TYPE.getKey(entity);
        if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
            return null;
        }
        return this.delegate.getEntityPoly(entity);
    }

    @Override
    public ItemStack reverseClientItem(ItemStack clientItem) {
        return this.delegate.reverseClientItem(clientItem);
    }

    @Override
    public boolean isVanillaLikeMap() {
        return this.delegate.isVanillaLikeMap();
    }

    @Override
    public boolean hasBlockWizards() {
        return this.delegate.hasBlockWizards();
    }

    @Override
    public @Nullable PolyMcResourcePack generateResourcePack(SimpleLogger logger) {
        return this.delegate.generateResourcePack(logger);
    }

    @Override
    public String dumpDebugInfo() {
        return String.format("Delegate{%s}", this.delegate.dumpDebugInfo());
    }

    @Override
    public BlockState getClientBlock(BlockState serverBlock) {
        final ResourceLocation key = Registry.BLOCK.getKey(serverBlock.getBlock());
        if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
            return serverBlock;
        }
        return this.delegate.getClientBlock(serverBlock);
    }

    @Override
    public int getClientStateRawId(BlockState state, ServerPlayer player) {
        if (player != null) {
            final ResourceLocation key = Registry.BLOCK.getKey(state.getBlock());
            if (key.getNamespace().equals(ChunkByChunkConstants.MOD_ID) && ((IServerPlayer) player).cbc$isClientInstalled()) {
                return Block.BLOCK_STATE_REGISTRY.getId(state);
            }
        }
        return this.delegate.getClientStateRawId(state, player);
    }
}
