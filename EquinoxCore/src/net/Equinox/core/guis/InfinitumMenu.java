package net.Equinox.core.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.Equinox.core.Core;
import net.Equinox.core.client.infinitum.InfinitumIcon;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class InfinitumMenu implements Listener
{

	private Core _core;
	private Inventory _invIcon;
	private Inventory _invColor;
	private Player _player;
	
	/*
	 * Color Items
	 */
	
	private ItemStack _white = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(0)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "White")
			.lore(false, ChatColor.GRAY + "Your name will look like this:",
					ChatColor.WHITE + "[X] " + ChatColor.GRAY + "RANK " + ChatColor.WHITE + "Your_Name" + ChatColor.GRAY + ": <messages>",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	private ItemStack _green = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(5)
			.name(ChatColor.GREEN.toString() + ChatColor.BOLD + "Green")
			.lore(false, ChatColor.GRAY + "Your name will look like this:",
					ChatColor.WHITE + "[X] " + ChatColor.GRAY + "RANK " + ChatColor.GREEN + "Your_Name" + ChatColor.GRAY + ": <messages>",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	private ItemStack _purple = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(10)
			.name(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Purple")
			.lore(false, ChatColor.GRAY + "Your name will look like this:",
					ChatColor.WHITE + "[X] " + ChatColor.GRAY + "RANK " + ChatColor.DARK_PURPLE + "Your_Name" + ChatColor.GRAY + ": <messages>",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	private ItemStack _gold = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(1)
			.name(ChatColor.GOLD.toString() + ChatColor.BOLD + "Gold")
			.lore(false, ChatColor.GRAY + "Your name will look like this:",
					ChatColor.WHITE + "[X] " + ChatColor.GRAY + "RANK " + ChatColor.GOLD + "Your_Name" + ChatColor.GRAY + ": <messages>",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _reset = new ItemBuilder(Material.BARRIER)
			.name(ChatColor.GOLD.toString() + ChatColor.GRAY + "Reset")
			.lore(false, ChatColor.GRAY + "Your name will no longer have a color",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	
	/*
	 * Icon Items
	 */
	
	private ItemStack _star = new ItemBuilder(Material.NETHER_STAR)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Star")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + InfinitumIcon.STAR.getChar() + "",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _compass = new ItemBuilder(Material.COMPASS)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Compass")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + InfinitumIcon.COMPASS.getChar() + "",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _heart = new ItemBuilder(Material.REDSTONE)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Heart")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + InfinitumIcon.HEART.getChar() + "",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _snowflake = new ItemBuilder(Material.SNOW_BALL)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Snowflake")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + InfinitumIcon.SNOWFLAKE.getChar() + "",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _music = new ItemBuilder(Material.NOTE_BLOCK)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Music")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + InfinitumIcon.MUSIC.getChar() + "",
					" ",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _recycle = new ItemBuilder(Material.COAL)
			.durability(1)
			.name(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Icon")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + "X",
					" ",
					ChatColor.YELLOW + "Coming soon!")
			.build();
	
	private ItemStack _battery = new ItemBuilder(Material.COAL)
			.durability(1)
			.name(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Icon")
			.lore(false, ChatColor.GRAY + "This icon looks like this: ",
					ChatColor.WHITE + "X",
					" ",
					ChatColor.YELLOW + "Coming soon!")
			.build();
	
	public InfinitumMenu(Core core)
	{
		_core = core;
		_invColor = Bukkit.createInventory(null, 36, "Infinitum Colors");
		_invIcon = Bukkit.createInventory(null, 36, "Infinitum Icons");
	}
	
	public void openColors(Player p)
	{
		_player = p;
		_invColor.setItem(10, _white);
		_invColor.setItem(12, _green);
		_invColor.setItem(14, _purple);
		_invColor.setItem(16, _gold);
		_invColor.setItem(31, _reset);
		
		p.openInventory(_invColor);
	}
	
	public void openIcons(Player p)
	{
		_player = p;
		
		_invIcon.setItem(11, _star);
		_invIcon.setItem(13, _compass);
		_invIcon.setItem(15, _heart);
		_invIcon.setItem(21, _snowflake);
		_invIcon.setItem(23, _music);
		_invIcon.setItem(29, _recycle);
		_invIcon.setItem(33, _battery);
		
		
		p.openInventory(_invIcon);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() == null)
		{
			return;
		}
		
		Player player = (Player) e.getWhoClicked();
		
		if(e.getInventory().equals(_invColor))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_white))
			{
				player.setDisplayName(ChatColor.RESET.toString() + ChatColor.WHITE + player.getName());
				player.closeInventory();
				UtilsMessages.inform("Color", "You've changed your name's color to " + ChatColor.WHITE + "white", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_green))
			{
				player.setDisplayName(ChatColor.RESET.toString() + ChatColor.GREEN + player.getName());
				player.closeInventory();
				UtilsMessages.inform("Color", "You've changed your name's color to " + ChatColor.GREEN + "green", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_purple))
			{
				player.setDisplayName(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + player.getName());
				player.closeInventory();
				UtilsMessages.inform("Color", "You've changed your name's color to " + ChatColor.DARK_PURPLE + "purple", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_gold))
			{
				player.setDisplayName(ChatColor.RESET.toString() + ChatColor.GOLD + player.getName());
				player.closeInventory();
				UtilsMessages.inform("Color", "You've changed your name's color to " + ChatColor.GOLD + "gold", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_reset))
			{
				player.setDisplayName(player.getName());
				player.closeInventory();
				UtilsMessages.inform("Color", "You've removed the color from your name", player);
				return;
			}
			
			return;
		}
		
		if(e.getInventory().equals(_invIcon))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_star))
			{
				_core.clientManager.getClient(player.getUniqueId()).setIcon(InfinitumIcon.STAR);
				player.closeInventory();
				UtilsMessages.inform("Icon", "You've set your icon to " + ChatColor.WHITE + "Star [" + InfinitumIcon.STAR.getChar() + "]", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_heart))
			{
				_core.clientManager.getClient(player.getUniqueId()).setIcon(InfinitumIcon.HEART);
				player.closeInventory();
				UtilsMessages.inform("Icon", "You've set your icon to " + ChatColor.WHITE + "Heart [" + InfinitumIcon.HEART.getChar() + "]", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_snowflake))
			{
				_core.clientManager.getClient(player.getUniqueId()).setIcon(InfinitumIcon.SNOWFLAKE);
				player.closeInventory();
				UtilsMessages.inform("Icon", "You've set your icon to " + ChatColor.WHITE + "Snowflake [" + InfinitumIcon.SNOWFLAKE.getChar() + "]", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_music))
			{
				_core.clientManager.getClient(player.getUniqueId()).setIcon(InfinitumIcon.MUSIC);
				player.closeInventory();
				UtilsMessages.inform("Icon", "You've set your icon to " + ChatColor.WHITE + "Music [" + InfinitumIcon.MUSIC.getChar() + "]", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_recycle))
			{
				player.closeInventory();
				UtilsMessages.inform("Icon", "This icon is coming soon!", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_compass))
			{
				_core.clientManager.getClient(player.getUniqueId()).setIcon(InfinitumIcon.COMPASS);
				player.closeInventory();
				UtilsMessages.inform("Icon", "You've set your icon to " + ChatColor.WHITE + "Compass [" + InfinitumIcon.COMPASS.getChar() + "]", player);
				return;
			}
			
			if(e.getCurrentItem().equals(_battery))
			{
				player.closeInventory();
				UtilsMessages.inform("Icon", "This icon is coming soon!", player);
				return;
			}
			
			return;
		}
	}
	
	
	
	
	
	
}
