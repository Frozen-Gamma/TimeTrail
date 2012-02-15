package com.github.FrozenGamma.TimeTrail;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class TimeTrail extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
		log.info("TimeTrail has been enabled.");
	}
	
	public void onDisable(){
		log.info("TimeTrail has been disabled.");
	}
}