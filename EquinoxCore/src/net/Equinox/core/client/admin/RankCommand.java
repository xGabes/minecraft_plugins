package net.Equinox.core.client.admin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import net.Equinox.core.Core;
import net.Equinox.core.client.Client;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class RankCommand implements CommandExecutor
{
	
	private Core _core;
	
	public RankCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("rank").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(player, 
					_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN, true))
			{
				return false;
			}
			if(args.length > 1)
			{
				UtilsMessages.inform("Rank", "Correct usage: " + ChatColor.GOLD + "/rank <player>", player);
				return false;
			}
			if(args.length < 1)
			{
				UtilsMessages.inform("Rank", "Correct usage: " + ChatColor.GOLD + "/rank <player>", player);
				return false;
			}
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if(target == null)
			{
				UtilsMessages.inform("Punishment", "Couldn't find player " + args[0], player);
				return false;
			}
			
			Client client = null;
			
			if (!target.isOnline()) 
			{
				try 
				{
					client = _core.playerData.load(target.getUniqueId());
					
				} catch(Exception ex)
				{
					ex.printStackTrace();
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
			
			
			
			_core.clientMenu.openRanks(player, target);
			
			
		} else
		{
			if(args.length < 2)
			{
				sender.sendMessage("Usage: /rank <name> <rank>");
				return false;
			}
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if(target == null)
			{
				sender.sendMessage("Couldn't find player: " + target.getName());
				return false;
			}
			
			Client client = null;
			
			if (!target.isOnline()) 
			{
				try 
				{
					client = _core.playerData.load(target.getUniqueId());
					
				} catch(Exception ex)
				{
					ex.printStackTrace();
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
			
			_core.clientManager.getClient(target.getUniqueId()).setRank(Rank.valueOf(args[1].toUpperCase()));
			_core.playerData.upload(target);
			System.out.println("Rank " + args[1] + " given to " + target.getName());
			if(target.isOnline())
			{
				Player player = (Player) target;
				UtilsMessages.inform("Rank", "Your has been updated to " + Rank.valueOf(args[1]).getTag(true, true), player);
			}
			
		}
		return false;
	}
}
