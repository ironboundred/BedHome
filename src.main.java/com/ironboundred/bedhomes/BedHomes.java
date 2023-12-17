package com.ironboundred.bedhomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


public class BedHomes extends JavaPlugin {
  private static BedHomes instance;
  public Logger logger;
  public HuskHomeHook huskHomesHook;

  public static BedHomes getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;

    logger = instance.getLogger();

    instance.saveDefaultConfig();
    
    instance.getCommand("bed").setExecutor(new HomeCommand());

    if (instance.getServer().getPluginManager().getPlugin("HuskHomes") != null) {
      this.huskHomesHook = new HuskHomeHook();
    }
    
    logger.info(instance.getName() + " has been enabled!");
  }

  @Override
  public void onDisable() {
    logger.info("Disabled " + instance.getDescription().getName() + ".");
  }

}
