package net.Equinox.core.client.admin;

import java.lang.annotation.Target;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.client.punishment.PunishedClient;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class PunishCommand implements CommandExecutor
{
	
	private Core _core;
	
	public PunishCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("punish").setExecutor(this);
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
			
			if(args.length < 2)
			{
				UtilsMessages.inform("Punishment", "Correct usage: " + ChatColor.GOLD + "/punish <player> <reason>", player);
				return false;
			}
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if(target == null)
			{
				UtilsMessages.inform("Punishment", "Couldn't find player " + args[0], player);
				return false;
			}
			
			StringBuilder builder = new StringBuilder();
			
			PunishedClient punished = null;
			
			for (int i = 1; i < args.length; i++)
			{
				builder.append(args[i]);
				if (i != args.length - 1)
				{
					builder.append(" ");
				}
			}
			
			try 
			{
				punished = _core.punishmentData.load(target);
				
			} catch(Exception ex)
			{
				ex.printStackTrace();
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
			
			_core.punishmentMenu.openPunishments(player, target, builder.toString());
			
			
		} else
		{
			if(args.length < 3)
			{
				sender.sendMessage("Usage: /punish <type> <name> <reason>");
				return false;
			}
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if(target == null)
			{
				sender.sendMessage("Couldn't find player: " + target.getName());
				return false;
			}
			
			StringBuilder builder = new StringBuilder();
			for (int i = 2; i < args.length; i++)
			{
				builder.append(args[i]);
				if (i != args.length - 1)
				{
					builder.append(" ");
				}
			}
			
		}
		return false;
	}
}
