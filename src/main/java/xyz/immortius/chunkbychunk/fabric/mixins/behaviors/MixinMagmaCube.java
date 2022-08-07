package xyz.immortius.chunkbychunk.fabric.mixins.behaviors;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MagmaCube.class)
public class MixinMagmaCube extends Slime {

    public MixinMagmaCube(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "jumpInLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/MagmaCube;setDeltaMovement(DDD)V"), index = 1)
    private double redirectJumpSetDeltaMovement(double par1) {
        final Vec3 deltaMovement = this.getDeltaMovement();
        if (deltaMovement.y < -0.2D) {
            return deltaMovement.y + par1;
        } else {
            return par1;
        }
    }

}
