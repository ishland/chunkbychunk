package xyz.immortius.chunkbychunk.fabric.mixins;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import xyz.immortius.chunkbychunk.fabric.IServerPlayer;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IServerPlayer {

    private boolean cbc$isClientInstalled = false;

    @Override
    public boolean cbc$isClientInstalled() {
        return this.cbc$isClientInstalled;
    }

    @Override
    public void cbc$setClientInstalled(boolean clientInstalled) {
        this.cbc$isClientInstalled = clientInstalled;
    }
}
