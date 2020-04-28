package net.Equinox.typhoon.game.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.typhoon.Typhoon;

public class TyphoonCreeper implements Listener
{

	
	private Typhoon _typhoon;
	private List<Player> _explosion;
	
	public TyphoonCreeper(Typhoon plugin)
	{
		_typhoon = plugin;
		_explosion = new ArrayList<Player>();

		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack _skull = new ItemBuilder(Material.SKULL_ITEM)
			.durability(SkullType.CREEPER.ordinal())
			.name(ChatColor.GREEN + "Creeper's Helmet")
			.lore(false, "")
			.build();
	
	private ItemStack _chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
			.name(ChatColor.GREEN + "Creeper's Chestplate")
			.lore(false, "")
			.build();
	
	private ItemStack _leggins = new ItemBuilder(Material.LEATHER_LEGGINGS)
			.name(ChatColor.GREEN + "Creeper's Leggings")
			.lore(false, "")
			.build();
	
	private ItemStack _powder = new ItemBuilder(Material.SULPHUR)
			.unsafeEnchant(Enchantment.DAMAGE_ALL, 1)
			.name(ChatColor.GREEN + "Creeper's Powder")
			.lore(false, "")
			.build();
	
	private ItemStack _tnt = new ItemBuilder(Material.TNT)
			.name(ChatColor.GREEN + "Creeper Explosion" + ChatColor.GRAY + " (click to activate)")
			.lore(false, ChatColor.WHITE + "When activated:",
					ChatColor.GRAY + "You instantly explode",
					ChatColor.GRAY + "dealing damage to those around you")
			.build();
	
	public void giveCreeperItems(Player p)
	{
		p.getInventory().setHelmet(_skull);
		p.getInventory().setChestplate(_chestplate);
		p.getInventory().setLeggings(_leggins);
		p.getInventory().setItem(0, _powder);
		p.getInventory().addItem(_tnt);
		p.getInventory().addItem(_tnt);
		p.getInventory().addItem(_tnt);
	}
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent e)
	{
		
		if(!_typhoon.game.hasStarted()) return;
		if(!_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getKit().equals(TyphoonKits.CREEPER)) return;
		
		if(e.getPlayer().getInventory().getItemInHand().equals(e.getPlayer().getInventory().getItem(1)))
		{
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				
				e.getPlayer().getInventory().getItem(1).setAmount(e.getPlayer().getInventory().getItem(1).getAmount() - 1);
				if(_explosion.contains(e.getPlayer()))
				{
					_explosion.remove(e.getPlayer());
					_explosion.add(e.getPlayer());
				} else
				{
					_explosion.add(e.getPlayer());
				}
				
				e.getPlayer().getWorld().createExplosion(e.getPlayer().getLocation().getX(),
						e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ(), 2, false, false);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(_typhoon, new Runnable()
						{

							@Override
							public void run() 
							{
								if(_explosion.contains(e.getPlayer()))
								{
									_explosion.remove(e.getPlayer());
								}
							}
					
						}, 10L);
			}
			
			return;
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{
		if(!_typhoon.game.hasStarted()) return;
		
		 Player p = (Player) e.getEntity();
		
		if(e.getCause() == DamageCause.BLOCK_EXPLOSION || e.getCause() == DamageCause.ENTITY_EXPLOSION)
		{
			if(_explosion.contains(p))
			{
				e.setCancelled(true);
			}
		}
		
	}
}
