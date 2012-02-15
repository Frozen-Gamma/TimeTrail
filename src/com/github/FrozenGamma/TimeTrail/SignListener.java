package com.github.FrozenGamma.TimeTrail;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener{
	
	Logger log = Logger.getLogger("Minecraft");
		
	@EventHandler
	public void signListener(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		if(action == Action.RIGHT_CLICK_BLOCK && (block.getType() == Material.SIGN || (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN))) 
		{
		    log.info("You right-clicked on a sign.");
		}
	}
}