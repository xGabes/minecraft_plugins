package net.Equinox.core.guis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.Equinox.core.Core;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class ServerMenu implements Listener
{
	
	private Core _core;
	private Inventory _inv;
	
	private ItemStack _whitelist;
	private ItemStack _doubleJ;
	private ItemStack _fly;
	private ItemStack _cosmetics;
	private ItemStack _chat;
	
	public ServerMenu(Core plugin)
	{
		_core = plugin;
		_inv = Bukkit.createInventory(null, 27, "Server Manager");
	}
	
	private Player _staff;
	
	/*
	 * Items
	 */
	
	public void openMain(Player p)
	{
		_staff = p;
		
		_doubleJ = new ItemBuilder(Material.LEASH)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Double jump")
				.lore(false, ChatColor.GRAY + (_core.serverManager.canDoubleJump() ? "Disable double jumping" : "Enable double jumping"))
				.build();
		
		_fly = new ItemBuilder(Material.FEATHER)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Fly")
				.lore(false, ChatColor.GRAY + (_core.serverManager.canFly() ? "Disable flying" : "Enable flying"))
				.build();
		
		_cosmetics = new ItemBuilder(Material.ENDER_CHEST)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Cosmetics")
				.lore(false, ChatColor.GRAY + (_core.serverManager.canCosmetics() ? "Disable cosmetics" : "Enable cosmetics"))
				.build();
		
		_chat = new ItemBuilder(Material.BOOK_AND_QUILL)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Chat Management")
				.lore(false, ChatColor.GRAY + "Open the chat manager")
				.build();
		
		_whitelist = new ItemStack(Material.PAPER);
		ItemMeta whitelistMeta = _whitelist.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + (Bukkit.getServer().hasWhitelist() ? "Disable whitelist" : "Enable whitelist"));
		lore.add("");
		lore.add(ChatColor.YELLOW + "Whitelisted players: ");
		
		whitelistMeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Whitelist");
		List<OfflinePlayer> name = new ArrayList<OfflinePlayer>(Bukkit.getServer().getWhitelistedPlayers());
		
		for(int i = 0; i < Bukkit.getServer().getWhitelistedPlayers().size(); i++)
		{
			lore.add(ChatColor.WHITE + name.get(i).getName());
		}
		
		whitelistMeta.setLore(lore);
		_whitelist.setItemMeta(whitelistMeta);
		
		
		_inv.setItem(9, _whitelist);
		_inv.setItem(11, _doubleJ);
		_inv.setItem(13, _fly);
		_inv.setItem(15, _cosmetics);
		_inv.setItem(17, _chat);
		p.openInventory(_inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() == null)
		{
			return;
		}
		
		Player player = (Player) e.getWhoClicked();
		
		if(e.getInventory().equals(_inv))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_whitelist))
			{
				Bukkit.getServer().setWhitelist(!Bukkit.getServer().hasWhitelist());
				UtilsMessages.informAll("Whitelist", "Whitelist has been " + ChatColor.WHITE + (Bukkit.getServer().hasWhitelist() ? "enabled " : "disabled ") + ChatColor.GRAY + "by " + ChatColor.RED + _staff.getName());
				player.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_doubleJ))
			{
				_core.serverManager.setCanDoubleJump(!_core.serverManager.canDoubleJump());
				UtilsMessages.informAll("Double Jump", "Double jump has been " + ChatColor.WHITE + (_core.serverManager.canDoubleJump() ? "enabled " : "disabled ") + ChatColor.GRAY + "by " + ChatColor.RED + _staff.getName());
				player.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_fly))
			{
				_core.serverManager.setCanFly(!_core.serverManager.canFly());
				UtilsMessages.informAll("Flight", "Flight has been " + ChatColor.WHITE + (_core.serverManager.canFly() ? "enabled " : "disabled ") + ChatColor.GRAY + "by " + ChatColor.RED + _staff.getName());
				player.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_cosmetics))
			{
				_core.serverManager.setCanCosmetics(!_core.serverManager.canCosmetics());
				UtilsMessages.informAll("Cosmetics", "Cosmetics have been " + ChatColor.WHITE + (_core.serverManager.canCosmetics() ? "enabled " : "disabled ") + ChatColor.GRAY + "by " + ChatColor.RED + _staff.getName());
				player.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_chat))
			{
				_core.chatMenu.openMain(player);
				return;
			}
			return;
		}
	}
}
