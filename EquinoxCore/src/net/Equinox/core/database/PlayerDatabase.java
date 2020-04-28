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

public class PlayerDatabase 
{

	private Core _core;
	private Connection _connection;
	private String _host;
	private int _port;
	private String _database;
	private String _username;
	private String _password;
	
	public PlayerDatabase(Core plugin)
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
	
	public Client load(UUID id)
	{
		Client loadedClient;
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
			createTable = _connection.prepareStatement("CREATE TABLE IF NOT EXISTS account_data (uuid VARCHAR(36), rank VARCHAR(40), infinitum BOOLEAN, icon VARCHAR(40), coins LONG, cubits LONG, PRIMARY KEY (uuid));");
			statement = _connection.prepareStatement("SELECT * FROM `account_data` WHERE uuid=?;");
			statement.setString(1, id.toString());
			createTable.executeUpdate();
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Rank rank = Rank.valueOf(result.getString("rank"));
				Boolean infinitum = result.getBoolean("infinitum");
				InfinitumIcon icon = InfinitumIcon.valueOf(result.getString("icon"));
				Long cubit = result.getLong("coins");
				Long tickets = result.getLong("cubits");
				
				System.out.println("Fetched Player Info [" + id.toString() + ", " 
				+ rank.getName() + ", "
				+ infinitum.toString() + ", "
				+ icon.getName() + ", "
				+ cubit + ", " 
				+ tickets 
				+ "] from database!");
				loadedClient = new Client(id, rank, infinitum, icon, cubit, tickets);
				if(loadedClient != null)
				{
					return loadedClient;
				}
			} else
			{
				insertTable = _connection.prepareStatement("INSERT INTO `account_data` (uuid,rank,infinitum,icon,coins,cubits) values (?,?,?,?,?,?);");
				insertTable.setString(1, id.toString());
				insertTable.setString(2, Rank.MEMBER.toString());
				insertTable.setBoolean(3, false);
				insertTable.setString(4, InfinitumIcon.NONE.toString());
				insertTable.setLong(5, 200);
				insertTable.setLong(6, 0);
				insertTable.executeUpdate();
				result.close();
				result = statement.executeQuery();
				if(result.next())
				{
					System.out.println("This player is not in the database, adding: ");
					System.out.println("Player Info [" + id.toString() + ", " + Rank.MEMBER.getName() + ", " + "false" + ", "
							+ InfinitumIcon.NONE.getName() + ", " + 200 + ", " + 0 + "]");
					
					return new Client(id, Rank.MEMBER, false, InfinitumIcon.NONE, 200, 0);
					
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
		System.out.println("Class: PlayerDatabase");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return null;
	}
	
	public Client load(OfflinePlayer player)
	{
		return load(player.getUniqueId());
	}
	
	public void upload(UUID id)
	{
		PreparedStatement insertTable = null;
		boolean opened = true;
		
		try
		{
			Client toUpload = _core.clientManager.getClient(id);
			opened = (getConnection() != null && !getConnection().isClosed());
			if(!opened)
			{
				openConnection();
				System.out.println("Connection to the database established successfully.");
				System.out.println("The Database Connection is now opened!");
			}
			insertTable = _connection.prepareStatement("INSERT INTO `account_data` (uuid,rank,infinitum,icon,coins,cubits) values (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE rank=?, infinitum=?, icon=?, coins=?, cubits=?;");
			insertTable.setString(1, toUpload.getUUID().toString());
			insertTable.setString(2, toUpload.getRank().toString());
			insertTable.setBoolean(3, toUpload.isInfinitum());
			insertTable.setString(4, toUpload.getIcon().toString());
			insertTable.setLong(5, toUpload.getCoins());
			insertTable.setLong(6, toUpload.getCubits());
			insertTable.setString(7, toUpload.getRank().toString());
			insertTable.setBoolean(8, toUpload.isInfinitum());
			insertTable.setString(9, toUpload.getIcon().toString());
			insertTable.setLong(10, toUpload.getCoins());
			insertTable.setLong(11, toUpload.getCubits());
			boolean success = insertTable.executeUpdate() != 0;
			if(success)
			{
				System.out.println("Player is leaving, uploading them to database...");
				System.out.println("Successfully uploaded " + id + " to database");
				System.out.println("Player Info [" + id.toString() + ", " + toUpload.getRank().toString() + ", " + toUpload.isInfinitum() + ", " + toUpload.getIcon().getName() +  ", " + toUpload.getCoins() + ", " + toUpload.getCubits() + "]");
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
				Client toUpload = _core.clientManager.getClient(online.getUniqueId());
				
				/*
				if(online == null)
				{
					continue;
				}
				*/
				
				insertTable = _connection.prepareStatement("INSERT INTO `account_data` (uuid,rank,infinitum,icon,coins,cubits) values (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE rank=?, infinitum=?, icon=?, coins=?, cubits=?;");
				insertTable.setString(1, toUpload.getUUID().toString());
				insertTable.setString(2, toUpload.getRank().toString());
				insertTable.setBoolean(3, toUpload.isInfinitum());
				insertTable.setString(4, toUpload.getIcon().toString());
				insertTable.setLong(5, toUpload.getCoins());
				insertTable.setLong(6, toUpload.getCubits());
				insertTable.setString(7, toUpload.getRank().toString());
				insertTable.setBoolean(8, toUpload.isInfinitum());
				insertTable.setString(9, toUpload.getIcon().toString());
				insertTable.setLong(10, toUpload.getCoins());
				insertTable.setLong(11, toUpload.getCubits());
				boolean success = insertTable.executeUpdate() != 0;
				if(success)
				{
					System.out.println("Player is leaving, uploading them to database...");
					System.out.println("Successfully uploaded " + toUpload.getUUID() + " to database");
					System.out.println("Player Info [" + toUpload.getUUID().toString()+ ", " + toUpload.getRank().toString() + ", " + toUpload.isInfinitum() + ", " + toUpload.getIcon().getName() +  ", " + toUpload.getCoins() + ", " + toUpload.getCubits() + "]");
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
