package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MappedRegistry.class)
public interface DefrostedRegistry {
    @Accessor("frozen")
    public void setFrozen(boolean newFrozen);
}
