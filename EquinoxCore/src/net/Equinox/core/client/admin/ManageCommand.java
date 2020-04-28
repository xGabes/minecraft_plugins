package net.Equinox.core.client.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;

public class ManageCommand implements CommandExecutor
{

	private Core _core;
	
	public ManageCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("manage").setExecutor(this);
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
			
			_core.serverMenu.openMain(player);
		}
		return false;
	}

	
}
