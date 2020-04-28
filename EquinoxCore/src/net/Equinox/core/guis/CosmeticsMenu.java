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
import net.Equinox.core.cosmetics.deathcries.DeathCryType;
import net.Equinox.core.cosmetics.infinitum.InfinitumTrailType;
import net.Equinox.core.cosmetics.killeffects.KillEffectType;
import net.Equinox.core.cosmetics.swordheros.SwordHeroType;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class CosmeticsMenu implements Listener
{

	private Core _core;
	private Player _user;
	
	/*
	 * Inventories
	 */
	private Inventory _main;
	private Inventory _infinitumMenu;
	private Inventory _deathCryMenu;
	private Inventory _killEffectMenu;
	private Inventory _swordHeroMenu;
	
	public CosmeticsMenu(Core plugin)
	{
		_core = plugin;
		_main = Bukkit.createInventory(null, 45, "Cosmetics");
		_infinitumMenu = Bukkit.createInventory(null, 45, "Infinitum Trails");
		
	}
	
	private ItemStack _infinitumTrails;
	
	private ItemStack _deathCry;
	private ItemStack _killEffect;
	private ItemStack _swordHero;

	public void openMain(Player p)
	{
		_user = p;
		
		_infinitumTrails = new ItemBuilder(Material.ENDER_PORTAL_FRAME)
				.name(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Trails")
				.lore(false, ChatColor.GRAY + "These amazing particle trails",
						ChatColor.GRAY + "will follow you around as you walk!",
						"",
						ChatColor.GRAY + "This is " + ChatColor.DARK_PURPLE + "Infinitum " + ChatColor.GRAY + "only!",
						ChatColor.GRAY + "Owned: " + (_core.clientManager.getClient(p.getUniqueId()).isInfinitum() ? ChatColor.YELLOW + "5/5" : ChatColor.YELLOW + "0/5"))
				.build();
		
		_deathCry = new ItemBuilder(Material.SULPHUR)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Death Cry")
				.lore(false, ChatColor.GRAY + "Leave a small surprise when you die",
						ChatColor.GRAY + "with these amazing effects.",
						"",
						ChatColor.GRAY + "Currently enabled:" + (_core.deathCryManager.hasEnabled(p.getUniqueId()) ? ChatColor.GREEN + _core.deathCryManager.getEnabled(p.getUniqueId()).getDeathCryName() : ChatColor.DARK_GRAY + " none"),
						ChatColor.GRAY + "Owned: " + ChatColor.YELLOW +  _core.deathCryManager.getAmountOwned(p.getUniqueId()) + "/" +DeathCryType.values().length)
				.build();
		
		_killEffect = new ItemBuilder(Material.BONE)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Kill Effect")
				.lore(false, ChatColor.GRAY + "Have these effects spawn around",
						ChatColor.GRAY + "you when you kill an enemy",
						"",
						ChatColor.GRAY + "Currently enabled:" + (_core.killEffectManager.hasEnabled(p.getUniqueId()) ? ChatColor.GREEN + _core.killEffectManager.getEnabled(p.getUniqueId()).getKillEffectName() : ChatColor.DARK_GRAY + " none"),
						ChatColor.GRAY + "Owned: " + ChatColor.YELLOW +  _core.killEffectManager.getAmountOwned(p.getUniqueId()) + "/" + KillEffectType.values().length)
				.build();
		
		_swordHero = new ItemBuilder(Material.DIAMOND_SWORD)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Sword Hero")
				.lore(false, ChatColor.GRAY + "Every time you swing your sword",
						ChatColor.GRAY + "the hero inside you gets revealed",
						"",
						ChatColor.GRAY + "Currently enabled:" + (_core.swordHeroManager.hasEnabled(p.getUniqueId()) ? ChatColor.GREEN + _core.swordHeroManager.getEnabled(p.getUniqueId()).getSwordHeroName() : ChatColor.DARK_GRAY + " none"),
						ChatColor.GRAY + "Owned: " + ChatColor.YELLOW + _core.swordHeroManager.getAmountOwned(p.getUniqueId()) + "/" + SwordHeroType.values().length)
				.build();
		
		_main.setItem(13, _infinitumTrails);
		_main.setItem(31, _killEffect);
		_main.setItem(29, _swordHero);
		_main.setItem(33, _deathCry);
		
		p.openInventory(_main);
		
	}
	
	private ItemStack _redDust;
	private ItemStack _flame;
	private ItemStack _firework;
	private ItemStack _water;
	private ItemStack _lava;
	
	public void openInfinitum(Player p)
	{
		_user = p;
		
		_redDust = new ItemBuilder(Material.REDSTONE)
				.name(ChatColor.RED.toString() + ChatColor.BOLD + "Red Dust")
				.lore(false, ChatColor.DARK_GRAY + "Trail", "",
						ChatColor.GRAY + "Have a trail of red dust",
						ChatColor.GRAY + "follow you as you walk!",
						"",
						(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.RED_DUST) ? ChatColor.RED + "Selected! Click to disable" : ChatColor.YELLOW + "Click to select!"))
				.build();
		
		_flame = new ItemBuilder(Material.BLAZE_POWDER)
				.name(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Flame")
				.lore(false, ChatColor.DARK_GRAY + "Trail", "",
						ChatColor.GRAY + "Have a flame trail",
						ChatColor.GRAY + "follow you as you walk!",
						"",
						(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.FLAME) ? ChatColor.RED + "Selected! Click to disable" : ChatColor.YELLOW + "Click to select!"))
				.build();
		
		_firework = new ItemBuilder(Material.FIREWORK)
				.name(ChatColor.WHITE.toString() + ChatColor.BOLD + "Firework Spark")
				.lore(false, ChatColor.DARK_GRAY + "Trail", "",
						ChatColor.GRAY + "Have a trail of firework sparks",
						ChatColor.GRAY + "follow you as you walk!",
						"",
						(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.FIREWORK) ? ChatColor.RED + "Selected! Click to disable" : ChatColor.YELLOW + "Click to select!"))
				.build();
		
		_water = new ItemBuilder(Material.WATER_BUCKET)
				.name(ChatColor.AQUA.toString() + ChatColor.BOLD + "Water Drops")
				.lore(false, ChatColor.DARK_GRAY + "Trail", "",
						ChatColor.GRAY + "Have a trail of water droplets",
						ChatColor.GRAY + "follow you as you walk!",
						"",
						(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.WATER) ? ChatColor.RED + "Selected! Click to disable" : ChatColor.YELLOW + "Click to select!"))
				.build();
		
		_lava = new ItemBuilder(Material.LAVA_BUCKET)
				.name(ChatColor.GOLD.toString() + ChatColor.BOLD + "Lava Drops")
				.lore(false, ChatColor.DARK_GRAY + "Trail", "",
						ChatColor.GRAY + "Have a trail of lava droplets",
						ChatColor.GRAY + "follow you as you walk!",
						"",
						(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.LAVA) ? ChatColor.RED + "Selected! Click to disable" : ChatColor.YELLOW + "Click to select!"))
				.build();
		
		_infinitumMenu.setItem(12, _redDust);
		_infinitumMenu.setItem(14, _flame);
		_infinitumMenu.setItem(22, _firework);
		_infinitumMenu.setItem(30, _water);
		_infinitumMenu.setItem(32, _lava);
		
		p.openInventory(_infinitumMenu);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory() == null)
		{
			return;
		}
		
		if(e.getInventory().equals(_main))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_infinitumTrails))
			{
				if(!_core.clientManager.getClient(p.getUniqueId()).isInfinitum())
				{
					UtilsMessages.inform("Cosmetics", "You don't have access to those items!", p);
					p.closeInventory();
					return;
				}
				
				openInfinitum(p);
				return;
			}
			
			return;
		}
		if(e.getInventory().equals(_infinitumMenu))
		{
			if(e.getCurrentItem() == null)
			{
				return;
			}
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(_redDust))
			{
				if(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.RED_DUST))
				{
					_core.infinitumTrailManager.disable(p.getUniqueId());
					UtilsMessages.inform("Cosmetics", "You've disabled your " + _redDust.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
					p.closeInventory();
					return;
				}
				
				_core.infinitumTrailManager.setEnabled(p.getUniqueId(), InfinitumTrailType.RED_DUST);
				UtilsMessages.inform("Cosmetics", "You've enabled " + _redDust.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_flame))
			{
				if(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.FLAME))
				{
					_core.infinitumTrailManager.disable(p.getUniqueId());
					UtilsMessages.inform("Cosmetics", "You've disabled your " + _flame.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
					p.closeInventory();
					return;
				}
				
				_core.infinitumTrailManager.setEnabled(p.getUniqueId(), InfinitumTrailType.FLAME);
				UtilsMessages.inform("Cosmetics", "You've enabled " + _flame.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_firework))
			{
				if(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.FIREWORK))
				{
					_core.infinitumTrailManager.disable(p.getUniqueId());
					UtilsMessages.inform("Cosmetics", "You've disabled your " + _firework.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
					p.closeInventory();
					return;
				}
				
				_core.infinitumTrailManager.setEnabled(p.getUniqueId(), InfinitumTrailType.FIREWORK);
				UtilsMessages.inform("Cosmetics", "You've enabled " + _firework.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_water))
			{
				if(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.WATER))
				{
					_core.infinitumTrailManager.disable(p.getUniqueId());
					UtilsMessages.inform("Cosmetics", "You've disabled your " + _water.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
					p.closeInventory();
					return;
				}
				
				_core.infinitumTrailManager.setEnabled(p.getUniqueId(), InfinitumTrailType.WATER);
				UtilsMessages.inform("Cosmetics", "You've enabled " + _water.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().equals(_lava))
			{
				if(_core.infinitumTrailManager.isEnabled(p.getUniqueId(), InfinitumTrailType.LAVA))
				{
					_core.infinitumTrailManager.disable(p.getUniqueId());
					UtilsMessages.inform("Cosmetics", "You've disabled your " + _lava.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
					p.closeInventory();
					return;
				}
				
				_core.infinitumTrailManager.setEnabled(p.getUniqueId(), InfinitumTrailType.LAVA);
				UtilsMessages.inform("Cosmetics", "You've enabled " + _lava.getItemMeta().getDisplayName() + ChatColor.GRAY + " trail", p);
				p.closeInventory();
				return;
			}
			
			return;
		}
	}
	
}
