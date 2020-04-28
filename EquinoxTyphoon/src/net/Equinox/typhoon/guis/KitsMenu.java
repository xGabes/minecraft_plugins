package net.Equinox.typhoon.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.Equinox.core.Core;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.typhoon.Typhoon;
import net.Equinox.typhoon.game.kits.TyphoonKits;

public class KitsMenu implements Listener
{
	
	private Typhoon _typhoon;
	private Core _core;
	private Inventory _inv;
	
	public KitsMenu(Typhoon plugin)
	{
		_typhoon = plugin;
		_core = Core.getPlugin(Core.class);
		_inv = Bukkit.createInventory(null, 27, "Kit Selection Menu");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack _zombie = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.ZOMBIE.ordinal())
			.name(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Zombie")
			.lore(false, ChatColor.DARK_GRAY + "Melee",
					"",
					ChatColor.WHITE + "Contains: ",
					ChatColor.GRAY + "- Zombie Skull Helmet",
					ChatColor.GRAY + "- Iron Chestplate",
					ChatColor.GRAY + "- Chain leggings",
					ChatColor.GRAY + "- Stone Axe",
					ChatColor.GRAY + "- x2 Zombie Flesh")
			.build();
	
	private ItemStack _skeleton = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.SKELETON.ordinal())
			.name(ChatColor.GRAY.toString() + ChatColor.BOLD + "Skeleton")
			.lore(false, ChatColor.DARK_GRAY + "Range",
					"",
					ChatColor.WHITE + "Contains: ",
					ChatColor.GRAY + "- Skeleton Skull Helmet",
					ChatColor.GRAY + "- Chain Chestplate",
					ChatColor.GRAY + "- Chain leggings",
					ChatColor.GRAY + "- Skeletal Bone (sharpness 2)",
					ChatColor.GRAY + "- Bow",
					ChatColor.GRAY + "- 1 arrow every 5 seconds")
			.build();
	
	private ItemStack _creeper = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.CREEPER.ordinal())
			.name(ChatColor.GREEN.toString() + ChatColor.BOLD + "Creeper")
			.lore(false, ChatColor.DARK_GRAY + "Defense",
					"",
					ChatColor.WHITE + "Contains: ",
					ChatColor.GRAY + "- Creeper Skull Helmet",
					ChatColor.GRAY + "- Diamond Chestplate",
					ChatColor.GRAY + "- Leather leggings",
					ChatColor.GRAY + "- Creeper Powder (sharpness 1)",
					ChatColor.GRAY + "- x3 Instant Exploding TNT")
			.build();
	
	public void openKits(Player p)
	{
		_inv.setItem(12, _zombie);
		_inv.setItem(13, _skeleton);
		_inv.setItem(14, _creeper);
		
		p.openInventory(_inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
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
			
			if(e.getCurrentItem().equals(_zombie))
			{
				_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.ZOMBIE);
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
				UtilsMessages.inform("Typhoon", "You selected the " + ChatColor.DARK_GREEN + TyphoonKits.ZOMBIE.getKitName() + ChatColor.GRAY + " kit", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_skeleton))
			{
				_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.SKELETON);
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
				UtilsMessages.inform("Typhoon", "You selected the " + ChatColor.GRAY+ TyphoonKits.SKELETON.getKitName() + ChatColor.GRAY + " kit!", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_creeper))
			{
				
				_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.CREEPER);
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
				UtilsMessages.inform("Typhoon", "You selected the " + ChatColor.GREEN + TyphoonKits.CREEPER.getKitName() + ChatColor.GRAY + " kit!", p);
				p.closeInventory();
				return;
			}
			
			return;
		}
	}
}
