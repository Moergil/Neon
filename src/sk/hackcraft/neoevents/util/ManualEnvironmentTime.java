package sk.hackcraft.neoevents.util;

public class ManualEnvironmentTime implements EnvironmentTime
{
	private long time;
	
	public void add(long value)
	{
		time += value;
	}
	
	@Override
	public long getTime()
	{
		return time;
	}
}
