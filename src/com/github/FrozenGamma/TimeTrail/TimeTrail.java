package com.github.FrozenGamma.TimeTrail;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class TimeTrail extends JavaPlugin
{	
	public SignListener signListener = new SignListener();
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(this.signListener, this);
	}
	
	public void onDisable()
	{
		
	}
	
	public static void ticks(final Player player)
	{
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(new TimeTrail(), new Runnable()
		{
            @Override
            public void run()
            {
            	if(com.github.FrozenGamma.TimeTrail.SignListener.counting.get(player))
            	{
            		com.github.FrozenGamma.TimeTrail.SignListener.amountticks(player);
            	}
            }
        }, 0, 1);
	}
}