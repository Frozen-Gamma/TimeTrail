package com.github.FrozenGamma.TimeTrail;

import org.bukkit.event.Listener;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Sign;

public class SignListener extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");

	@EventHandler
	public static void signListener(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if(action == Action.RIGHT_CLICK_BLOCK && (block.getType() == Material.SIGN || (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN))) 
		{
			Sign s = (Sign)event.getClickedBlock().getState();
			String signText = s.getLine(0);
		    if(signText.equalsIgnoreCase("[TimeTrail]"))
		    {
		    	if(com.github.FrozenGamma.TimeTrail.TimeTrail.counting == false)
		    	{
		    		com.github.FrozenGamma.TimeTrail.TimeTrail.counting = true;
		    	}
		    	else
		    	{
		    		com.github.FrozenGamma.TimeTrail.TimeTrail.counting = false;
		    	}
		    }
		}
	}
}