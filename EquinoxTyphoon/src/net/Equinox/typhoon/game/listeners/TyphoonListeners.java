package net.Equinox.typhoon.game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.nms.Titles;
import net.Equinox.core.utils.ItemBuilder;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.core.utils.update.UpdateEvent;
import net.Equinox.typhoon.Typhoon;
import net.Equinox.typhoon.game.TyphoonState;
import net.Equinox.typhoon.game.kits.TyphoonKits;
import net.Equinox.typhoon.game.player.TyphoonPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TyphoonListeners implements Listener
{

	private Typhoon _typhoon;
	private Core _core;
	
	public TyphoonListeners(Typhoon plugin)
	{
		_typhoon = plugin;
		_core = Core.getPlugin(Core.class);
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	private ItemStack _kits;
	private ItemStack _shop;
	private ItemStack _cosmetics;
	
	public void addLobbyItems(Player p)
	{
		p.getInventory().clear();
		
		_kits = new ItemBuilder(Material.ARMOR_STAND)
				.name(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Kit Selector " + ChatColor.GRAY + "(click to open)")
				.lore(false, ChatColor.GRAY + "Use this item to select your kit")
				.build();
		_shop = new ItemBuilder(Material.ANVIL)
				.name(ChatColor.GREEN.toString() + ChatColor.BOLD + "Shop " + ChatColor.GRAY + "(Coming soon)")
				.lore(false, ChatColor.GRAY + "Use this item to buy kits and upgrades")
				.build();
		_cosmetics = new ItemBuilder(Material.ENDER_CHEST)
				.name(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Cosmetics" + ChatColor.GRAY + " (Coming soon)")
				.lore(false, ChatColor.GRAY + "Use this to select awesome",
						ChatColor.GRAY + "cosmetics for minigames")
				.build();
		
		p.getInventory().setItem(0, _kits);
		p.getInventory().setItem(3, _shop);
		p.getInventory().setItem(4, _cosmetics);
		
	}
	
	public void giveKitItem(Player p)
	{
		switch (_typhoon.playerManager.getTyphoonPlayer(p.getPlayer()).getKit())
		{
		case ZOMBIE:
			_typhoon.zombie.giveZombieItems(p);
			break;
		
		case SKELETON:
			_typhoon.skeleton.giveSkeletonItems(p);
			break;
		case CREEPER:
			_typhoon.creeper.giveCreeperItems(p);
			break;
		
		default: _typhoon.zombie.giveZombieItems(p);
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemPick(PlayerPickupItemEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		if(!(e.getPlayer().getLocation().getY() < 40)) return;
		
		if(_typhoon.game.hasStarted())
		{
			if(_typhoon.game.getSpectators().contains(e.getPlayer()))
			{
				e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
				return;
			}
			
			
			UtilsMessages.informAll("Typhoon", ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GRAY + " fell into the void");
			
			if(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getLives() > 1)
			{
				e.getPlayer().teleport(_typhoon.mapManager.getSelected().getSpawn());
				e.getPlayer().setHealth(20);
				_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).setLives(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getLives() - 1);
				e.getPlayer().getInventory().clear();
				giveKitItem(e.getPlayer());
				
			} else
			{
				e.getPlayer().setHealth(20);
				e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
				_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).setLives(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getLives() - 1);
				_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).setKit(TyphoonKits.SPECTATOR);
				_typhoon.game.removeAlivePlayer(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()));
				if(_typhoon.game.getAlive().size() > 1)
				{
					Titles.sendFullTitle(e.getPlayer(), 0, 60, 0, ChatColor.RED + "You lost!", ChatColor.YELLOW + "Wait for the next round to start!");
				}
			}
			
			if(_typhoon.game.getAlive().size() <= 1)
			{
				_typhoon.game.gameFinish();
			}
			
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(_typhoon.game.hasStarted())
		{
			return;
		}
		
		if(e.getPlayer().getItemInHand().equals(_kits))
		{
			_typhoon.kitMenu.openKits(e.getPlayer());
			return;
		}
		
		if(e.getPlayer().getItemInHand().equals(_shop))
		{
			UtilsMessages.inform("Typhoon Shop", "This is coming soon!", e.getPlayer());
			return;
		}
		
		if(e.getPlayer().getItemInHand().equals(_cosmetics))
		{
			_core.cosmeticsMenu.openMain(e.getPlayer());
			return;
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}
		
		if(e.getInventory() == null)
		{
			return;
		}
		
		if(e.getCurrentItem() == null)
		{
			return;
		}
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		e.getPlayer().setHealth(20);
		e.getPlayer().setFoodLevel(20);
		if(_typhoon.game.hasStarted())
		{
			TyphoonPlayer typhoonp = new TyphoonPlayer(e.getPlayer(), TyphoonKits.SPECTATOR);
			_typhoon.playerManager.addTyphoonPlayer(typhoonp);
			UtilsMessages.inform("Typhoon", "The game is already started! Wait for the next round!", e.getPlayer());
			return;
		}
		
		TyphoonPlayer typhoonp = new TyphoonPlayer(e.getPlayer(), TyphoonKits.ZOMBIE);
		_typhoon.playerManager.addTyphoonPlayer(typhoonp);
		_typhoon.scoreboard.getKitTeam().get(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()).getKit()).addPlayer(e.getPlayer());
		addLobbyItems(e.getPlayer());
		
		
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if(_typhoon.game.hasStarted())
		{
			if(_typhoon.game.getAlive().contains(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer())))
			{
				_typhoon.game.removeAlivePlayer(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()));
			}
			if(_typhoon.game.getSpectators().contains(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer())))
			{
				_typhoon.game.removeSpector(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()));
			}
			if(_typhoon.game.getAlive().size() <= 1)
			{
				_typhoon.game.gameFinish();
			}
		}
		
		_typhoon.playerManager.removeTyphoonPlayer(_typhoon.playerManager.getTyphoonPlayer(e.getPlayer()));
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player)) return;
		
		if(e.getCause() == DamageCause.FALL)
		{
			e.setCancelled(true);
		}
		
		if(e.getCause() == DamageCause.BLOCK_EXPLOSION)
		{
			Player p = (Player) e.getEntity();
			if((p.getHealth() - e.getFinalDamage()) <= 0)
			{
				e.setCancelled(true);
				UtilsMessages.informAll("Typhoon", ""+ ChatColor.YELLOW + e.getEntity().getName() + ChatColor.GRAY + " has exploded");
				
				if(_typhoon.playerManager.getTyphoonPlayer(p).getLives() > 1)
				{
					p.setHealth(20);
					e.getEntity().teleport(_typhoon.mapManager.getSelected().getSpawn());
					_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
					p.getInventory().clear();
					giveKitItem(p);
					
				} else
				{
					p.setHealth(20);
					e.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
					_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
					_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.SPECTATOR);
					_typhoon.game.removeAlivePlayer(_typhoon.playerManager.getTyphoonPlayer(p));
					if(_typhoon.game.getAlive().size() > 1)
					{
						Titles.sendFullTitle(p, 0, 60, 0, ChatColor.RED + "You lost!", ChatColor.YELLOW + "Wait for the next round to start!");
					}
				}
				
				if(_typhoon.game.getAlive().size() <= 1)
				{
					_typhoon.game.gameFinish();
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
	{
		if(_typhoon.game.getGameState().equals(TyphoonState.LOBBY) || _typhoon.game.getGameState().equals(TyphoonState.SECONDS))
		{
			e.setCancelled(true);
			return;
		}
		
		if(e.getCause().equals(DamageCause.PROJECTILE))
		{
			if(!(e.getDamager() instanceof Arrow))
			{
				return;
			}
			
			Projectile arrow = (Projectile) e.getDamager();
			Player p = (Player) e.getEntity();
			ProjectileSource dam = arrow.getShooter();
			
			if(!(dam instanceof Player))
			{
				return;
			}
			
			Player damager = (Player) dam;
			
			if((p.getHealth() - e.getDamage()) <= 0)
			{
				e.setCancelled(true);
				_typhoon.playerManager.getTyphoonPlayer(damager).setCoins(_typhoon.playerManager.getTyphoonPlayer(damager).getCoins() + 5);
				_typhoon.playerManager.getTyphoonPlayer(damager).setCubits(_typhoon.playerManager.getTyphoonPlayer(damager).getCubits() + 2);
				
				UtilsMessages.informAll("Typhoon", ""+ ChatColor.YELLOW + e.getEntity().getName() + ChatColor.GRAY + " has been shot by " + ChatColor.RED + damager.getName());
				
				if(_core.clientManager.getClient(damager.getUniqueId()).getRank().isRank(Rank.VIP))
				{
					damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits" + " (+2 for your rank)").create());
				}else if(_core.clientManager.getClient(damager.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(damager.getUniqueId()).getRank(), Rank.PRO))
				{
					damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits" + " (+4 for your rank)").create());
				} else 
				{
					damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits").create());
				}
				
				
				if(_typhoon.playerManager.getTyphoonPlayer(p).getLives() > 1)
				{
					p.setHealth(20);
					e.getEntity().teleport(_typhoon.mapManager.getSelected().getSpawn());
					_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
					p.getInventory().clear();
					giveKitItem(p);
					
				} else
				{
					p.setHealth(20);
					e.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
					_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
					_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.SPECTATOR);
					_typhoon.game.removeAlivePlayer(_typhoon.playerManager.getTyphoonPlayer(p));
					if(_typhoon.game.getAlive().size() > 1)
					{
						Titles.sendFullTitle(p, 0, 60, 0, ChatColor.RED + "You lost!", ChatColor.YELLOW + "Wait for the next round to start!");
					}
				}
				
				if(_typhoon.game.getAlive().size() <= 1)
				{
					_typhoon.game.gameFinish();
				}
			}
			
			return;
		}
		
		Player p = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		if((p.getHealth() - e.getFinalDamage()) <= 0)
		{
			e.setCancelled(true);
			_typhoon.playerManager.getTyphoonPlayer(damager).setCoins(_typhoon.playerManager.getTyphoonPlayer(damager).getCoins() + 5);
			_typhoon.playerManager.getTyphoonPlayer(damager).setCubits(_typhoon.playerManager.getTyphoonPlayer(damager).getCubits() + 2);
			
			UtilsMessages.informAll("Typhoon", ""+ ChatColor.YELLOW + e.getEntity().getName() + ChatColor.GRAY + " has been killed by " + ChatColor.RED + damager.getName());
			
			if(_core.clientManager.getClient(damager.getUniqueId()).getRank().isRank(Rank.VIP))
			{
				damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits" + " (+2 for your rank)").create());
			}else if(_core.clientManager.getClient(damager.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(damager.getUniqueId()).getRank(), Rank.PRO))
			{
				damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits" + " (+4 for your rank)").create());
			} else 
			{
				damager.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.GRAY +  "You earned " + ChatColor.YELLOW + "5 coins" + ChatColor.GRAY + " and " + ChatColor.AQUA + "2 cubits").create());
			}
			
			
			if(_typhoon.playerManager.getTyphoonPlayer(p).getLives() > 1)
			{
				p.setHealth(20);
				e.getEntity().teleport(_typhoon.mapManager.getSelected().getSpawn());
				_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
				p.getInventory().clear();
				giveKitItem(p);
				
			} else
			{
				p.setHealth(20);
				e.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
				_typhoon.playerManager.getTyphoonPlayer(p).setLives(_typhoon.playerManager.getTyphoonPlayer(p).getLives() - 1);
				_typhoon.playerManager.getTyphoonPlayer(p).setKit(TyphoonKits.SPECTATOR);
				_typhoon.game.removeAlivePlayer(_typhoon.playerManager.getTyphoonPlayer(p));
				if(_typhoon.game.getAlive().size() > 1)
				{
					Titles.sendFullTitle(p, 0, 60, 0, ChatColor.RED + "You lost!", ChatColor.YELLOW + "Wait for the next round to start!");
				}
			}
			
			if(_typhoon.game.getAlive().size() <= 1)
			{
				_typhoon.game.gameFinish();
			}
		}
	}
	
	/*public void onPlayerDeath(PlayerDeathEvent e)
	{
	}*/
}
