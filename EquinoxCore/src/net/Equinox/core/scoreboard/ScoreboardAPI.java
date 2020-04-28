package net.Equinox.core.scoreboard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore.EnumScoreboardAction;

public class ScoreboardAPI
{

	private Scoreboard _scoreboard;
	private Objective _objective;
	private List<Score> _lines;
	private Map<Player, Map<Integer, String>> _playerLines;

	public ScoreboardAPI(String topLine, List<String> lines)
	{
		_lines = new LinkedList<Score>();
		_playerLines = new HashMap<Player, Map<Integer, String>>();
		_scoreboard = Bukkit.getServer().getScoreboardManager()
				.getNewScoreboard();
		_objective = _scoreboard.registerNewObjective("apiobj", "dummy");
		_objective.setDisplayName(topLine);
		_objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		int currentLineNum = lines.size();
		for (String s : lines)
		{
			Score sc = _objective.getScore(createNonDuplicate(s));
			sc.setScore(currentLineNum--);
			_lines.add(sc);
		}
	}

	public String getTopLine()
	{
		return _objective.getDisplayName();
	}

	public void setTopLine(String s)
	{
		_objective.setDisplayName(s);
	}

	public String getLine(int index)
	{
		if (_lines.size() - 1 < index)
		{
			return null;
		}
		return StringUtils.stripEnd(_lines.get(index).getEntry(),
				ChatColor.RESET.toString());
	}

	public String getLineNoStrip(int index)
	{
		if (_lines.size() - 1 < index)
		{
			return null;
		}
		return _lines.get(index).getEntry();
	}

	public void setLine(String s, int index)
	{
		if (getLine(index).equals(s))
		{
			return;
		}
		if (_lines.size() - 1 >= index)
		{
			Score old = _lines.get(index);
			_scoreboard.resetScores(old.getEntry());
			Score newScore = _objective.getScore(createNonDuplicate(s));
			newScore.setScore(_lines.size() - index);
			_lines.set(index, newScore);
		}
		else if (index > _lines.size() - 1)
		{
			int numEmptyScores = index - _lines.size();
			int oldListSize = _lines.size();
			for (int i = 0; i < numEmptyScores; i++)
			{
				Score empty = _objective.getScore(createNonDuplicate(""));
				empty.setScore((numEmptyScores + 1) - i);
				_lines.add(empty);
			}
			Score added = _objective.getScore(createNonDuplicate(s));
			added.setScore(1);
			_lines.add(added);
			for (int i = 0; i < oldListSize; i++)
			{
				_lines.get(i).setScore(_lines.size() - i);
			}
		}
	}

	public void addNextLine(String s)
	{
		Score next = _objective.getScore(createNonDuplicate(s));
		_lines.add(next);
		int currentLineNum = _lines.size();
		for (Score sc : _lines)
		{
			sc.setScore(currentLineNum--);
		}
	}

	public void removeLine(int index)
	{
		_scoreboard.resetScores(_lines.remove(index).getEntry());
		int currentLineNum = _lines.size();
		for (Score sc : _lines)
		{
			sc.setScore(currentLineNum--);
		}
	}

	public void swapLines(int index1, int index2)
	{
		Score score1 = _lines.get(index1);
		Score score2 = _lines.get(index2);
		_scoreboard.resetScores(score1.getEntry());
		_scoreboard.resetScores(score1.getEntry());
		Score newScore1 = _objective.getScore(score2.getEntry());
		Score newScore2 = _objective.getScore(score1.getEntry());
		newScore1.setScore(score2.getScore());
		newScore2.setScore(score1.getScore());
		_lines.set(index1, newScore2);
		_lines.set(index2, newScore1);
	}

	public void resetLines()
	{
		for (Score sc : _lines)
		{
			_scoreboard.resetScores(sc.getEntry());
		}
		for (Entry<Player, Map<Integer, String>> entry : _playerLines
				.entrySet())
		{
			for (String s : entry.getValue().values())
			{
				PacketPlayOutScoreboardScore removeOld = new PacketPlayOutScoreboardScore(
						s);
				try
				{
					Field objectiveField = removeOld.getClass()
							.getDeclaredField("b");
					objectiveField.setAccessible(true);
					objectiveField.set(removeOld, "apiobj");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				((CraftPlayer) entry.getKey()).getHandle().playerConnection
						.sendPacket(removeOld);
			}
		}
		_playerLines.clear();
		_lines.clear();
	}

	public void setLines(List<String> lines)
	{
		resetLines();
		int currentLineNum = lines.size();
		for (String s : lines)
		{
			Score sc = _objective.getScore(createNonDuplicate(s));
			sc.setScore(currentLineNum--);
			_lines.add(sc);
		}
	}

	private boolean isDuplicate(String s)
	{
		for (Score sc : _lines)
		{
			if (sc.getEntry().equals(s))
			{
				return true;
			}
		}
		return false;
	}

	private String createNonDuplicate(String s)
	{
		StringBuilder sb = new StringBuilder(s);
		while (isDuplicate(sb.toString()))
		{
			sb.append(ChatColor.RESET.toString());
		}
		return sb.toString();
	}

	public List<String> getLines()
	{
		List<String> data = new LinkedList<String>();
		for (Score sc : _lines)
		{
			data.add(StringUtils.stripEnd(sc.getEntry(),
					ChatColor.RESET.toString()));
		}
		return data;
	}

	public List<String> getLinesNoStrip()
	{
		List<String> data = new LinkedList<String>();
		for (Score sc : _lines)
		{
			data.add(sc.getEntry());
		}
		return data;
	}

	public void compact()
	{
		List<Score> toRemove = new ArrayList<Score>();
		for (Score sc : _lines)
		{
			if (ChatColor.stripColor(sc.getEntry()).trim().isEmpty())
			{
				_scoreboard.resetScores(sc.getEntry());
				toRemove.add(sc);
			}
		}
		_lines.removeAll(toRemove);
		int currentLineNum = _lines.size();
		for (Score sc : _lines)
		{
			sc.setScore(currentLineNum--);
		}
	}

	public void setLineForPlayer(Player p, String line, int index)
	{
		if (_lines.size() > index)
		{
			PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(
					line);
			String kitLine = getLineForPlayer(index, p);
			if (kitLine != null)
			{
				if (kitLine.equals(line))
				{
					return;
				}
				removeLineForPlayer(kitLine, p);
			}
			else
			{
				removeLineForPlayer(_lines.get(index).getEntry(), p);
			}
			try
			{
				Field objectiveField = packet.getClass().getDeclaredField("b");
				objectiveField.setAccessible(true);
				objectiveField.set(packet, "apiobj");
				Field scoreField = packet.getClass().getDeclaredField("c");
				scoreField.setAccessible(true);
				scoreField.setInt(packet, _lines.size() - index);
				Field actionField = packet.getClass().getDeclaredField("d");
				actionField.setAccessible(true);
				actionField.set(packet, EnumScoreboardAction.CHANGE);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (_playerLines.containsKey(p))
			{
				Map<Integer, String> lines = _playerLines.get(p);
				lines.put(index, line);
			}
			else
			{
				Map<Integer, String> lines = new HashMap<Integer, String>();
				lines.put(index, line);
				_playerLines.put(p, lines);
			}
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void removeLineForPlayer(String s, Player p)
	{
		PacketPlayOutScoreboardScore removeOld = new PacketPlayOutScoreboardScore(
				s);
		try
		{
			Field objectiveField = removeOld.getClass().getDeclaredField("b");
			objectiveField.setAccessible(true);
			objectiveField.set(removeOld, "apiobj");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(removeOld);
	}

	public String getLineForPlayer(int index, Player p)
	{
		if (_playerLines.containsKey(p))
		{
			Map<Integer, String> lines = _playerLines.get(p);
			if (lines.containsKey(index))
			{
				return lines.get(index);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	public void showPlayer(Player p)
	{
		p.setScoreboard(_scoreboard);
	}

	public void hidePlayer(Player p)
	{
		if (_scoreboard.equals(p.getScoreboard()))
		{
			p.setScoreboard(Bukkit.getServer().getScoreboardManager()
					.getNewScoreboard());
		}
	}

	public Scoreboard getScoreboard()
	{
		return _scoreboard;
	}

}