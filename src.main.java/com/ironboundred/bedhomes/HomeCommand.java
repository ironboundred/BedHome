package com.ironboundred.bedhomes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class HomeCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command is only for players to use!");
      return true;
    }

    Player player = (Player) sender;

    Location loc = player.getBedSpawnLocation();
    
    if(loc != null) {
      if (BedHomes.getInstance().huskHomesHook != null) {
        BedHomes.getInstance().huskHomesHook.teleportPlayer(player, loc);
        return true;
      }
      
      if(!BedHomes.getInstance().getConfig().getBoolean("UseWaitTime")) {
        player.sendMessage(ChatColor.YELLOW + "Teleporting to your home...");
        player.teleportAsync(loc, TeleportCause.PLUGIN);
        return true;
      }
      
      if(player.hasPermission("bedhome.time.bypass")) {
          player.sendMessage(ChatColor.YELLOW + "Teleporting to your home...");
          player.teleportAsync(loc, TeleportCause.PLUGIN);
      }else {
        player.sendMessage(ChatColor.YELLOW + "Teleporting to your home please standby...");
        player.sendMessage(ChatColor.YELLOW + "Wait time is: " + ChatColor.RED + BedHomes.getInstance().getConfig().getLong("WaitTime") + ChatColor.YELLOW + " sec");
        BedHomes.getInstance().getServer().getScheduler().runTaskLater(BedHomes.getInstance(), new Runnable() {
          public void run() {
            player.teleportAsync(loc, TeleportCause.PLUGIN);
          }
        }, BedHomes.getInstance().getConfig().getLong("WaitTime") * 20);
      }
    }else {
      player.sendMessage(ChatColor.GREEN
          + "You have not set a spawn location yet! You need to click on a bed to set your home.");
      return true;
    }
    
    return true;
  }
}
