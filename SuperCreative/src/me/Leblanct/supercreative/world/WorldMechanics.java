package me.Leblanct.supercreative.world;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.client.Client;
import me.Leblanct.supercreative.client.Rank;

public class WorldMechanics implements Listener
{
	
	private SuperCreative _plugin;
	
	public WorldMechanics(SuperCreative plugin)
	{
		_plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		Client c = _plugin.getClientManager().getClient(p.getUniqueId());
		CreativeWorld w = _plugin.getWorldManager().getWorld(c.getWorld().getOwner().toString());
		
		
		if(_plugin.getClientManager().hasRank(c, Rank.ADMIN))
		{
			return;
		}
		
		if(e.getBlock().getType().equals(Material.BEDROCK)) 
		{
			e.setCancelled(true);
			return;
		}
		
		if(p.getWorld().equals(Bukkit.getWorld("world")))
		{
			e.setCancelled(true);
			return;
		}
		
		if(!p.getWorld().equals(Bukkit.getWorld(p.getUniqueId() + "")))
		{
			CreativeWorld loc = _plugin.getWorldManager().getWorld(p.getWorld().getName());
			if(loc == null)
			{
				return;
			}
			
			if(!loc.getAllowed().contains(c.getId()))
			{
				e.setCancelled(true);
				return;
			}
			
		} else return;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		Client c = _plugin.getClientManager().getClient(p.getUniqueId());
		CreativeWorld w = _plugin.getWorldManager().getWorld(c.getWorld().getOwner().toString());
		
		if(_plugin.getClientManager().hasRank(c, Rank.ADMIN))
		{
			return;
		}
		
		if(e.getBlock().getType().equals(Material.BEDROCK)) 
		{
			e.setCancelled(true);
			return;
		}
		
		if(p.getWorld().equals(Bukkit.getWorld("world")))
		{
			e.setCancelled(true);
			return;
		}
		
		if(!p.getWorld().equals(Bukkit.getWorld(p.getUniqueId() + "")))
		{
			CreativeWorld loc = _plugin.getWorldManager().getWorld(p.getWorld().getName());
			if(loc == null)
			{
				return;
			}
			
			if(loc.getAllowed().contains(c.getId()))
			{
				return;
			} else
			{
				e.setCancelled(true);
				return;
			}
		}
		
		/*Player p = e.getPlayer();
		Client c = _plugin.getClientManager().getClient(p.getUniqueId());
		CreativeWorld w = c.getWorld();
		
		if(_plugin.getClientManager().hasRank(c, Rank.ADMIN))
		{
			return;
		}
		
		if(p.getWorld().equals(Bukkit.getWorld("world")))
		{
			e.setCancelled(true);
			return;
		}
		
		CreativeWorld loc = _plugin.getWorldManager().getWorld(p.getLocation().getWorld().getName());
		
		if(loc == null || loc.getName() == "")
		{
			Bukkit.broadcastMessage("Check #1");
			return;
		}
		
		if(loc != w)
		{
			if(!loc.getAllowed().contains(p.getUniqueId()))
			{
				e.setCancelled(true);
				return;
			}
		}*/
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	{
		e.setCancelled(true);
	}
	
	
}
