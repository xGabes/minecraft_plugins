package net.Equinox.core.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.client.infinitum.InfinitumIcon;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class ClientMenu implements Listener
{

	private Core _core;
	private Inventory _invRanks;
	private Player _staff;
	private OfflinePlayer _client;
	private ItemStack _member = new ItemBuilder(Material.WOOL)
			.durability(8)
			.name(Rank.MEMBER.getTag(true, true))
			.lore(false,
					ChatColor.GRAY + "Set player's rank to Member")
			.build();
	private ItemStack _vip = new ItemBuilder(Material.WOOL)
			.durability(3)
			.name(Rank.VIP.getTag(true, true))
			.lore(false,
					ChatColor.GRAY + "Set player's rank to VIP")
			.build();
	private ItemStack _pro = new ItemBuilder(Material.WOOL)
			.durability(11)
			.name(Rank.PRO.getTag(true, true))
			.lore(false,
					ChatColor.GRAY + "Set player's rank to PRO")
			.build();
	private ItemStack _admin = new ItemBuilder(Material.WOOL)
			.durability(14)
			.name(Rank.ADMIN.getTag(true, true))
			.lore(false,
					ChatColor.GRAY + "Set player's rank to Admin")
			.build();
	private ItemStack _infinitum = new ItemBuilder(Material.WOOL)
			.durability(10)
			.name(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Infinitum")
			.lore(false,
					ChatColor.GRAY + "Set player's Infinitum access")
			.build();
	
	public ClientMenu(Core plugin)
	{
		_core = plugin;
		_invRanks = Bukkit.createInventory(null, 45, "Ranks");
	}
	
	public void openRanks(Player staff, OfflinePlayer target)
	{
		_staff = staff;
		_client = target;
		ItemStack skull = new ItemBuilder(Material.SKULL_ITEM)
				.durability(SkullType.PLAYER.ordinal())
				.setOwner(target)
				.name(ChatColor.GREEN.toString() + ChatColor.BOLD + target.getName())
				.lore(false,
						ChatColor.GRAY + "User's current rank: ",
						_core.clientManager.getClient(target.getUniqueId()).getRank().getTag(true, true).toUpperCase(),
						" ",
						ChatColor.GRAY + "Infinitum: " + (_core.clientManager.getClient(target.getUniqueId()).isInfinitum() ? "True" : "False"))
				.build();
		
		_invRanks.setItem(4, skull);
		_invRanks.setItem(19, _member);
		_invRanks.setItem(21, _vip);
		_invRanks.setItem(23, _pro);
		_invRanks.setItem(25, _admin);
		_invRanks.setItem(40, _infinitum);
		
		staff.openInventory(_invRanks);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() == null)
		{
			return;
		}
		
		if(e.getInventory().equals(_invRanks))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_member))
			{
				_core.clientManager.getClient(_client.getUniqueId()).setRank(Rank.MEMBER);
				UtilsMessages.inform("Rank", "You've set " + ChatColor.GREEN + _client.getName()  + ChatColor.GRAY + "'s rank to " + Rank.MEMBER.getTag(true, true), _staff);
				_core.playerData.upload(_client);
				if(_client.isOnline())
				{
					UtilsMessages.inform("Rank", "Staff " + ChatColor.RED + _staff.getName()   + ChatColor.GRAY + " has set your rank to " + Rank.MEMBER.getTag(true, true), ((Player) _client));
				}
				
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_vip))
			{
				_core.clientManager.getClient(_client.getUniqueId()).setRank(Rank.VIP);
				UtilsMessages.inform("Rank", "You've set " + ChatColor.GREEN + _client.getName()  + ChatColor.GRAY + "'s rank to " + Rank.VIP.getTag(true, true), _staff);
				_core.playerData.upload(_client);
				if(_client.isOnline())
				{
					UtilsMessages.inform("Rank", "Staff " + ChatColor.RED + _staff.getName()   + ChatColor.GRAY + " has set your rank to " + Rank.VIP.getTag(true, true), ((Player) _client));
				}
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_pro))
			{
				_core.clientManager.getClient(_client.getUniqueId()).setRank(Rank.PRO);
				UtilsMessages.inform("Rank", "You've set " + ChatColor.GREEN + _client.getName()  + ChatColor.GRAY + "'s rank to " + Rank.PRO.getTag(true, true), _staff);
				_core.playerData.upload(_client);
				if(_client.isOnline())
				{
					UtilsMessages.inform("Rank", "Staff " + ChatColor.RED + _staff.getName()   + ChatColor.GRAY + " has set your rank to " + Rank.PRO.getTag(true, true), ((Player) _client));
				}
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_admin))
			{
				_core.clientManager.getClient(_client.getUniqueId()).setRank(Rank.ADMIN);
				UtilsMessages.inform("Rank", "You've set " + ChatColor.GREEN + _client.getName()  + ChatColor.GRAY + "'s rank to " + Rank.ADMIN.getTag(true, true), _staff);
				_core.playerData.upload(_client);
				if(_client.isOnline())
				{
					UtilsMessages.inform("Rank", "Staff " + ChatColor.RED + _staff.getName()   + ChatColor.GRAY + " has set your rank to " + Rank.ADMIN.getTag(true, true), ((Player) _client));
				}
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_infinitum))
			{
				_core.clientManager.getClient(_client.getUniqueId()).setInfinitum(!_core.clientManager.getClient(_client.getUniqueId()).isInfinitum());
				UtilsMessages.inform("Rank", "You've set " + ChatColor.GREEN + _client.getName()  + ChatColor.GRAY + "'s Infinitum access to "
						+ (_core.clientManager.getClient(_client.getUniqueId()).isInfinitum() ? "true" : "false"), _staff);
				
				if(_core.clientManager.getClient(_client.getUniqueId()).isInfinitum())
				{
					_core.clientManager.getClient(_client.getUniqueId()).setIcon(InfinitumIcon.STAR);
				}
				
				if(_client.isOnline())
				{
					UtilsMessages.inform("Rank", "Staff " + ChatColor.RED + _staff.getName()  + ChatColor.GRAY + " has set your Infinitum access to "+ (_core.clientManager.getClient(_client.getUniqueId()).isInfinitum() ? "true" : "false"), ((Player) _client));
				}
				
				_core.playerData.upload(_client);
				_staff.closeInventory();
				return;
			}
		}
	}
}
