package me.Leblanct.supercreative.commands;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.utils.UtilMessages;
import net.md_5.bungee.api.ChatColor;

public class WorldCommand implements CommandExecutor
{
	
	private SuperCreative _plugin;
	private List<String> _confirmation;
	
	public WorldCommand(SuperCreative plugin)
	{
		_plugin = plugin;
		_plugin.getCommand("world").setExecutor(this);
		_confirmation = new ArrayList<String>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			System.out.print("Only players can use this command.");
			return false;
		}
		
		Player p = (Player) sender;
		
		if(args.length < 1)
		{
			UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world <args> [args]", p);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("create"))
		{
			if(args.length < 2 || args.length > 2)
			{
				UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world create <type>", p);
				UtilMessages.inform("World", "World types: ", p);
				p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Normal");
				p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Flat");
				p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Empty");
				return false;
				
			}
			
			if(args[1].equalsIgnoreCase("normal"))
			{
				UtilMessages.warn("World", "Currently not available.", p);
				return true;
			}
			
			if(args[1].equalsIgnoreCase("empty"))
			{
				if(_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getName() != null)
				{
					UtilMessages.warn("World", "You already own a world! Use " + ChatColor.GOLD + "/world home", p);
					return false;
				}
				
				_plugin.getGenerator().createEmptyWorld(p);
				return true;
			}
			
			if(args[1].equalsIgnoreCase("flat"))
			{
				UtilMessages.warn("World", "Currently not available.", p);
				return true;
			}
			
			UtilMessages.inform("World", "World types: ", p);
			p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Normal");
			p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Flat");
			p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + "Empty");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("home"))
		{
			if(_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getName() == null)
			{
				UtilMessages.warn("World", "Couldn't teleport you! You have no world!", p);
				return false;
			}
			
			Location loc = _plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getSpawn();
			p.teleport(loc);
			
			UtilMessages.inform("World", "You've been teleported to your home world", p);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("add"))
		{
			if(args.length < 2 || args.length > 2)
			{
				UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world add <player>", p);
				return false;
			}
			
			OfflinePlayer toMan = (OfflinePlayer) Bukkit.getOfflinePlayer(args[1]);
			
			if(!toMan.hasPlayedBefore())
			{
				UtilMessages.warn("Player Finder", "Couldn't find player " + args[1], p);
				return false;
			}
			
			if(_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getAllowed().contains(toMan.getUniqueId()))
			{
				UtilMessages.warn("World", "Player " + args[1] + " already has access to your world", p);
				return false;
			}
			
			_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().giveAccess(toMan.getUniqueId());
			
			
			UtilMessages.inform("World", "You've given " + ChatColor.AQUA + toMan.getName() + ChatColor.WHITE + " access to your world", p);
			
			if(!toMan.isOnline()) return false;
			
			UtilMessages.inform("World", ChatColor.AQUA + p.getName() + ChatColor.RESET + " has given you access to build in their world", toMan.getPlayer());
			return true;
		}
		
		if(args[0].equalsIgnoreCase("remove"))
		{
			if(args.length < 2 || args.length > 2)
			{
				UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world remove <player>", p);
				return false;
			}
			
			OfflinePlayer toMan = (OfflinePlayer) Bukkit.getOfflinePlayer(args[1]);
			
			if(!toMan.hasPlayedBefore())
			{
				UtilMessages.warn("Player Finder", "Couldn't find player " + args[1], p);
				return false;
			}
			
			if(!_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getAllowed().contains(toMan.getUniqueId()))
			{
				UtilMessages.warn("World", "Player " + args[1] + " was never allowed access", p);
				return false;
			}
			
			_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().removeAccess(toMan.getUniqueId());
			
			UtilMessages.inform("World", "You've removed " + ChatColor.AQUA + toMan.getName() + ChatColor.WHITE + "'s access to your world", p);
			
			if(!toMan.isOnline()) return false;
			
			UtilMessages.inform("World", ChatColor.AQUA + p.getName() + ChatColor.RESET + " has removed your access to their world", toMan.getPlayer());
			return true;
		}
		
		if(args[0].equalsIgnoreCase("list"))
		{
			if(_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getAllowed().isEmpty())
			{
				UtilMessages.warn("World", "You haven't granted anyone access to your world", p);
				return false;
			}
			
			UtilMessages.inform("World", "List of users with access:", p);
			for(UUID id : _plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getAllowed())
			{
				p.sendMessage(ChatColor.YELLOW + "> " + ChatColor.WHITE + Bukkit.getOfflinePlayer(id).getName());
			}
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("reset"))
		{
			if(_plugin.getClientManager().getClient(p.getUniqueId()).getWorld().getName() == null)
			{
				UtilMessages.warn("World", "You do not own a world you can reset.", p);
				return false;
			}
			
			if(!_confirmation.contains(p.getName().toString()))
			{
				_confirmation.add(p.getName().toString());
				UtilMessages.inform(" World", "You have requested a world reset!", p);
				p.sendMessage(ChatColor.RED + "================");
				p.sendMessage(ChatColor.RED + "You've requested a world reset.");
				p.sendMessage(ChatColor.RED + "This action is " + ChatColor.BOLD + " IRREVERSIBLE");
				p.sendMessage(ChatColor.RED + "Use the command" + ChatColor.GREEN + " /w reset" + ChatColor.RED + " again");
				p.sendMessage(ChatColor.RED + "in the next 3 seconds to start resetting your world.");
				p.sendMessage(ChatColor.RED + "================");
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new BukkitRunnable()
						{
					
					public void run()
					{
						_confirmation.remove(p.getName().toString());
					}
					
						}, 60L);
				
				return true;
			}
			
			
			World _world = Bukkit.getWorld(p.getUniqueId());
			
			if(_confirmation.contains(p.getName()))
			{
				for(Chunk c : _world.getLoadedChunks())
				{
					int cx = c.getX() << 4;
					int cz = c.getZ() << 4;
					for(int x = 0; x < cx + 16; x++)
					{
						for(int z = 0; z < cz + 16; z++)
						{
							for(int y = 0; y < 256; y++)
							{
								if(_world.getBlockAt(x, y, z).getType() != Material.AIR)
								{
									_world.getBlockAt(x,  y, z).setType(Material.AIR);
									
								} else
								{
									return false;
								}
							}
						}
					}
				}
				
				_world.getBlockAt(0, 100, 0).setType(Material.BEDROCK);
				
				UtilMessages.inform("World", "Your world has been reset successfully", p);
				_confirmation.remove(p.getName().toString());
				
				return true;
			}
			
		}
		
		
		UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world create", p);
		UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world home", p);
		UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world add", p);
		UtilMessages.warn("Command", "Correct usage is " + ChatColor.GOLD + "/world remove", p);
		
		return false;
	}

}
