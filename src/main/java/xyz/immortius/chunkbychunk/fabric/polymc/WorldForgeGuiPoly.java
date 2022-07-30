package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.api.gui.GuiPoly;
import io.github.theepicblock.polymc.impl.poly.gui.GuiUtils;
import io.github.theepicblock.polymc.impl.poly.gui.StaticSlot;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.immortius.chunkbychunk.common.menus.WorldForgeMenu;

import java.util.List;

public class WorldForgeGuiPoly implements GuiPoly {
    @Override
    public AbstractContainerMenu replaceScreenHandler(AbstractContainerMenu base, ServerPlayer player, int syncId) {
        if (base instanceof WorldForgeMenu menu) {
            return new WorldForgePolyHandler(menu, syncId, player);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static class WorldForgePolyHandler extends AbstractContainerMenu {


        private static final Item[] PROGRESS = {
                Items.GRAY_STAINED_GLASS_PANE,
                Items.RED_STAINED_GLASS_PANE,
                Items.PINK_STAINED_GLASS_PANE,
                Items.YELLOW_STAINED_GLASS_PANE,
                Items.LIME_STAINED_GLASS_PANE,
                Items.GREEN_STAINED_GLASS_PANE
        };

        private static Item getProgress(double progress) {
            return PROGRESS[(int) Math.ceil(progress * (PROGRESS.length - 1))];
        }

        private final WorldForgeMenu menu;
        private final ServerPlayer player;

        protected WorldForgePolyHandler(WorldForgeMenu menu, int syncId, ServerPlayer player) {
            super(MenuType.HOPPER, syncId);
            this.menu = menu;
            this.player = player;

            final List<Slot> originalSlots = GuiUtils.removePlayerSlots(menu.slots);
            this.addSlot(new StaticSlot(new ItemStack(Items.BLACK_STAINED_GLASS_PANE)));
            this.addSlot(originalSlots.get(0));
            this.addSlot(new NotVeryStaticSlot(() -> {
                final double progress = menu.getProgress() / (double) menu.getGoal();
                return new ItemStack(getProgress(progress))
                        .setHoverName(new TextComponent("Progress: %.1f%%".formatted(progress * 100.0)));
            }));
            this.addSlot(originalSlots.get(1));
            this.addSlot(new StaticSlot(new ItemStack(Items.BLACK_STAINED_GLASS_PANE)));

            // Player inventory
            for (int y = 0; y < 3; ++y) {
                for (int x = 0; x < 9; ++x) {
                    this.addSlot(new Slot(player.getInventory(), x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
                }
            }

            // Player hotbar
            for (int hotbar = 0; hotbar < 9; ++hotbar) {
                this.addSlot(new Slot(player.getInventory(), hotbar, 8 + hotbar * 18, 142));
            }
        }

        @Override
        public boolean stillValid(Player player) {
            return menu.stillValid(player);
        }

        @Override
        public ItemStack quickMoveStack(Player player, int i) {
            return menu.quickMoveStack(player, 0);
        }
    }

}
