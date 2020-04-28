package me.Leblanct.supercreative;

import org.bukkit.plugin.java.JavaPlugin;

import me.Leblanct.supercreative.client.ClientManager;
import me.Leblanct.supercreative.commands.WorldCommand;
import me.Leblanct.supercreative.database.ClientDatabase;
import me.Leblanct.supercreative.database.WorldsDatabase;
import me.Leblanct.supercreative.listeners.Global;
import me.Leblanct.supercreative.scoreboard.CreativeBoard;
import me.Leblanct.supercreative.world.CreativeGenerators;
import me.Leblanct.supercreative.world.CreativeWorldManager;
import me.Leblanct.supercreative.world.WorldMechanics;
import net.md_5.bungee.api.ChatColor;

public class SuperCreative extends JavaPlugin 
{

	private CreativeWorldManager _worldManager;
	private ClientManager _clientManager;
	private Global _globalListeners;
	private WorldMechanics _worldMech;
	private CreativeGenerators _worldGen;
	
	private WorldCommand _worldCmd;
	
	private WorldsDatabase _worldsBase;
	private ClientDatabase _clientBase;
	
	private CreativeBoard _creativeBoard;
	
	public void onEnable()
	{
		System.out.println("SUPER CREATIVE");
		System.out.println("Plugin starting up...");
		_worldManager = new CreativeWorldManager();
		System.out.println("World Manager loaded");
		_clientManager = new ClientManager();
		System.out.println("Client Manager loaded");
		_worldsBase = new WorldsDatabase(this);
		System.out.println("Worlds Database loaded");
		_clientBase = new ClientDatabase(this);
		System.out.println("Client Database loaded");
		_globalListeners = new Global(this);
		System.out.println("Global Listeners loaded");
		_worldCmd = new WorldCommand(this);
		System.out.println("World Command loaded");
		_worldMech = new WorldMechanics(this);
		System.out.println("World Mechanics loaded");
		_worldGen = new CreativeGenerators(this);
		System.out.println("Creative Generators loaded");
		_creativeBoard = new CreativeBoard(this);
		System.out.println("Creative Board loeaded");
		
		System.out.println(ChatColor.GREEN + "PLUGIN HAS BEEN SUCCESSFULLY LOADED");
	}
	
	public CreativeWorldManager getWorldManager()
	{
		return _worldManager;
	}
	
	public ClientManager getClientManager()
	{
		return _clientManager;
	}
	
	public WorldsDatabase getWorldsBase()
	{
		return _worldsBase;
	}
	
	public ClientDatabase getClientBase()
	{
		return _clientBase;
	}
	
	public CreativeGenerators getGenerator()
	{
		return _worldGen;
	}
}
