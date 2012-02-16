package com.github.FrozenGamma.TimeTrail;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class TimeTrail extends JavaPlugin
{	
	static boolean counting = false;
	int ticks = 0;
	
	public SignListener signListener = new SignListener();
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(this.signListener, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
            @Override
            public void run()
            {
            	if(counting == true)
            	{
            		ticks = ticks + 1;
            		log.info(Integer.toString(ticks));
            		log.info(Boolean.toString(counting));
            	}
            }
        }, 0, 1);
	}
	
	public void onDisable()
	{
		
	}
}