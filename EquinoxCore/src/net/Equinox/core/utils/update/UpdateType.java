package net.Equinox.core.utils.update;

import net.Equinox.core.utils.UtilsTime;

public enum UpdateType
{
	
	DAY(86400000L),
	HOUR(3600000L),
	MINUTE(60000L),
	SECOND(1000L),
	HALF_SECOND(500L),
	QUARTER_SECOND(250L),
	FAST(150L),
	FASTER(100L),
	TICK(49L);

	private long _time;
	private long _last;

	private UpdateType(long time)
	{
		_time = time;
		_last = System.currentTimeMillis();
	}

	public boolean elapsed()
	{
		if (UtilsTime.elapsed(_last, _time))
		{
			_last = System.currentTimeMillis();
			return true;
		}
		return false;
	}

}
