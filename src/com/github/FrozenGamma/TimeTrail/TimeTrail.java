package com.github.FrozenGamma.TimeTrail;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class TimeTrail extends JavaPlugin{
	
	public final SignListener signListener = new SignListener();
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(this.signListener, this);
	}
	
	public void onDisable(){
		
	}

}