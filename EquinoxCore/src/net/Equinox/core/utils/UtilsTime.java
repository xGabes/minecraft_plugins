package net.Equinox.core.utils;

public class UtilsTime 
{

	public static boolean elapsed(long start, long time)
	{
		return System.currentTimeMillis() - start > time;
	}
}
