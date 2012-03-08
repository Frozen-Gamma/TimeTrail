package com.github.FrozenGamma.TimeTrail;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Sign;

public class SignListener extends JavaPlugin implements Listener
{
	public static Player[] playersOnline;
	static Player player, playerLogin, playerDeath;
	public static Map<Player, Boolean> counting = new HashMap<Player, Boolean>();
	public static Map<Player, Double> ticks = new HashMap<Player, Double>();
	static Logger log = Logger.getLogger("Minecraft");
	
	@EventHandler
	public static void playerLogin(PlayerLoginEvent event)
	{
		playerLogin = event.getPlayer();
		counting.put(playerLogin, false);
		ticks.put(playerLogin, 0.00);
	}
	
	@EventHandler
	public static void deathEvent(PlayerDeathEvent event)
	{
		playerDeath = (Player) event.getEntity();
		if(counting.get(playerDeath))
		{
			playerDeath.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You died, counting has stopped.");
			counting.put(playerDeath, false);
			ticks.put(playerDeath, 0.00);
		}
	}
	
	@EventHandler
	public static void signListener(PlayerInteractEvent event)
	{
		player = (Player) event.getPlayer();
		Block block = event.getClickedBlock();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (block.getType() == Material.SIGN || (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN))) 
		{
			Sign s = (Sign) event.getClickedBlock().getState();
			String signText = s.getLine(0);
		    
			if(signText.equalsIgnoreCase("[TimeTrail]"))
		    {
		    	if(!counting.get(player))
		    	{
		    		ticks.put(player, 0.00);
		    		counting.put(player, true);
		    		player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Counting has begun.");
		    	}
		    	else
		    	{
		    		counting.put(player, false);
		    		log.info(ticks.toString());
		    		player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Your time was: " + ChatColor.RED + ticks.get(player) / 20 + ChatColor.WHITE + " seconds.");
		    	}
		    }
		}
	}

	public static void ticks()
	{
		playersOnline = (Bukkit.getOnlinePlayers());
		for(Player p : playersOnline)
		{
			if(counting.get(p))
			{
				ticks.put(p, ticks.get(p) + 1);
			}
		}
	}
}