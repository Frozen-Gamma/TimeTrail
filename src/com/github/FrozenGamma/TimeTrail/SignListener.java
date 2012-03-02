package com.github.FrozenGamma.TimeTrail;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.HashMap;
import java.util.Map;
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
	public static boolean playerNotNull = false;
	static Player player;
	public static Map<Player, Boolean> counting = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> ticks = new HashMap<Player, Integer>();
	static Logger log = Logger.getLogger("Minecraft");
	
	@EventHandler
	public static void signListener(PlayerInteractEvent event)
	{
		player = (Player) event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if(action == Action.RIGHT_CLICK_BLOCK && (block.getType() == Material.SIGN || (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN))) 
		{
			try
			{
				Sign s = (Sign) event.getClickedBlock().getState();
				String signText = s.getLine(0);
		    	if(signText.equalsIgnoreCase("[TimeTrail]"))
		    	{
		    		if(!counting.get(player))
		    		{
		    			ticks.put(player, 0);
		    			counting.put(player, true);
		    		}
		    		else
		    		{
		    			counting.put(player, false);
		    			log.info(ticks.toString());
		    		}
		    	}
			}
			catch (Exception e)
			{
				counting.put(player, false);
				Sign s = (Sign) event.getClickedBlock().getState();
				String signText = s.getLine(0);
		    	if(signText.equalsIgnoreCase("[TimeTrail]"))
		    	{
		    		ticks.put(player, 0);
		    		counting.put(player, true);
		    		playerNotNull = true;
		    	}
			}
		}
	}

	public static void ticks()
	{
		if(counting.get(player))
		{
			ticks.put(player, ticks.get(player) + 1);			
			//log.info(counting.toString()); // Show the value of counting
			//log.info(ticks.toString()); // Show the value of ticks
			// Both are used as debugging code
		}		
	}
}