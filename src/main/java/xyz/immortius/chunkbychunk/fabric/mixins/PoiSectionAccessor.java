package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PoiSection.class)
public interface PoiSectionAccessor {

    @Invoker
    boolean invokeAdd(PoiRecord poiRecord);

    @Accessor
    Runnable getSetDirty();

}
