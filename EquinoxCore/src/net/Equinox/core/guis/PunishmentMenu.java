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
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class PunishmentMenu implements Listener
{

	private Core _core;
	private Inventory _inv;
	private Player _punisher;
	private OfflinePlayer _toPunish;
	private String _reason;
	private ItemStack _mute = new ItemBuilder(Material.BOOK)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Mute / Unmute")
			.lore(false,
					ChatColor.GRAY + "Mute player permanently.",
					ChatColor.GRAY + "Unmute player.")
			.build();
	private ItemStack _ban = new ItemBuilder(Material.REDSTONE_BLOCK)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Ban")
			.lore(false,
					ChatColor.GRAY + "Ban player permanently.",
					ChatColor.GRAY + "Bans are permanent. User can't be unbanned.")
			.build();
	private ItemStack _kick = new ItemBuilder(Material.DIAMOND_BOOTS)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Kick")
			.lore(false,
					ChatColor.GRAY + "Kick player from the server!",
					ChatColor.GRAY + "Used as warning.")
			.build();
	
	public PunishmentMenu(Core plugin)
	{
		_core = plugin;
		_inv = Bukkit.createInventory(null, 36, "Punishments");
	}
	
	public void openPunishments(Player punisher, OfflinePlayer toPunish, String reason)
	{
		_punisher = punisher;
		_toPunish = toPunish;
		_reason = reason;
		ItemStack skull = new ItemBuilder(Material.SKULL_ITEM)
				.durability(SkullType.PLAYER.ordinal())
				.setOwner(toPunish)
				.name(ChatColor.GREEN.toString() + ChatColor.BOLD + toPunish.getName())
				.lore(false,
						ChatColor.GRAY + "User's current punishment: ",
						" ",
						ChatColor.WHITE + "Muted: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isMuted() ? "True" : "False"),
						ChatColor.WHITE + "Reason: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isMuted() ? (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).getMutedReason()) : "n/a"),
						ChatColor.WHITE + "Muted by: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isMuted() ? (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).getPunisher()) : "n/a"),
						" ",
						ChatColor.WHITE + "Banned: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isBanned() ? "True" : "False"),
						ChatColor.WHITE + "Reason: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isBanned() ? (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).getBannedReason()) : "n/a"),
						ChatColor.WHITE + "Banned by: " + ChatColor.GRAY + (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).isBanned() ? (_core.punishedManager.getPunishedClient(toPunish.getUniqueId()).getPunisher()) : "n/a"))
				.build();
		
		_inv.setItem(4, skull);
		_inv.setItem(20, _mute);
		_inv.setItem(22, _ban);
		_inv.setItem(24, _kick);
		
		punisher.openInventory(_inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() == null)
		{
			return;
		}
		
		if(e.getInventory().equals(_inv))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_mute))
			{
				_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).setMuted(!_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isMuted());
				_core.punishedManager.setMutedReason(_toPunish.getUniqueId(), _reason);
				_core.punishedManager.setPunisher(_toPunish.getUniqueId(), _punisher.getName());
				UtilsMessages.inform("Punishment", "Player " + ChatColor.GREEN + _toPunish.getName() + ChatColor.GRAY + " has been "
						+ ChatColor.WHITE + (_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isMuted() ? "muted" : "unmuted")
						+ ChatColor.GRAY + " for "  + ChatColor.WHITE + _reason, _punisher);
				_core.punishmentData.upload(_toPunish);
				if(!_toPunish.isOnline())
				{
					_punisher.closeInventory();
					return;
				}
				UtilsMessages.inform("Punishment", "Staff " + ChatColor.RED + _punisher.getName() + ChatColor.GRAY + " has "
						+ ChatColor.WHITE + (_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isMuted() ? "muted" : "unmuted")
						+ ChatColor.GRAY + " you for "  + ChatColor.WHITE + _reason, ((Player) _toPunish));
				UtilsMessages.informAll("Punishment", ChatColor.GREEN + _toPunish.getName() + ChatColor.GRAY + " has been " +
						(_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isMuted() ? "muted" : "unmuted")  + " by staff " + ChatColor.RED + _punisher.getName());
				_punisher.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_ban))
			{
				_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).setBanned(!_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isBanned());
				_core.punishedManager.setBannedReason(_toPunish.getUniqueId(), _reason);
				_core.punishedManager.setPunisher(_toPunish.getUniqueId(), _punisher.getName());
				UtilsMessages.inform("Punishment", "Player " + ChatColor.GREEN + _toPunish.getName() + ChatColor.GRAY + " has been "
						+ ChatColor.WHITE + (_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isBanned() ? "banned" : "pardoned")
						+ ChatColor.GRAY + " for "  + ChatColor.WHITE + _reason, _punisher);
				_core.punishmentData.upload(_toPunish);
				if(_toPunish.isOnline())
				{
					((Player) _toPunish).kickPlayer(ChatColor.RED + "[Banned]" 
							+ ChatColor.GRAY + "\nYou have been banned by " + ChatColor.RED + _punisher.getName()
							+ ChatColor.GRAY + "\nReason: " + ChatColor.YELLOW + _reason);
					UtilsMessages.informAll("Punishment", ChatColor.GREEN + _toPunish.getName() + ChatColor.GRAY + " has been " +
							(_core.punishedManager.getPunishedClient(_toPunish.getUniqueId()).isBanned() ? "banned" : "pardoned")  + " by staff " + ChatColor.RED + _punisher.getName());
				}
				_punisher.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_kick))
			{
				if(!_toPunish.isOnline())
				{
					UtilsMessages.inform("Punishment", "You can't kick someone who's not online!", _punisher);
					_punisher.closeInventory();
					return;
				}
				UtilsMessages.inform("Punishment", "Player " + ChatColor.GREEN + _toPunish.getName() + ChatColor.GRAY + " has been "
						+ ChatColor.WHITE + "kicked"
						+ ChatColor.GRAY + " for "  + ChatColor.WHITE + _reason, _punisher);
				_punisher.closeInventory();
				((Player) _toPunish).kickPlayer(ChatColor.RED + "[Punishment]" 
						+ ChatColor.GRAY + "\nYou have been kicked by " + ChatColor.RED + _punisher.getName()
						+ ChatColor.GRAY + "\nReason: " + ChatColor.YELLOW + _reason);
				return;
			}
			
		}
		
	}
}
