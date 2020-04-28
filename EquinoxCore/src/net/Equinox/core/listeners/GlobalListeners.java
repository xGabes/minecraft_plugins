package net.Equinox.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Equinox.core.Core;
import net.Equinox.core.client.Client;
import net.Equinox.core.client.Rank;
import net.Equinox.core.client.punishment.PunishedClient;
import net.Equinox.core.cosmetics.infinitum.InfinitumTrailManager;
import net.Equinox.core.cosmetics.infinitum.InfinitumTrailType;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.core.utils.particles.HelixEffect;
import net.Equinox.core.utils.particles.WingsEffect;
import net.md_5.bungee.api.ChatColor;

public class GlobalListeners implements Listener
{
	
	private Core _core;
	
	public GlobalListeners(Core core)
	{
		_core = core;
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)  {
        if(event.getMessage().equals("/?") || event.getMessage().contains("/bukkit:") || event.getMessage().equals("/pl") ||
        		event.getMessage().equals("/plugins"))
        {
        	   UtilsMessages.inform("Commands", "Invalid usage. Please use /help", event.getPlayer());
        	    event.setCancelled(true);
        }
    }

	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event)
	{
		Client client;
		PunishedClient punished;
		
		try 
		{
			punished = _core.punishmentData.load(event.getUniqueId());
			
		} catch(Exception ex)
		{
			ex.printStackTrace();
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "We couldn't load your punishment information, try again later.");
			return;
		}
		try {
			if(punished != null)
			{
				_core.punishedManager.addPunishedClient(punished);
				
			} else
			{
				System.out.println("Problem found while adding player to the Client Manager");
			}
		} catch(NullPointerException exp)
		{
			exp.printStackTrace();
		}

		if(_core.punishedManager.getPunishedClient(event.getUniqueId()).isBanned())
		{
			event.disallow(Result.KICK_OTHER,
					ChatColor.RED + "[Banned]" 
			+ ChatColor.GRAY + "\nYou have been banned by " + ChatColor.RED + _core.punishedManager.getPunishedClient(event.getUniqueId()).getPunisher()
			+ ChatColor.GRAY + "\nReason: " + ChatColor.YELLOW + _core.punishedManager.getPunishedClient(event.getUniqueId()).getBannedReason());
			return;
		}
		
		try 
		{
			client = _core.playerData.load(event.getUniqueId());
			
		} catch(Exception ex)
		{
			ex.printStackTrace();
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "We couldn't load your player information, try again later.");
			return;
		}
		try {
			if(client != null)
			{
				_core.clientManager.addClient(client);
				
			} else
			{
				System.out.println("Problem found while adding player to the Client Manager");
			}
		} catch(NullPointerException exp)
		{
			exp.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		if(_core.clientManager.getClient(player.getUniqueId()).getRank()
				.hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.VIP) && 
				event.getResult() == event.getResult().KICK_FULL)
		{
			event.allow();
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		player.teleport(Bukkit.getWorld("world").getSpawnLocation());
		Location loc = player.getLocation();
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.VIP))
		{
			for(int i = 0; i < 100; i++)
			{
				player.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 1, 0D, 0D, 0D, 0.5D);
			}
		}
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.PRO))
		{
			event.setJoinMessage(ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "» " + ChatColor.RESET + ""
		        + _core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase() + " "
		        + ChatColor.GRAY + player.getName()
		        + ChatColor.BLUE + " joined the server" 
		        + ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + " «");
			
			player.getLocation().getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 100, 0D, 0D, 0D, 0D);
			player.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 100, 0D, 0D, 0D, 0D);
			player.getWorld().strikeLightningEffect(player.getLocation());
		}
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.ADMIN) ||
				_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.OWNER))
		{
			event.setJoinMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "» " 
			        + _core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase() + " "
			        + ChatColor.GRAY + player.getName()
			        + ChatColor.RED + " joined the server" 
			        + ChatColor.DARK_RED.toString() + ChatColor.BOLD + " «");
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		_core.playerData.upload(event.getPlayer().getUniqueId());
		_core.punishmentData.upload(event.getPlayer().getUniqueId());
		_core.clientManager.removeClient(_core.clientManager.getClient(player.getUniqueId()));
		_core.punishedManager.removePunishedClient(_core.punishedManager.getPunishedClient(player.getUniqueId()));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		/*if(event.getPlayer().getItemInHand() == null)
		{
			return;
		}
		if(event.getAction().equals(Action.LEFT_CLICK_AIR))
		{
			if(event.getPlayer().getItemInHand().getType() == Material.DIAMOND_SWORD)
			{
				WingsEffect.drawParticles(event.getPlayer().getLocation(), Particle.VILLAGER_HAPPY);
			}
		}*/
	}
	
	@EventHandler
	public void onPlayerWalk(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		InfinitumTrailManager trails = _core.infinitumTrailManager;
		
		if(_core.clientManager.getClient(p.getUniqueId()).isInfinitum())
		{
			if(trails.isEnabled(p.getUniqueId(), InfinitumTrailType.RED_DUST))
			{
				Location loc = e.getPlayer().getLocation();
				double r = 0.5;
				double x = r*Math.cos(loc.getZ());
				double y = 2;
				double z = r*Math.sin(loc.getX());
				loc.add(x, y, z);
				loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				return;
			}
			
			if(trails.isEnabled(p.getUniqueId(), InfinitumTrailType.FLAME))
			{
				Location loc = e.getPlayer().getLocation();
				double r = 0.5;
				double x = r*Math.cos(loc.getZ());
				double y = 2;
				double z = r*Math.sin(loc.getX());
				loc.add(x, y, z);
				loc.getWorld().spawnParticle(Particle.FLAME, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				return;
			}
			
			if(trails.isEnabled(p.getUniqueId(), InfinitumTrailType.FIREWORK))
			{
				Location loc = e.getPlayer().getLocation();
				double r = 0.5;
				double x = r*Math.cos(loc.getZ());
				double y = 2;
				double z = r*Math.sin(loc.getX());
				loc.add(x, y, z);
				loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				return;
			}
			
			if(trails.isEnabled(p.getUniqueId(), InfinitumTrailType.WATER))
			{
				Location loc = e.getPlayer().getLocation();
				double r = 0.5;
				double x = r*Math.cos(loc.getZ());
				double y = 2;
				double z = r*Math.sin(loc.getX());
				loc.add(x, y, z);
				loc.getWorld().spawnParticle(Particle.DRIP_WATER, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				return;
			}
			
			if(trails.isEnabled(p.getUniqueId(), InfinitumTrailType.LAVA))
			{
				Location loc = e.getPlayer().getLocation();
				double r = 0.5;
				double x = r*Math.cos(loc.getZ());
				double y = 2;
				double z = r*Math.sin(loc.getX());
				loc.add(x, y, z);
				loc.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				return;
			}
		}
		/*Location loc = e.getPlayer().getLocation();
		double r = 0.5;
		double x = r*Math.cos(loc.getZ());
		double y = 2;
		double z = r*Math.sin(loc.getX());
		loc.add(x, y, z);
		loc.getWorld().spawnParticle(Particle.FLAME, loc, 2, 0D, 0D, 0D, 0D);
		loc.subtract(x, y, z);*/
	}
}
