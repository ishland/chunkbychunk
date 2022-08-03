package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.world.level.chunk.storage.SectionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SectionStorage.class)
public interface SectionStorageAccessor<R> {

    @Invoker
    R invokeGetOrCreate(long l);

}
