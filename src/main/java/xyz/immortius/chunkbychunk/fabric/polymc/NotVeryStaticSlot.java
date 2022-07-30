package xyz.immortius.chunkbychunk.fabric.polymc;

import io.github.theepicblock.polymc.impl.poly.gui.StaticSlot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class NotVeryStaticSlot extends StaticSlot {
    private final Supplier<ItemStack> stackSupplier;

    public NotVeryStaticSlot(Supplier<ItemStack> stackSupplier) {
        super(ItemStack.EMPTY);
        this.stackSupplier = stackSupplier;
    }

    public void onQuickCraft(ItemStack originalItem, ItemStack itemStack) {
        throw new AssertionError("PolyMc: the contents of a static, unchangeable slot were changed. Containing: " + this.stackSupplier.toString());
    }

    @Override
    public ItemStack getItem() {
        return this.stackSupplier.get();
    }
}
