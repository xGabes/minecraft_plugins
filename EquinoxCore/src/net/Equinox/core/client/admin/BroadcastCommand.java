package net.Equinox.core.client.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class BroadcastCommand implements CommandExecutor
{
	
	private Core _core;
	
	public BroadcastCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("broadcast").setExecutor(this);
		
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
			
			if(args.length < 1)
			{
				UtilsMessages.inform("Command", "Correct use is " + ChatColor.GOLD + "/broadcast <message>", player);
				return false;
			}
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < args.length; i++)
			{
				builder.append(args[i]);
				if (i != args.length - 1)
				{
					builder.append(" ");
				}
			}
			UtilsMessages.broadcast(builder.toString(), player);
			
		} else
		{
			return false;
		}
		return false;
	}

}
