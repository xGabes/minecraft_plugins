package net.Equinox.core.client.infinitum;

public enum InfinitumIcon
{
	NONE("NULL", "null"),
	STAR("Star", Character.toString((char) 10022)),
	COMPASS("Compass", Character.toString((char) 10050)),
	HEART("Heart", Character.toString((char) 10084)),
	MUSIC("Music", Character.toString((char) 9835)),
	SNOWFLAKE("Snowflake", Character.toString((char) 10052)),
	RECYCLING("Recycling", Character.toString((char) 267)),
	BATTERY("Battery", Character.toString((char) 236));
	
	private String _name;
	private String _char;
	
	InfinitumIcon(String name, String charId)
	{
		_name = name;
		_char = charId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getChar()
	{
		return _char;
	}
}
