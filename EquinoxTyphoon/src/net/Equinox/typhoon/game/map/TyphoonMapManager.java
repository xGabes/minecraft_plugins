package net.Equinox.typhoon.game.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.Equinox.typhoon.Typhoon;

public class TyphoonMapManager 
{

	private Typhoon _typhoon;
	private Map<Integer, TyphoonMap> _maps;
	private TyphoonMap _selected;
	
	public TyphoonMapManager(Typhoon plugin)
	{
		_typhoon = plugin;
		_maps = new HashMap<Integer, TyphoonMap>();
		
	}
	
	public void loadMap(Integer id, String name)
	{
		TyphoonMap map = new TyphoonMap(name,
				new Location(Bukkit.getServer().getWorld(name + "Map"),
						_typhoon.config.getDouble("maps." + name + ".x"),
						_typhoon.config.getDouble("maps." + name + ".y"),
						_typhoon.config.getDouble("maps." + name + ".z")));
		
		_maps.put(id, map);
	}
	
	public TyphoonMap getMap(int id)
	{
		return _maps.get(id);
	}
	
	public TyphoonMap getSelected()
	{
		if(_selected == null)
		{
			Bukkit.broadcastMessage("Error loading selected map. Was a map selected?");
			return new TyphoonMap("Error", new Location(Bukkit.getWorld("world"), 100, 65, 100));
		}
		
		return _selected;
	}
	
	public void pickMap()
	{
		Random random = new Random();
		int i = random.nextInt(_maps.size() + 1);
		int sel = i == 0 ? i = 1 : i;
		
		_selected = getMap(sel);
	}
	
}
