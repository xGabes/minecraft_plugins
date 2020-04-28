package me.Leblanct.supercreative.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.apache.commons.lang.StringUtils;

import com.mysql.fabric.xmlrpc.base.Array;


import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.world.CreativeWorld;

public class WorldsDatabase 
{

	private SuperCreative _plugin;
	private Connection _connection;
	private String _host, _database, _username, _password;
	private int _port;
	
	public WorldsDatabase(SuperCreative plugin)
	{
		_plugin = plugin;
		_host = "198.245.51.96";
		_database = "db_116650";
		_username = "db_116650";
		_password = "cc60df0247";
		_port = 3306;
	}
	
	public void openConnection() throws SQLException, ClassNotFoundException 
	{
	    if (_connection != null && !_connection.isClosed())
	    {
	        return;
	    }
	 
	    synchronized (this)
	    {
	        if (_connection != null && !_connection.isClosed())
	        {
	            return;
	        } 
	        Class.forName("com.mysql.jdbc.Driver");
	        _connection = DriverManager.getConnection("jdbc:mysql://" + this._host+ ":" + this._port + "/" + this._database, this._username, this._password);
	    }
	}
	
	public CreativeWorld load(UUID id)
	{
		CreativeWorld loaded;
		PreparedStatement state;
		PreparedStatement insert;
		
		try
		{
			openConnection();
			state = _connection.prepareStatement("SELECT * FROM `world_data` WHERE owner=?;");
			state.setString(1, id.toString());
			
			ResultSet result = state.executeQuery();
			if(result.next())
			{
				String name = result.getString("name");
				Location loc = new Location(Bukkit.getWorld(id.toString()), result.getDouble("spawnX"),
						result.getDouble("spawnY"),
						result.getDouble("spawnZ"));
				
				List<UUID> ids = new ArrayList<UUID>();
				String[] players = result.getString("players").split(":");
				
				if(result.getString("players") != null)
				{
					if(result.getString("players").length() < 36)
					{
						
					} else
					{
						for(String toAdd : players)
						{
							ids.add(UUID.fromString(toAdd));
						}
					}
					
				} else
				{
					
				}
				
				loaded = new CreativeWorld(id, name, loc, ids);
				
				return loaded;
				
			} 
			else
			{
				
				insert = _connection.prepareStatement("INSERT INTO `world_data` (owner, name, spawnX, spawnY, spawnZ, players) VALUES (?,?,?,?,?,?);");
				insert.setString(1, id.toString());
				insert.setString(2, "");
				insert.setDouble(3, 0);
				insert.setDouble(4, 0);
				insert.setDouble(5, 0);
				insert.setString(6, "");
				
				insert.executeUpdate();
				
				return new CreativeWorld(id, null, null, new ArrayList<UUID>());
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("WorldsDatabase returning null in load() method.");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
		return null;
	}
	
	public void upload(UUID id)
	{
		PreparedStatement upload;
		
		try
		{
			CreativeWorld toUp = _plugin.getClientManager().getClient(id).getWorld();
			openConnection();
			
			upload = _connection.prepareStatement("UPDATE world_data SET name=?, spawnX=?, spawnY=?, spawnZ=?, players=? WHERE owner=?;");
			if(toUp.getName() == null)
			{
				upload.setString(1, "");
			} else
			{
				upload.setString(1, toUp.getName());
			}
			
			if(toUp.getSpawn() == null)
			{
				upload.setDouble(2, 0);
				upload.setDouble(3, 0);
				upload.setDouble(4, 0);
			} else
			{
				upload.setDouble(2, toUp.getSpawn().getX());
				upload.setDouble(3, toUp.getSpawn().getY());
				upload.setDouble(4, toUp.getSpawn().getZ());
			}
			
			if(toUp.getAllowed().isEmpty())
			{
				upload.setString(5, "");
				
			}else
			{
				upload.setString(5, StringUtils.join(toUp.getAllowed(), ":"));
			}
			
			upload.setString(6, id.toString());
			upload.executeUpdate();
			
			System.out.println("Uploaded " + id.toString());
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
