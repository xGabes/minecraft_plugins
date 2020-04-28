package net.Equinox.core.client.pro;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class FlyCommand implements CommandExecutor
{
	private Core _core;
	
	public FlyCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("fly").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			if(!_core.serverManager.canFly())
			{
				UtilsMessages.inform("Fly", "Flying is currently disabled!", ((Player) sender).getPlayer());
				return false;
			}
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(player, _core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.PRO, true)) 
			{
				return false;
			}
			
			_core.serverManager.setClientFly(player.getUniqueId(), !_core.serverManager.canClientFly(player.getUniqueId()));
			player.setAllowFlight(!player.getAllowFlight());
			UtilsMessages.inform("Fly", "Fly mode has been changed!" /*+ (player.getAllowFlight() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled")*/, player);
		}
		return false;
	}
}
