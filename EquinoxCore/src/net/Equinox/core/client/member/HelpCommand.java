package net.Equinox.core.client.member;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.PriorityQueue;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class HelpCommand implements CommandExecutor
{

	private Core _core;
	private List<String> _alias;
	private List<String> _commands;
	
	public HelpCommand(Core plugin)
	{
		_core = plugin;
		_alias = new ArrayList<String>();
		_alias.add("h");
		plugin.getCommand("help").setAliases(_alias);
		plugin.getCommand("help").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player  = (Player) sender;
			_commands = new ArrayList<String>();
			UtilsMessages.inform("Help", "Available commands:", player);
			getCommands(player);
			for(int i = 0; i < _commands.size(); i++)
			{
				player.sendMessage(_commands.get(i));
			}
			_commands.clear();
		} else
		{
			return false;
		}
		return false;
	}
	
	public void getCommands(Player player)
	{
		if(player.isOp())
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /firework " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /shout " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /fly " + Rank.PRO.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /rank " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /chat " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /broadcast " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /announce " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /punish " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /upload " + Rank.OWNER.getTag(true, true));
			return;
		}
		if(_core.clientManager.getClient(player.getUniqueId()).isInfinitum())
		{
			_commands.add(ChatColor.WHITE + "- /icon " + ChatColor.BOLD.toString() + ChatColor.DARK_PURPLE + "Infinitum");
			_commands.add(ChatColor.WHITE + "- /color " + ChatColor.BOLD.toString() + ChatColor.DARK_PURPLE + "Infinitum");
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.MEMBER))
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.VIP))
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /firework " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /shout " + Rank.VIP.getTag(true, true));
			return;
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.PRO))
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /firework " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /shout " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /fly " + Rank.PRO.getTag(true, true));
			return;
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.ADMIN))
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /firework " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /shout " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /fly " + Rank.PRO.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /rank " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /chat " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /broadcast " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /announce " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /punish " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /manage " + Rank.ADMIN.getTag(true, true));
			return;
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().isRank(Rank.OWNER))
		{
			_commands.add(ChatColor.WHITE + "- /help " + Rank.MEMBER.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /firework " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /shout " + Rank.VIP.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /fly " + Rank.PRO.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /rank " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /chat " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /broadcast " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /announce " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /punish " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /manage " + Rank.ADMIN.getTag(true, true));
			_commands.add(ChatColor.WHITE + "- /upload " + Rank.OWNER.getTag(true, true));
			return;
		}
	}

}
