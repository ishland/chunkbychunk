package xyz.immortius.chunkbychunk.quilt.mixins;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Mixin to support accessing and adding to the list of WorldPresets
 */
@Mixin(BucketItem.class)
public interface BucketFluidAccessor {
    @Accessor("content")
    public Fluid getFluid();
}
