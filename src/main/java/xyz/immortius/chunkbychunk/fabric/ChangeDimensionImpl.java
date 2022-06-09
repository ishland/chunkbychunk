package xyz.immortius.chunkbychunk.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

/**
 * Implementation for changing the dimension of entities by providing portal information
 */
public final class ChangeDimensionImpl {

    private static final ThreadLocal<PortalInfo> portalInfo = new ThreadLocal<>();

    private ChangeDimensionImpl() {

    }

    public static PortalInfo getPortalInfo() {
        return portalInfo.get();
    }

    public static Entity changeDimension(Entity entity, ServerLevel level, PortalInfo info) {
        portalInfo.set(info);
        Entity result = entity.changeDimension(level);
        portalInfo.remove();
        return result;
    }
}
