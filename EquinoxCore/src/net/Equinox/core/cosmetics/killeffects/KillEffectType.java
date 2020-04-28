package net.Equinox.core.cosmetics.killeffects;

public enum KillEffectType 
{

	BLOOD_HELIX("Blood Helix", 300),
	FLAME_HELIX("Flame Helix", 300),
	SNOW_HELIX("Snow Helix", 300),
	FLAME_RAGE("Flame Rage", 500),
	ANGELIC_WAVES("Angelic Waves", 500);
	
	private String _name;
	private int _price;
	
	KillEffectType(String name, int price)
	{
		_name = name;
		_price = price;
	}
	
	public String getKillEffectName()
	{
		return _name;
	}
	
	public int getFullPrice()
	{
		return _price;
	}
	
	public int getVipPrice()
	{
		return _price - ((_price * 15) / 100);
	}
	
	public int getProPrice()
	{
		return _price - ((_price * 25) / 100);
	}
}
