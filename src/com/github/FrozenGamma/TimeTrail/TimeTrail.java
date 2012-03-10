package com.github.FrozenGamma.TimeTrail;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class TimeTrail extends JavaPlugin
{	
	public static boolean Permissions = false;
	public SignListener signListener = new SignListener();
	static Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		if(pm.isPluginEnabled("bPermissions") || pm.isPluginEnabled("PermissionsEx"))
		{
			Permissions = true;
		}
		pm.registerEvents(this.signListener, this);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
            @Override
            public void run()
            {
            	if(SignListener.player != null)
            	{
                	SignListener.ticks();
            	}
            }
        }, 0, 1);
	}
	
	public void onDisable()
	{
		
	}
}