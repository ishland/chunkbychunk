package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.api.gui.GuiPoly;
import io.github.theepicblock.polymc.impl.poly.gui.StaticSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.immortius.chunkbychunk.common.menus.WorldScannerMenu;

public class WorldScannerGuiPoly implements GuiPoly {
    @Override
    public AbstractContainerMenu replaceScreenHandler(AbstractContainerMenu base, ServerPlayer player, int syncId) {
        if (base instanceof WorldScannerMenu menu) {
            return new WorldScannerHandler(menu, syncId, player);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static class WorldScannerHandler extends AbstractContainerMenu {

        private final WorldScannerMenu menu;
        private final ServerPlayer player;

        protected WorldScannerHandler(WorldScannerMenu menu, int syncId, ServerPlayer player) {
            super(MenuType.HOPPER, syncId);
            this.menu = menu;
            this.player = player;

            final ItemStack stack = new ItemStack(Items.BARRIER)
                    .setHoverName(new TextComponent("Please install or update chunkbychunk on client to view this container")
                            .withStyle(ChatFormatting.RED));
            this.addSlot(new StaticSlot(stack));
            this.addSlot(new StaticSlot(stack));
            this.addSlot(new StaticSlot(stack));
            this.addSlot(new StaticSlot(stack));
            this.addSlot(new StaticSlot(stack));

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
