package me.Leblanct.supercreative.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.client.Client;
import me.Leblanct.supercreative.client.Rank;
import me.Leblanct.supercreative.utils.UtilMessages;
import me.Leblanct.supercreative.world.CreativeWorld;
import net.md_5.bungee.api.ChatColor;

public class Global implements Listener
{
	
	private SuperCreative _plugin;
	
	public Global(SuperCreative plugin)
	{
		_plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		Client client = _plugin.getClientManager().getClient(p.getUniqueId());
		if(client.getRank().equals(Rank.MEMBER))
		{
			e.setFormat(client.getRank().getFullTag() + " %s: " + ChatColor.GRAY + "%s");
		} else
		{
			e.setFormat(client.getRank().getFullTag() + " %s: " + ChatColor.WHITE + "%s");
		}
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		try
		{
			Client client = _plugin.getClientBase().load(e.getUniqueId());
			_plugin.getClientManager().addClient(client);
			_plugin.getWorldManager().addWorld(client.getWorld());
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		Client c = _plugin.getClientManager().getClient(p.getUniqueId());
		
		Location loc = Bukkit.getWorld("world").getSpawnLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		
		p.teleport(loc);
		
		if(c.getRank().equals(Rank.MEMBER))
		{
			e.setJoinMessage(ChatColor.GRAY + ">" + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + e.getPlayer().getName() + " wants to build!");
		} else if(_plugin.getClientManager().hasRank(c, Rank.MOD))
		{
			
			e.setJoinMessage(ChatColor.YELLOW + ">" + ChatColor.GOLD + "> " + ChatColor.YELLOW + "Staff "  + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " wants to build!" );
			
		} else
		{
			e.setJoinMessage(ChatColor.GREEN + ">" + ChatColor.AQUA + "> " + ChatColor.WHITE + p.getName() + " wants to build!");
		}
		
		if(c.getWorld().getName() != null)
		{
			if(Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(c.getId().toString())))
			{
				return;
			}
			
			WorldCreator world = new WorldCreator(c.getId().toString());
			world.createWorld();
		}
		
		if(c.getWorld().getName() == null)
		{
			p.sendMessage("");
			p.sendMessage(ChatColor.YELLOW + ">" + ChatColor.GOLD + ">" + ChatColor.WHITE + "------------------------------" + ChatColor.GOLD + "<" + ChatColor.YELLOW + "<");
			p.sendMessage(ChatColor.WHITE + "Welcome, " + ChatColor.GREEN + p.getName());
			p.sendMessage(ChatColor.WHITE + "It seems that you don't own a world");
			p.sendMessage(ChatColor.WHITE + "You can create one using " + ChatColor.GOLD + "/world");
			p.sendMessage(ChatColor.WHITE + "Have fun and build amazing things!");
			p.sendMessage("");
			
		} else
		{
			p.sendMessage("");
			p.sendMessage(ChatColor.YELLOW + ">" + ChatColor.GOLD + ">" + ChatColor.WHITE + "------------------------------" + ChatColor.GOLD + "<" + ChatColor.YELLOW + "<");
			p.sendMessage(ChatColor.WHITE + "Welcome back, " + ChatColor.GREEN + p.getName());
			p.sendMessage(ChatColor.WHITE + "Your world has been loaded!");
			p.sendMessage(ChatColor.WHITE + "You can get to your world using " + ChatColor.GOLD + "/world home");
			p.sendMessage(ChatColor.WHITE + "Have fun and build amazing things!");
			p.sendMessage("");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		e.setQuitMessage(null);
		try
		{
			_plugin.getClientBase().upload(e.getPlayer().getUniqueId());
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
