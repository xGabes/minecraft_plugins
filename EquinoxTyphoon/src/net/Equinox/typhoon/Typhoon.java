package net.Equinox.typhoon;

import org.bukkit.WorldCreator;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.Equinox.typhoon.game.TyphoonCountdown;
import net.Equinox.typhoon.game.TyphoonGame;
import net.Equinox.typhoon.game.kits.TyphoonCreeper;
import net.Equinox.typhoon.game.kits.TyphoonSkeleton;
import net.Equinox.typhoon.game.kits.TyphoonZombie;
import net.Equinox.typhoon.game.listeners.TyphoonListeners;
import net.Equinox.typhoon.game.map.TyphoonMapManager;
import net.Equinox.typhoon.game.player.TyphoonPlayerManager;
import net.Equinox.typhoon.guis.KitsMenu;


/*
 * -1 lives when jumping into the void and game started and youre spectator.
 * disable /whisper and /me in core
 */


public class Typhoon extends JavaPlugin
{
	
	
	public TyphoonGame game;
	public TyphoonPlayerManager playerManager;
	public TyphoonCountdown countdown;
	public TyphoonMapManager mapManager;
	public TyphoonBoard scoreboard;
	public TyphoonListeners listeners;
	public KitsMenu kitMenu;
	
	
	public TyphoonZombie zombie;
	public TyphoonSkeleton skeleton;
	public TyphoonCreeper creeper;
	
	public FileConfiguration config = getConfig();

	public void onEnable()
	{
		
		getServer().createWorld(new WorldCreator("RuinsMap"));
		getServer().createWorld(new WorldCreator("IslandMap"));
		getServer().createWorld(new WorldCreator("NetherMap"));
		
		scoreboard = new TyphoonBoard(this);
		listeners = new TyphoonListeners(this);
		countdown = new TyphoonCountdown(this, 30);
		playerManager = new TyphoonPlayerManager();
		game = new TyphoonGame(this);
		mapManager = new TyphoonMapManager(this);
		kitMenu = new KitsMenu(this);
		zombie = new TyphoonZombie(this);
		skeleton = new TyphoonSkeleton(this);
		creeper = new TyphoonCreeper(this);
		
		
		config.createSection("maps");
		
		config.getConfigurationSection("maps").set("Island.world", "IslandMap");
		config.getConfigurationSection("maps").set("Island.x", 185.5);
		config.getConfigurationSection("maps").set("Island.y", 128.5);
		config.getConfigurationSection("maps").set("Island.z", -37.5);
		
		config.getConfigurationSection("maps").set("Nether.world", "NetherMap");
		config.getConfigurationSection("maps").set("Nether.x", 93.5);
		config.getConfigurationSection("maps").set("Nether.y", 127.5);
		config.getConfigurationSection("maps").set("Nether.z", -44.5);
		
		config.getConfigurationSection("maps").set("Ruins.world", "RuinsMap");
		config.getConfigurationSection("maps").set("Ruins.x", 242.5);
		config.getConfigurationSection("maps").set("Ruins.y", 108.5);
		config.getConfigurationSection("maps").set("Ruins.z", 224.5);
		
		mapManager.loadMap(1, "Island");
		mapManager.loadMap(2, "Nether");
		mapManager.loadMap(3, "Ruins");
		countdown.startCountdown();
		mapManager.pickMap();
		
		
		
	}
	
	public void onDisable()
	{
		this.saveConfig();
	}
	
	
}
