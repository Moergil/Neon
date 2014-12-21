package sk.hackcraft.neoevents.util;

import java.util.concurrent.TimeUnit;

public class RealNanoEnvironmentTime implements EnvironmentTime
{
	private volatile long lastValue;

	@Override
	public long getTime()
	{
		long value = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
		
		if (value < lastValue)
		{
			return lastValue;
		}
		else
		{
			lastValue = value;
			return value;
		}
	}
}
