package net.Equinox.typhoon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import net.Equinox.core.Core;
import net.Equinox.core.client.Client;
import net.Equinox.core.client.Rank;
import net.Equinox.core.scoreboard.ScoreboardAPI;
import net.Equinox.core.utils.update.UpdateEvent;
import net.Equinox.core.utils.update.UpdateType;
import net.Equinox.typhoon.game.TyphoonState;
import net.Equinox.typhoon.game.kits.TyphoonKits;

public class TyphoonBoard implements Listener
{

	private Typhoon _typhoon;
	private Core _core;
	private ScoreboardAPI _scoreboard;
	private Map<TyphoonKits, Team> _kitTeam;
	
	public TyphoonBoard(Typhoon plugin)
	{
		_typhoon = plugin;
		_core = Core.getPlugin(Core.class);
		
		_scoreboard = new ScoreboardAPI(ChatColor.RED.toString() + ChatColor.BOLD + "Typhoon",
				Arrays.asList("date", "",
						ChatColor.WHITE + "Map: n/a",
						ChatColor.WHITE + "Players: n/a", "",
						ChatColor.WHITE + "Kit: n/a", "",
						ChatColor.WHITE + "Coins: n/a",
						ChatColor.WHITE + "Cubits: n/a", "",
						ChatColor.GRAY + "equinoxnetwork.net"));
		
		_kitTeam = new HashMap<TyphoonKits, Team>();
		
		for (TyphoonKits kit : TyphoonKits.values())
		{
			Team team = _scoreboard.getScoreboard().registerNewTeam(
					kit.getKitName());
			
			team.setPrefix(kit.getColor() + "[" + kit.getKitName() + "]" + ChatColor.WHITE + " ");
			_kitTeam.put(kit, team);
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public Map<TyphoonKits, Team> getKitTeam()
	{
		return _kitTeam;
	}
	
	public ScoreboardAPI getScoreboard()
	{
		return _scoreboard;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		_scoreboard.showPlayer(e.getPlayer());
	}
	
	public boolean sizeCheck()
	{
		if(_scoreboard.getLines().size() == 7)
		{
			return true;
		}
		
		return false;
	}
	
	private String _frame0 = ChatColor.WHITE.toString() + ChatColor.BOLD + "" + ChatColor.RED.toString() + ChatColor.BOLD + "T" + ChatColor.WHITE.toString() + ChatColor.BOLD + "yphoon";
	private String _frame1 = ChatColor.WHITE.toString() + ChatColor.BOLD + "T" + ChatColor.RED.toString() + ChatColor.BOLD + "y" + ChatColor.WHITE.toString() + ChatColor.BOLD + "phoon";
	private String _frame2 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Ty" + ChatColor.RED.toString() + ChatColor.BOLD + "p" + ChatColor.WHITE.toString() + ChatColor.BOLD + "hoon";
	private String _frame3 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typ" + ChatColor.RED.toString() + ChatColor.BOLD + "h" + ChatColor.WHITE.toString() + ChatColor.BOLD + "oon";
	private String _frame4 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typh" + ChatColor.RED.toString() + ChatColor.BOLD + "o" + ChatColor.WHITE.toString() + ChatColor.BOLD + "on";
	private String _frame5 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typho" + ChatColor.RED.toString() + ChatColor.BOLD + "o" + ChatColor.WHITE.toString() + ChatColor.BOLD + "n";
	private String _frame6 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typhoo" + ChatColor.RED.toString() + ChatColor.BOLD + "n" + ChatColor.WHITE.toString() + ChatColor.BOLD + "";
	private String _frame7 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typho" + ChatColor.RED.toString() + ChatColor.BOLD + "o" + ChatColor.WHITE.toString() + ChatColor.BOLD + "n";
	private String _frame8 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typh" + ChatColor.RED.toString() + ChatColor.BOLD + "o" + ChatColor.WHITE.toString() + ChatColor.BOLD + "on";
	private String _frame10 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Typ" + ChatColor.RED.toString() + ChatColor.BOLD + "h" + ChatColor.WHITE.toString() + ChatColor.BOLD + "oon";
	private String _frame11 = ChatColor.WHITE.toString() + ChatColor.BOLD + "Ty" + ChatColor.RED.toString() + ChatColor.BOLD + "p" + ChatColor.WHITE.toString() + ChatColor.BOLD + "hoon";
	private String _frame12 = ChatColor.WHITE.toString() + ChatColor.BOLD + "T" + ChatColor.RED.toString() + ChatColor.BOLD + "y" + ChatColor.WHITE.toString() + ChatColor.BOLD + "phoon";
	private String _frame13 = ChatColor.WHITE.toString() + ChatColor.BOLD + "" + ChatColor.RED.toString() + ChatColor.BOLD + "T" + ChatColor.WHITE.toString() + ChatColor.BOLD + "yphoon";
	
	private List<String> _frames = new ArrayList<String>();
	{
		_frames.add(_frame0);
		_frames.add(_frame1);
		_frames.add(_frame2);
		_frames.add(_frame3);
		_frames.add(_frame4);
		_frames.add(_frame5);
		_frames.add(_frame6);
		_frames.add(_frame7);
		_frames.add(_frame8);
		_frames.add(_frame10);
		_frames.add(_frame11);
		_frames.add(_frame12);
		_frames.add(_frame13);}
	
	private int _iterator = 0;
	
	@EventHandler
	public void onUpdate(UpdateEvent e)
	{
		if(e.getType() == UpdateType.HALF_SECOND)
		{
			for(Player p : Bukkit.getServer().getOnlinePlayers())
			{
				if(_typhoon.game.hasStarted())
				{
					_scoreboard.setLine("", 1);
					_scoreboard.setLine("Map: " + ChatColor.AQUA + _typhoon.mapManager.getSelected().getName(), 2);
					_scoreboard.setLine("Alive: " + ChatColor.GREEN + _typhoon.game.getAlive().size(), 3);
					
					_scoreboard.setLineForPlayer(p, "Kit: " + ChatColor.YELLOW + _typhoon.playerManager.getTyphoonPlayer(p).getKit().getKitName(), 5);
					
					_scoreboard.setLineForPlayer(p, "Lives: " + ChatColor.RED + _typhoon.playerManager.getTyphoonPlayer(p).getLives(), 7);

					_scoreboard.setLineForPlayer(p, "Game end: " + ChatColor.GOLD + _typhoon.game.tillEnd(), 8);
					
				} else
				{
					_scoreboard.setLine(ChatColor.GRAY + "" + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), 0);
					_scoreboard.setLine("Map: " + ChatColor.AQUA + _typhoon.mapManager.getSelected().getName(), 2);
					_scoreboard.setLine("Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), 3);
					
					_scoreboard.setLineForPlayer(p, "Kit: " + ChatColor.YELLOW + _typhoon.playerManager.getTyphoonPlayer(p).getKit().getKitName(), 5);
					
					_scoreboard.setLineForPlayer(p, "Coins: " + ChatColor.BLUE + _core.clientManager.getClient(p.getUniqueId()).getCoins(), 7);
					_scoreboard.setLineForPlayer(p, "Cubits: " + ChatColor.BLUE + _core.clientManager.getClient(p.getUniqueId()).getCubits(), 8);
					
				}
				
				Team toLeave = null;
				Team toEnter = null;
				
				for (Entry<TyphoonKits, Team> entry : _kitTeam.entrySet())
				{
					if (entry.getKey().equals(_typhoon.playerManager.getTyphoonPlayer(p).getKit())
							&& !entry.getValue().hasPlayer(p))
					{
						toEnter = entry.getValue();
					}
					else if (!entry.getKey().equals(_typhoon.playerManager.getTyphoonPlayer(p).getKit())
							&& entry.getValue().hasPlayer(p))
					{
						toLeave = entry.getValue();
					}
				}
				if (toLeave != null)
				{
					toLeave.removePlayer(p);
				}
				if (toEnter != null)
				{
					toEnter.addPlayer(p);
				}
			}
		} 
		else if(e.getType() == UpdateType.FASTER)
		{
			_iterator++;
			if(_iterator > 12)
			{
				_iterator = 0;
			}
			
			_scoreboard.setTopLine(_frames.get(_iterator));
		}
	}
	
}
