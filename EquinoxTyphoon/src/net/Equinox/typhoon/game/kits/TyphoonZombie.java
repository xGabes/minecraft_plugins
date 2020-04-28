package net.Equinox.typhoon.game.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.typhoon.Typhoon;

public class TyphoonZombie implements Listener
{

	private Typhoon _typhoon;
	
	public TyphoonZombie(Typhoon plugin)
	{
		_typhoon = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack _skull = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.ZOMBIE.ordinal())
			.name(ChatColor.DARK_GREEN + "Zombie's Helmet")
			.lore(false, "")
			.build();
	
	private ItemStack _chestplate = new ItemBuilder(Material.IRON_CHESTPLATE)
			.name(ChatColor.DARK_GREEN + "Zombie's Chestplate")
			.lore(false, "")
			.build();
	
	private ItemStack _leggins = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
			.name(ChatColor.DARK_GREEN + "Zombie's Leggings")
			.lore(false, "")
			.build();
	
	private ItemStack _axe = new ItemBuilder(Material.STONE_AXE)
			.name(ChatColor.DARK_GREEN + "Zombie's Axe")
			.lore(false, "")
			.build();
	
	private ItemStack _flesh = new ItemBuilder(Material.ROTTEN_FLESH)
			.name(ChatColor.DARK_GREEN + "Zombie Flesh" + ChatColor.GRAY + " (click to activate)")
			.lore(false, ChatColor.WHITE + "When activated:",
					ChatColor.GRAY + "Speed 2 for 5 seconds",
					ChatColor.GRAY + "Regenerates 2 hearts")
			.build();
	
	public void giveZombieItems(Player p)
	{
		p.getInventory().setHelmet(_skull);
		p.getInventory().setChestplate(_chestplate);
		p.getInventory().setLeggings(_leggins);
		p.getInventory().setItem(0, _axe);
		p.getInventory().addItem(_flesh);
		p.getInventory().addItem(_flesh);
	}
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent e)
	{
		
		if(!_typhoon.game.hasStarted()) return;
		if(!_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getKit().equals(TyphoonKits.ZOMBIE)) return;
		
		if(e.getPlayer().getInventory().getItemInHand().equals(e.getPlayer().getInventory().getItem(1)))
		{
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				if(e.getPlayer().getInventory().getItem(1).getAmount() > 1)
				{
					e.getPlayer().getInventory().setItem(1, _flesh);
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 1));
					if(e.getPlayer().getHealth() >= 17)
					{
						e.getPlayer().setHealth(20);
						return;
					}
					e.getPlayer().setHealth(e.getPlayer().getHealth() + 4);
					return;
				}
				
				e.getPlayer().getInventory().getItem(1).setAmount(_flesh.getAmount() - 1);
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 1));
				if(e.getPlayer().getHealth() >= 16)
				{
					e.getPlayer().setHealth(20D);
					return;
				}
				
				e.getPlayer().setHealth(e.getPlayer().getHealth() + 4);
			}
			
			return;
		}
	}
		
}
