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
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Sign;

public class SignListener extends JavaPlugin implements Listener
{
	public static Player[] playersOnline;
	static Player player, playerLogin, playerDeath, playerPlacer;
	public static Map<Player, Boolean> counting = new HashMap<Player, Boolean>();
	public static Map<Player, Double> ticks = new HashMap<Player, Double>();
	public static Map<Player, String> TrackName = new HashMap<Player, String>();
	static Logger log = Logger.getLogger("Minecraft");
	
	@EventHandler
	public static void signPlace(SignChangeEvent event)
	{
		playerPlacer = event.getPlayer();
		String signTextLine1 = event.getLine(0);
		String signTextLine2 = event.getLine(1);
		String signTextLine3 = event.getLine(2);
		if(signTextLine1.equalsIgnoreCase("[TimeTrail]"))
		{
			if(TimeTrail.Permissions)
			{
				if(playerPlacer.hasPermission("TimeTrail.create") || playerPlacer.hasPermission("TimeTrail.*"))
				{
					if(!signTextLine2.isEmpty() && (signTextLine3.equalsIgnoreCase("Begin") || signTextLine3.equalsIgnoreCase("End")))
					{
						if(signTextLine3.equals("Begin"))
						{
							playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] " + ChatColor.RED + signTextLine2 + ChatColor.WHITE + " has been made, be sure to make an ending.");
						}
						else
						{
							playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] " + ChatColor.RED + signTextLine2 + ChatColor.WHITE + " has been made, be sure to have a beginning.");
						}
					}
					else
					{
						playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You have placed an invalid sign.");
					}
				}
				else
				{
					playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You do not have the permission to make a TimeTrail track.");
					event.setLine(0, "");
					event.setLine(1, "");
					event.setLine(2, "");
				}
			}
			else
			{
				if(playerPlacer.isOp())
				{
					if(!signTextLine2.isEmpty() && (signTextLine3.equalsIgnoreCase("Begin") || signTextLine3.equalsIgnoreCase("End")))
					{
						if(signTextLine3.equals("Begin"))
						{
							playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] " + ChatColor.RED + signTextLine2 + ChatColor.WHITE + " has been made, be sure to make an ending.");
						}
						else
						{
							playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] " + ChatColor.RED + signTextLine2 + ChatColor.WHITE + " has been made, be sure to have a beginning.");
						}
					}
					else
					{
						playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You have placed an invalid sign.");
					}
				}
				else
				{
					playerPlacer.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You can't make a TimeTrail track.");
					event.setLine(0, "");
					event.setLine(1, "");
					event.setLine(2, "");
				}
			}
		}
	}
	
	@EventHandler
	public static void playerLogin(PlayerLoginEvent event)
	{
		playerLogin = event.getPlayer();
		counting.put(playerLogin, false);
		ticks.put(playerLogin, 0.00);
		TrackName.put(playerLogin, null);
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
			TrackName.put(playerDeath, null);
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
			String signTextLine1 = s.getLine(0);
			String signTextLine2 = s.getLine(1);
			String signTextLine3 = s.getLine(2);
		    
			if(signTextLine1.equalsIgnoreCase("[TimeTrail]"))
		    {
				if(TimeTrail.Permissions)
				{
					if(player.hasPermission("TimeTrail.use") || player.hasPermission("TimeTrail.*"))
					{
						if(!signTextLine2.isEmpty() && (signTextLine3.equalsIgnoreCase("Begin") || signTextLine3.equalsIgnoreCase("End")))
						{
							if(TrackName.get(player) == null && signTextLine3.equalsIgnoreCase("Begin"))
							{
								TrackName.put(player, signTextLine2);
								ticks.put(player, 0.00);
								counting.put(player, true);
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Counting has begun.");
							}
							else if(signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("End"))
							{
								TrackName.put(player, null);
								counting.put(player, false);
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Your time was: " + ChatColor.RED + ticks.get(player) / 20 + ChatColor.WHITE + " seconds.");
							}
							else if(signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("Begin"))
							{
								TrackName.put(player, null);
							    counting.put(player, false);
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Counting has stopped.");
							}
							else if(TrackName.get(player) == null && signTextLine3.equalsIgnoreCase("End"))
							{
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You haven't started counting.");
							}
							else if(!signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("Begin"))
							{
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You're already doing track: " + ChatColor.RED + TrackName.get(player));
							}
							else if(!signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("End"))
							{
								player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] This is the wrong ending.");
							}
						}
						else
						{
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Invalid sign.");
						}
					}
					else
					{
						player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You do not have the permission to use a TimeTrail track.");
					}
				}
				else
				{
					if(!signTextLine2.isEmpty() && (signTextLine3.equalsIgnoreCase("Begin") || signTextLine3.equalsIgnoreCase("End")))
					{
						if(TrackName.get(player) == null && signTextLine3.equalsIgnoreCase("Begin"))
						{
							TrackName.put(player, signTextLine2);
							ticks.put(player, 0.00);
							counting.put(player, true);
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Counting has begun.");
						}
						else if(signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("End"))
						{
							TrackName.put(player, null);
							counting.put(player, false);
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Your time was: " + ChatColor.RED + ticks.get(player) / 20 + ChatColor.WHITE + " seconds.");
						}
						else if(signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("Begin"))
						{
							TrackName.put(player, null);
						    counting.put(player, false);
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Counting has stopped.");
						}
						else if(TrackName.get(player) == null && signTextLine3.equalsIgnoreCase("End"))
						{
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You haven't started counting.");
						}
						else if(!signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("Begin"))
						{
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] You're already doing track: " + ChatColor.RED + TrackName.get(player));
						}
						else if(!signTextLine2.equalsIgnoreCase(TrackName.get(player)) && signTextLine3.equalsIgnoreCase("End"))
						{
							player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] This is the wrong ending.");
						}
					}
					else
					{
						player.sendMessage("[" + ChatColor.RED + "TIMETRAIL" + ChatColor.WHITE + "] Invalid sign.");
					}
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