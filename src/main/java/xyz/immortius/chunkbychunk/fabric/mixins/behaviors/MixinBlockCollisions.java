package xyz.immortius.chunkbychunk.fabric.mixins.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.immortius.chunkbychunk.common.world.NetherChunkByChunkGenerator;
import xyz.immortius.chunkbychunk.fabric.mixins.EntityCollisionContextAccessor;

@Mixin(BlockCollisions.class)
public class MixinBlockCollisions {

    @Redirect(method = "computeNext()Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape redirectGetCollisionShape(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter instanceof ServerLevel serverLevel &&
                serverLevel.getChunkSource().getGenerator() instanceof NetherChunkByChunkGenerator &&
                collisionContext instanceof EntityCollisionContext &&
                !(((EntityCollisionContextAccessor) collisionContext).getEntity() instanceof Player) &&
                instance.getBlock() == Blocks.BARRIER &&
                blockPos.getY() == 0) {
            return Shapes.empty();
        }
        return instance.getCollisionShape(blockGetter, blockPos, collisionContext);
    }

}
