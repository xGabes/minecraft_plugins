package net.Equinox.core.client.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class AnnounceCommand implements CommandExecutor
{
	
	private Core _core;
	
	public  AnnounceCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("announce").setExecutor(this);
		
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
				UtilsMessages.inform("Command", "Correct use is " + ChatColor.GOLD + "/announce <message>", player);
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
			
			UtilsMessages.announce(builder.toString());
			for(Player p : Bukkit.getOnlinePlayers())
			{
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
			}
			
		} else
		{
			return false;
		}
		return false;
	}
}
