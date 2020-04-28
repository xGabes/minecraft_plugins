package net.Equinox.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Client;
import net.Equinox.core.client.Rank;
import net.Equinox.core.client.infinitum.InfinitumIcon;
import net.Equinox.core.client.punishment.PunishedClient;
import net.Equinox.core.client.punishment.PunishmentType;

public class PunishmentDatabase 
{

	private Core _core;
	private Connection _connection;
	private String _host;
	private int _port;
	private String _database;
	private String _username;
	private String _password;
	
	public PunishmentDatabase(Core plugin)
	{
		_core = plugin;
		_host = "188.165.56.183";
		_port = 3306;
		_database = "mc38912";
		_username = "mc38912";
		_password = "5d6048f84f";
	}
	
	public synchronized void openConnection()
	{
		try 
		{
			if(_connection != null && !_connection.isClosed())
			{
				return;
			}
			
			_connection = DriverManager.getConnection("jdbc:mysql://" + _host + ":" + _port + "/" + _database, _username, _password);
			
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized Connection getConnection()
	{
		return _connection;
	}
	
	public synchronized void closeConnection()
	{
		try 
		{
			if(_connection != null && !_connection.isClosed())
			{
				return;
			}
			
			_connection.close();
			
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public PunishedClient load(UUID id)
	{
		PunishedClient loadedClient;
		PreparedStatement createTable = null;
		PreparedStatement statement = null;
		PreparedStatement insertTable = null;
		boolean opened = true;
		
		try
		{
			opened = (getConnection() != null && !getConnection().isClosed());
			if(!opened)
			{
				openConnection();
				System.out.println("Connection to the database established successfully.");
				System.out.println("The Database Connection is now opened!");
			}
			createTable = _connection.prepareStatement("CREATE TABLE IF NOT EXISTS punishment_data (uuid VARCHAR(36), punisher VARCHAR(36), type VARCHAR(5), muted BOOLEAN, muteReason VARCHAR(256), banned BOOLEAN, banReason VARCHAR(256), PRIMARY KEY (uuid));");
			statement = _connection.prepareStatement("SELECT * FROM `punishment_data` WHERE uuid=?;");
			statement.setString(1, id.toString());
			createTable.executeUpdate();
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				String punisher = result.getString("punisher");
				PunishmentType type = PunishmentType.valueOf(result.getString("type"));
				boolean muted = result.getBoolean("muted");
				String muteReason = result.getString("muteReason");
				boolean banned = result.getBoolean("banned");
				String banReason = result.getString("banReason");
				
				
				
				System.out.println("Fetched Punishment Info [" + id.toString() + ", " 
				+ punisher.toString() + ", "
				+ type.toString() + ", "
				+ muted + ", "
				+ muteReason + ", " 
				+ banned + ", "
				+ banReason
				+ "] from database!");
				loadedClient = new PunishedClient(id, punisher, type, muted, muteReason, banned, banReason);
				if(loadedClient != null)
				{
					return loadedClient;
				}
			} else
			{
				insertTable = _connection.prepareStatement("INSERT INTO `punishment_data` (uuid,punisher,type,muted,muteReason,banned,banReason) values (?,?,?,?,?,?,?);");
				insertTable.setString(1, id.toString());
				insertTable.setString(2, "");
				insertTable.setString(3, PunishmentType.NONE.toString());
				insertTable.setBoolean(4, false);
				insertTable.setString(5, "");
				insertTable.setBoolean(6, false);
				insertTable.setString(7, "");
				insertTable.executeUpdate();
				result.close();
				result = statement.executeQuery();
				if(result.next())
				{
					System.out.println("This player is not in the database, adding: ");
					System.out.println("Adding Punishment Info [" + id.toString() + ", " 
							+ "" + ", "
							+ PunishmentType.NONE.toString() + ", "
							+ "false" + ", "
							+ "" + ", " 
							+ "false" + ", "
							+ ""
							+ "] from database!");
					
					return new PunishedClient(id, "", PunishmentType.NONE, false, "", false, "");
					
				} else
				{
					throw new RuntimeException();
				}
			}
		
		} catch(SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			if(!opened)
			{
				try
				{
					_connection.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (insertTable != null)
			{
				try
				{
					insertTable.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (createTable != null)
			{
				try
				{
					createTable.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("Returning Null, this is very serious, database connection may have failed!");
		System.out.println("Class: PunishmentDatabase");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return null;
	}
	
	public PunishedClient load(OfflinePlayer player)
	{
		return load(player.getUniqueId());
	}
	
	public void upload(UUID id)
	{
		PreparedStatement insertTable = null;
		boolean opened = true;
		
		try
		{
			PunishedClient toUpload = _core.punishedManager.getPunishedClient(id);
			opened = (getConnection() != null && !getConnection().isClosed());
			if(!opened)
			{
				openConnection();
				System.out.println("Connection to the database established successfully.");
				System.out.println("The Database Connection is now opened!");
			}
			insertTable = _connection.prepareStatement("INSERT INTO `punishment_data` (uuid,punisher,type,muted,muteReason,banned,banReason) values (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE punisher=?, type=?, muted=?, muteReason=?, banned=?, banReason=?;");
			insertTable.setString(1, toUpload.getUUID().toString());
			insertTable.setString(2, toUpload.getPunisher());
			insertTable.setString(3, toUpload.getPunishmentType().toString());
			insertTable.setBoolean(4, toUpload.isMuted());
			insertTable.setString(5, toUpload.getMutedReason());
			insertTable.setBoolean(6, toUpload.isBanned());
			insertTable.setString(7, toUpload.getBannedReason());
			insertTable.setString(8, toUpload.getPunisher());
			insertTable.setString(9, toUpload.getPunishmentType().toString());
			insertTable.setBoolean(10, toUpload.isMuted());
			insertTable.setString(11, toUpload.getMutedReason());
			insertTable.setBoolean(12, toUpload.isBanned());
			insertTable.setString(13, toUpload.getBannedReason());
			boolean success = insertTable.executeUpdate() != 0;
			if(success)
			{
				System.out.println("Player is leaving, uploading them to database...");
				System.out.println("Successfully uploaded " + id + " to database");
				System.out.println("Punishment Info [" + id.toString() + ", " 
						+ toUpload.getPunisher() + ", "
						+ toUpload.getPunishmentType().toString() + ", "
						+ toUpload.isMuted() + ", "
						+ toUpload.getMutedReason() + ", " 
						+ toUpload.isBanned() + ", "
						+ toUpload.getBannedReason()
						+ "]");
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
			
		} finally 
		{
			if(!opened) {
				try {
					_connection.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if(insertTable != null) 
			{
				try
				{
					insertTable.close();
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void upload(OfflinePlayer player)
	{
		upload(player.getUniqueId());
	}
	
	public void uploadAll() throws Exception
	{
		PreparedStatement insertTable = null;
		boolean opened = true;
		try
		{
			opened = (getConnection() != null && !getConnection().isClosed());
			if(!opened)
			{
				openConnection();
				System.out.println("Connection to the database established successfully.");
				System.out.println("The Database Connection is now opened!");
			}
			getConnection().setAutoCommit(false);
			for(Player online : Bukkit.getOnlinePlayers())
			{
				PunishedClient toUpload = _core.punishedManager.getPunishedClient(online.getUniqueId());
				
				/*
				if(online == null)
				{
					continue;
				}
				*/
				
				insertTable = _connection.prepareStatement("INSERT INTO `punishment_data` (uuid,punisher,type,muted,muteReason,banned,banReason) values (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE punisher=?, type=?, muted=?, muteReason=?, banned=?, banReason=?;");
				insertTable.setString(1, toUpload.getUUID().toString());
				insertTable.setString(2, toUpload.getPunisher());
				insertTable.setString(3, toUpload.getPunishmentType().toString());
				insertTable.setBoolean(4, toUpload.isMuted());
				insertTable.setString(5, toUpload.getMutedReason());
				insertTable.setBoolean(6, toUpload.isBanned());
				insertTable.setString(7, toUpload.getBannedReason());
				insertTable.setString(8, toUpload.getPunisher().toString());
				insertTable.setString(9, toUpload.getPunishmentType().toString());
				insertTable.setBoolean(10, toUpload.isMuted());
				insertTable.setString(11, toUpload.getMutedReason());
				insertTable.setBoolean(12, toUpload.isBanned());
				insertTable.setString(13, toUpload.getBannedReason());
				boolean success = insertTable.executeUpdate() != 0;
				if(success)
				{
					System.out.println("Player is leaving, uploading them to database...");
					System.out.println("Successfully uploaded " + toUpload.getUUID() + " to database");
					System.out.println("Punishment Info [" + toUpload.toString() + ", " 
							+ toUpload.getPunisher() + ", "
							+ toUpload.getPunishmentType().toString() + ", "
							+ toUpload.isMuted() + ", "
							+ toUpload.getMutedReason() + ", " 
							+ toUpload.isBanned() + ", "
							+ toUpload.getBannedReason()
							+ "]");
				}
			}
			
		} catch(SQLException e)
		{
			e.printStackTrace();
			
		} finally 
		{
			if(!opened) {
				try {
					_connection.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if(insertTable != null) 
			{
				try
				{
					insertTable.close();
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
