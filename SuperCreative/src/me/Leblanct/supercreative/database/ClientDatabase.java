package me.Leblanct.supercreative.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import me.Leblanct.supercreative.SuperCreative;
import me.Leblanct.supercreative.client.Client;
import me.Leblanct.supercreative.client.Rank;
import me.Leblanct.supercreative.world.CreativeWorld;

public class ClientDatabase 
{

	private SuperCreative _plugin;
	private Connection _connection;
	private String _host, _database, _username, _password;
	private int _port;
	
	public ClientDatabase(SuperCreative plugin)
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
	
	public Client load(UUID id)
	{
		
		Client loaded;
		PreparedStatement state;
		PreparedStatement insert;
		
		try
		{
			openConnection();
			state = _connection.prepareStatement("SELECT * FROM `client_data` WHERE uuid=?;");
			state.setString(1, id.toString());
			
			ResultSet result = state.executeQuery();
			
			if(result.next())
			{
				Rank rank = Rank.valueOf(result.getString("rank"));
				CreativeWorld world = _plugin.getWorldsBase().load(id);
				
				loaded = new Client(id, rank, world);
				return loaded;
				
			} else
			{
				insert = _connection.prepareStatement("INSERT INTO `client_data` (uuid, rank) values (?,?);");
				insert.setString(1, id.toString());
				insert.setString(2, Rank.MEMBER.toString());
				
				insert.executeUpdate();
				CreativeWorld world = _plugin.getWorldsBase().load(id);
				return new Client(id, Rank.MEMBER, world);
				
			}
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("Error: Couldn't load player ClientDatabase load() method");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!");
		return null;
	}
	
	public void upload(UUID id)
	{
		PreparedStatement upload;
		
		try
		{
			Client toUp = _plugin.getClientManager().getClient(id);
			openConnection();
			upload = _connection.prepareStatement("UPDATE client_data SET rank=? WHERE uuid=?;");
			upload.setString(1, toUp.getRank().toString());
			upload.setString(2, id.toString());
			
			upload.executeUpdate();
			
			_plugin.getWorldsBase().upload(id);
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
