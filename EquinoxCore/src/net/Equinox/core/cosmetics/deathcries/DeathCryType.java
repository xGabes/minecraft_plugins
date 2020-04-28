package net.Equinox.core.cosmetics.deathcries;

public enum DeathCryType
{
	MINERAL("Mineral Death", 200),
	SWEET("Sweet Death", 200),
	VEGGIE("Veggie Death", 200),
	EXPLODE("Exploding Death", 200),
	FIERY("Fiery Death", 300);
	
	private String _name;
	private int _price;
	
	DeathCryType(String name, int price)
	{
		_name = name;
		_price = price;
	}
	
	public String getDeathCryName()
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
