package me.Leblanct.supercreative.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.utils.UtilMessages;

@SuppressWarnings("deprecation")
public class CreativeGenerators
{

	private SuperCreative _plugin;
	
	public CreativeGenerators(SuperCreative plugin)
	{
		_plugin = plugin;
	}
	
	public void createEmptyWorld(Player p)
	{
		
		UtilMessages.load("World", "Starting creation of your world!", p);
		WorldCreator w = new WorldCreator(p.getUniqueId().toString());
		
		UtilMessages.load("World", "Loading world properties...", p);
		w.environment(Environment.NORMAL);
		w.type(WorldType.FLAT);
		w.generatorSettings("0;0;0;0");
		w.generateStructures(false);
		Bukkit.getServer().createWorld(w);
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("spawnRadius", "0");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doMobSpawning", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doWeatherCycle", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("announceAdvancements", "false");
		
		Location loc = new Location(Bukkit.getWorld(p.getUniqueId().toString()), 0, 100, 0);
		Bukkit.getWorld(p.getUniqueId().toString()).getWorldBorder().setCenter(loc);
		Bukkit.getWorld(p.getUniqueId().toString()).getWorldBorder().setSize(200);
		
		loc.getBlock().setType(Material.BEDROCK);
		
		Location spawn = loc.clone();
		spawn.setY(101);
		
		CreativeWorld world = new CreativeWorld(p.getUniqueId(), p.getUniqueId().toString(), spawn, new ArrayList<UUID>());
		_plugin.getClientManager().getClient(p.getUniqueId()).setWorld(world);
		_plugin.getWorldsBase().upload(p.getUniqueId());
		UtilMessages.load("World", "World created! Teleporting you now!", p);
		
		p.teleport(spawn);
		UtilMessages.load("World", "Enjoy your new world!", p);
	}
	
	public void createIslandWorld(Player p)
	{
		UtilMessages.load("World", "Starting creation of your world!", p);
		WorldCreator w = new WorldCreator(p.getUniqueId().toString());
		
		UtilMessages.load("World", "Loading world properties...", p);
		w.environment(Environment.NORMAL);
		w.type(WorldType.FLAT);
		w.generatorSettings("0;0;0;0");
		w.generateStructures(false);
		Bukkit.getServer().createWorld(w);
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("spawnRadius", "0");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doMobSpawning", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("doWeatherCycle", "false");
		Bukkit.getServer().getWorld(p.getUniqueId().toString()).setGameRuleValue("announceAdvancements", "false");
		
		
		UtilMessages.load("World", "Loading your world template...", p);
		Location loc = new Location(Bukkit.getWorld(p.getUniqueId().toString()), 0, 100, 0);
		File file = new File("plugins/WorldEdit/schematics/island_template.schematic");
	    EditSession es = new EditSession(new BukkitWorld(Bukkit.getWorld(p.getUniqueId().toString())), 999999999);
	    
	    try
		 {
		    	
	    	    CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
			cc.paste(es, new Vector(0, 100,0), false);
			
		} catch (MaxChangedBlocksException e)
		 {
			e.printStackTrace();
		} catch (DataException e)
		 {
			e.printStackTrace();
		} catch (IOException e)
		 {
			e.printStackTrace();
		}
	    
	    Bukkit.getWorld(p.getUniqueId().toString()).getWorldBorder().setCenter(loc);
		Bukkit.getWorld(p.getUniqueId().toString()).getWorldBorder().setSize(200);
		
		Location spawn = loc.clone();
		spawn.setY(101);
		
		CreativeWorld world = new CreativeWorld(p.getUniqueId(), p.getUniqueId().toString(), spawn, new ArrayList<UUID>());
		_plugin.getClientManager().getClient(p.getUniqueId()).setWorld(world);
		_plugin.getWorldsBase().upload(p.getUniqueId());
		UtilMessages.load("World", "World created! Teleporting you now!", p);
		
		p.teleport(spawn);
		UtilMessages.load("World", "Enjoy your new world!", p);
		
	}
}
