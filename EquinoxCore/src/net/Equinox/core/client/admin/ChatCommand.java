package net.Equinox.core.client.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;

public class ChatCommand implements CommandExecutor
{

	private Core _core;
	
	public ChatCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("chat").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(player, _core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN, true))
			{
				return false;
			}
			
			_core.chatMenu.openMain(player);
		}
		return false;
	}

	
}
