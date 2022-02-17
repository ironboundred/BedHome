package com.ironboundred.bedhomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


public class BedHomes extends JavaPlugin {
  private static BedHomes instance;
  public Logger logger;

  public static BedHomes getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;

    logger = instance.getLogger();

    instance.saveDefaultConfig();
    
    instance.getCommand("home").setExecutor(new HomeCommand());
    
    logger.info(instance.getName() + " has been enabled!");
  }

  @Override
  public void onDisable() {
    logger.info("Disabled " + instance.getDescription().getName() + ".");
  }
}
