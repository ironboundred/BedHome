package com.ironboundred.bedhomes;

import net.william278.huskhomes.api.HuskHomesAPI;
import net.william278.huskhomes.position.Position;
import net.william278.huskhomes.position.World;
import net.william278.huskhomes.teleport.TeleportationException;
import net.william278.huskhomes.user.OnlineUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HuskHomeHook {
    private final HuskHomesAPI huskHomesAPI;

    public HuskHomeHook() {
        this.huskHomesAPI = HuskHomesAPI.getInstance();
    }

    public void teleportPlayer(Player player, Location location) {
        OnlineUser onlineUser = huskHomesAPI.adaptUser(player);

        Position position = Position.at(location.getX(), location.getY(), location.getZ(),
                World.from(location.getWorld().getName(), location.getWorld().getUID()),
                onlineUser.getPosition().getServer());

        try {
            huskHomesAPI.teleportBuilder()
                    .teleporter(onlineUser)
                    .target(position)
                    .toTimedTeleport()
                    .execute();
        }catch (TeleportationException e) {
            e.printStackTrace();
        }
    }
}
