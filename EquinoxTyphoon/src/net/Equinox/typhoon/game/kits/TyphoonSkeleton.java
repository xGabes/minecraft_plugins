package net.Equinox.typhoon.game.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.update.UpdateEvent;
import net.Equinox.core.utils.update.UpdateType;
import net.Equinox.typhoon.Typhoon;

public class TyphoonSkeleton implements Listener
{
	
	private Typhoon _typhoon;
	
	public TyphoonSkeleton(Typhoon plugin)
	{
		_typhoon = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		arrowGiver();
	}
	
	private ItemStack _skull = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.SKELETON.ordinal())
			.name(ChatColor.GRAY + "Skeleton's Helmet")
			.lore(false, "")
			.build();
	
	private ItemStack _chestplate = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
			.name(ChatColor.GRAY + "Skeleton's Chestplate")
			.lore(false, "")
			.build();
	
	private ItemStack _leggins = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
			.name(ChatColor.GRAY + "Skeleton's Leggings")
			.lore(false, "")
			.build();
	
	private ItemStack _bone = new ItemBuilder(Material.BONE)
			.unsafeEnchant(Enchantment.DAMAGE_ALL, 2)
			.name(ChatColor.GRAY + "Skeleton's Bone")
			.lore(false, "")
			.build();
	
	private ItemStack _bow = new ItemBuilder(Material.BOW)
			.name(ChatColor.GRAY + "Skeleton's Bow")
			.lore(false, "")
			.build();
	
	private ItemStack _arrow = new ItemBuilder(Material.ARROW)
			.name(ChatColor.GRAY + "Skeleton's Arrow")
			.lore(false, "")
			.build();
	
	public void giveSkeletonItems(Player p)
	{
		p.getInventory().setHelmet(_skull);
		p.getInventory().setChestplate(_chestplate);
		p.getInventory().setLeggings(_leggins);
		p.getInventory().setItem(0, _bone);
		p.getInventory().setItem(1, _bow);
		p.getInventory().addItem(_arrow);
	}
	
	public void arrowGiver()
	{
		int task = _typhoon.getServer().getScheduler().scheduleSyncRepeatingTask(_typhoon, new Runnable()
				{

					@Override
					public void run() 
					{
						if(_typhoon.game.hasStarted())
						{
							for(Player p : Bukkit.getOnlinePlayers())
							{
								if(_typhoon.playerManager.getTyphoonPlayer(p).getKit().equals(TyphoonKits.SKELETON))
								{
									if(p.getInventory().getItem(2) == null)
									{
										p.getInventory().addItem(_arrow);
										return;
									}
									
									if(p.getInventory().getItem(2).getAmount() >= 3)
									{
										return;
									}
									
									p.getInventory().addItem(_arrow);
								}
							}
						}
						
					}
			
				}, 0	, 20*5L);
	}
	
}
