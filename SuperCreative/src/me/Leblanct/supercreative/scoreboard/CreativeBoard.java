package me.Leblanct.supercreative.scoreboard;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.client.Client;
import me.Leblanct.supercreative.client.Rank;
import net.md_5.bungee.api.ChatColor;

public class CreativeBoard implements Listener
{

	private SuperCreative _plugin;
	private ScoreboardAPI _scoreboard;
	private Map<Rank, Team> _rankTeams;
	
	public CreativeBoard(SuperCreative plugin)
	{
		_plugin = plugin;
		
		_scoreboard = new ScoreboardAPI(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Super"
				+ ChatColor.WHITE.toString() + ChatColor.BOLD +" Creative",
				Arrays.asList(
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""));
		
		_rankTeams = new HashMap<Rank, Team>();
		for (Rank rank : Rank.values())
		{
			Team team = _scoreboard.getScoreboard().registerNewTeam(
					rank.name());
			team.setPrefix(rank.getColor().toString() + ChatColor.BOLD + "> " + ChatColor.RESET + rank.getColor());
			_rankTeams.put(rank, team);
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		
		update();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		_scoreboard.showPlayer(e.getPlayer());
		_rankTeams.get(_plugin.getClientManager().getClient(e.getPlayer().getUniqueId()).getRank()).addPlayer(e.getPlayer());
	}
	
	public void update()
	{
		new BukkitRunnable()
		{

			@Override
			public void run()
			{
				for(Player p : Bukkit.getServer().getOnlinePlayers())
				{
					Client client = _plugin.getClientManager().getClient(p.getUniqueId());
					
					_scoreboard.setLine(ChatColor.DARK_GRAY + "" + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), 0);
					
					if(client.getRank() == Rank.MEMBER)
					{
						_scoreboard.setLineForPlayer(p, "You are a", 2);
						_scoreboard.setLineForPlayer(p, ChatColor.YELLOW + client.getRank().getRankName(), 3);
						_scoreboard.setLineForPlayer(p, "Consider helping us", 5);
						_scoreboard.setLineForPlayer(p, "by" + ChatColor.YELLOW + " purchasing" + ChatColor.WHITE + " a rank", 6);
					} else
					{
						_scoreboard.setLineForPlayer(p, "You are a", 2);
						_scoreboard.setLineForPlayer(p, ChatColor.YELLOW + client.getRank().getRankName(), 3);
						_scoreboard.setLineForPlayer(p, "Thank you for", 5);
						_scoreboard.setLineForPlayer(p, "your support!", 6);
					}
					
					_scoreboard.setLine(ChatColor.YELLOW.toString() + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + " builders online", 8);
				
					Team toLeave = null;
					Team toEnter = null;
					for (Entry<Rank, Team> entry : _rankTeams.entrySet())
					{
						if (entry.getKey().equals(client.getRank())
								&& !entry.getValue().hasPlayer(p))
						{
							toEnter = entry.getValue();
						}
						else if (!entry.getKey().equals(client.getRank())
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
			
		}.runTaskTimer(_plugin, 0, 5L);
	}
}
