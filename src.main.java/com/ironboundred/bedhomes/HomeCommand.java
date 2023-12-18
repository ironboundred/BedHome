package com.ironboundred.bedhomes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeCommand extends Command {

  public HomeCommand() {
    super("bed", "teleport to you beds location", "/bed", new ArrayList<>());
    super.setPermission("bedhome.time.bypass");
  }

  @Override
  public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(Component.text("This command is only for players to use!"));
      return true;
    }

    Player player = (Player) commandSender;

    Location loc = player.getBedSpawnLocation();

    if(loc != null) {
      if (BedHomes.getInstance().huskHomesHook != null) {
        BedHomes.getInstance().huskHomesHook.teleportPlayer(player, loc);
        return true;
      }

      if(!BedHomes.getInstance().getConfig().getBoolean("UseWaitTime")) {
        player.sendMessage(Component.text("Teleporting to your home...").color(NamedTextColor.YELLOW));
        player.teleportAsync(loc, TeleportCause.PLUGIN);
        return true;
      }

      if(player.hasPermission("bedhome.time.bypass")) {
        player.sendMessage(Component.text("Teleporting to your home...").color(NamedTextColor.YELLOW));
        player.teleportAsync(loc, TeleportCause.PLUGIN);
      }else {
        player.sendMessage(Component.text("Teleporting to your home...").color(NamedTextColor.YELLOW));
        player.sendMessage(Component.text("Wait time is: ")
                .color(NamedTextColor.YELLOW)
                .append(Component.text(BedHomes.getInstance().getConfig().getLong("WaitTime"))
                        .color(NamedTextColor.RED))
                .append(Component.text(" sec").color(NamedTextColor.YELLOW)));
        BedHomes.getInstance().getServer().getScheduler().runTaskLater(BedHomes.getInstance(), new Runnable() {
          public void run() {
            player.teleportAsync(loc, TeleportCause.PLUGIN);
          }
        }, BedHomes.getInstance().getConfig().getLong("WaitTime") * 20);
      }
    }else {
      player.sendMessage(Component.text("You have not set a spawn location yet! You need to click on a bed to set your home.")
              .color(NamedTextColor.GREEN));
      return true;
    }

    return true;
  }
}
