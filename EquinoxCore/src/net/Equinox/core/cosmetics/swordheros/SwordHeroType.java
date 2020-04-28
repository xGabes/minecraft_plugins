package net.Equinox.core.cosmetics.swordheros;

public enum SwordHeroType
{
	
	BLOOD_HERO("Blood Hero", 700),
	FIERY_HERO("Fiery Hero", 700),
	ANGELIC_HERO("Angelic Hero", 700),
	EMERALD_HERO("Emerald Hero", 700),
	FROZEN_HERO("Frozen Hero", 700);
	
	private String _name;
	private int _price;
	
	SwordHeroType(String name, int price)
	{
		_name = name;
		_price = price;
	}
	
	public String getSwordHeroName()
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
