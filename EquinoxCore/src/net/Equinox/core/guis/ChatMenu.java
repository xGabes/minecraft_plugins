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
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class ChatMenu implements Listener
{

	private Core _core;
	private Player _staff;
	
	private Inventory _inv;
	private Inventory _slowInv;
	private Inventory _silenceInv;
	
	/*
	 *  Main Menu Items
	 */
	
	private ItemStack _slow = new ItemBuilder(Material.TRIPWIRE_HOOK)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Chat Slow")
			.lore(false, ChatColor.GRAY + "Slow the chat down to avoid spam.")
			.build();
	private ItemStack _mute = new ItemBuilder(Material.LEVER)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Chat Mute")
			.lore(false, ChatColor.GRAY + "Mute the chat to avoid spam,",
					ChatColor.GRAY + "advertising and other.")
			.build();
	private ItemStack _clear = new ItemBuilder(Material.REDSTONE_BLOCK)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Clear chat")
			.lore(false,	ChatColor.GRAY + "Clears the chat.")
			.build();
	
	/*
	 * Chat Slow Items
	 */
	
	private ItemStack _oneSecond = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(0)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "1 Second")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 1 second.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _twoSeconds = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(5)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "2 Seconds")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 2 seconds.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _threeSeconds = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(4)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "3 Seconds")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 3 seconds.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _fourSeconds = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(1)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "4 Seconds")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 4 seconds.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _fiveSeconds = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(14)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "5 Seconds")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 5 seconds.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _tenSeconds = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(15)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "10 Seconds")
			.lore(false, ChatColor.GRAY + "Allow users to send messages every 10 seconds.",
					ChatColor.RED + "To be used in extreme cases.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	/*
	 * Chat Silence Items
	 */
	
	private ItemStack _oneMinute = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(0)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "1 minute")
			.lore(false, ChatColor.GRAY + "Silence the chat for 1 minute",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _twoMinutes = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(5)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "2 minutes")
			.lore(false, ChatColor.GRAY + "Silence the chat for 2 minutes",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _threeMinutes = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(4)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "3 minutes")
			.lore(false, ChatColor.GRAY + "Silence the chat for 3 minutes",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _fourMinutes = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(1)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "4 minutes")
			.lore(false, ChatColor.GRAY + "Silence the chat for 4 minutes",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _fiveMinutes = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(14)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "5 minutes")
			.lore(false, ChatColor.GRAY + "Silence the chat for 5 minutes",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	private ItemStack _permanent = new ItemBuilder(Material.STAINED_GLASS_PANE)
			.durability(15)
			.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "10 minutes")
			.lore(false, ChatColor.GRAY + "Silence the chat permanently",
					ChatColor.RED + "To be used in extreme cases.",
					ChatColor.GRAY + "",
					ChatColor.YELLOW + "Click to select!")
			.build();
	
	
	public ChatMenu(Core plugin)
	{
		_core = plugin;
		_inv = Bukkit.createInventory(null, 27, "Chat Management");
		_slowInv = Bukkit.createInventory(null, 27, "Chat Slow");
		_silenceInv = Bukkit.createInventory(null, 27, "Chat Silence");
	}
	
	public void openMain(Player p)
	{
		_staff = p;
		_inv.setItem(11, _slow);
		_inv.setItem(13, _mute);
		_inv.setItem(15, _clear);
		
		p.openInventory(_inv);
	}
	
	public void openSlow(Player p)
	{
		_slowInv.clear();
		_slowInv.setItem(4, _slow);
		_slowInv.setItem(19, _oneSecond);
		_slowInv.setItem(20, _twoSeconds);
		_slowInv.setItem(21, _threeSeconds);
		_slowInv.setItem(22, _fourSeconds);
		_slowInv.setItem(23, _fiveSeconds);
		_slowInv.setItem(25, _tenSeconds);
		
		p.openInventory(_slowInv);
	}
	
	public void openSilence(Player p)
	{
		_silenceInv.clear();
		_silenceInv.setItem(4, _mute);
		_silenceInv.setItem(19, _oneMinute);
		_silenceInv.setItem(20, _twoMinutes);
		_silenceInv.setItem(21, _threeMinutes);
		_silenceInv.setItem(22, _fourMinutes);
		_silenceInv.setItem(23, _fiveMinutes);
		_silenceInv.setItem(25, _permanent);
		
		p.openInventory(_silenceInv);
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
			if(e.getCurrentItem() == null ) 
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_slow))
			{
				this.openSlow(_staff);
				return;
			}
			
			if(e.getCurrentItem().equals(_mute))
			{
				this.openSilence(_staff);
				return;
			}
			
			if(e.getCurrentItem().equals(_clear))
			{
				for(int i = 0; i < 100; i++)
				{
					Bukkit.broadcastMessage("");
				}
				
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has cleared the chat");
				_staff.closeInventory();
				return;
			}
			
			return;
		}
		
		if(e.getInventory().equals(_slowInv))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			if(e.getCurrentItem().equals(_slow))
			{
				_core.chatManager.setChatSlowed(false, 0);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has removed the chat slow");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_oneSecond))
			{
				_core.chatManager.setChatSlowed(true, 1 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 1 second");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_twoSeconds))
			{
				_core.chatManager.setChatSlowed(true, 2 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 2 seconds");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_threeSeconds))
			{
				_core.chatManager.setChatSlowed(true, 3 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 3 seconds");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_fourSeconds))
			{
				_core.chatManager.setChatSlowed(true, 4 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 4 seconds");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_fiveSeconds))
			{
				_core.chatManager.setChatSlowed(true, 5 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 5 seconds");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_tenSeconds))
			{
				_core.chatManager.setChatSlowed(true, 10 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has set the chat slow to" + ChatColor.WHITE + " 10 seconds");
				_staff.closeInventory();
				return;
			}
			
			return;
		}
		
		if(e.getInventory().equals(_silenceInv))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			if(e.getCurrentItem().equals(_mute))
			{
				_core.chatManager.setSilenced(false, 0L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has removed the chat silence");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_oneMinute))
			{
				_core.chatManager.setSilenced(true, 1 * 60 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat for" + ChatColor.WHITE + " 1 Minute");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_twoMinutes))
			{
				_core.chatManager.setSilenced(true, 2 * 60 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat for" + ChatColor.WHITE + " 2 Minutes");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_threeMinutes))
			{
				_core.chatManager.setSilenced(true, 3 * 60 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat for" + ChatColor.WHITE + " 3 Minutes");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_fourMinutes))
			{
				_core.chatManager.setSilenced(true, 4 * 60 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat for" + ChatColor.WHITE + " 4 Minutes");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_fiveMinutes))
			{
				_core.chatManager.setSilenced(true, 5 * 60 * 1000L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat for" + ChatColor.WHITE + " 5 Minutes");
				_staff.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_permanent))
			{
				_core.chatManager.setSilenced(true, -1L);
				UtilsMessages.informAll("Chat", "Staff " + ChatColor.RED + _staff.getName() + ChatColor.GRAY + " has silenced the chat" + ChatColor.WHITE + " Permanently");
				_staff.closeInventory();
				return;
			}
			
			return;
		}
		
	}
	
	
}
