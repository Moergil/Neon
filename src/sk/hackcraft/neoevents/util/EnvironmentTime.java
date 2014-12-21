package sk.hackcraft.neoevents.util;

public interface EnvironmentTime
{
	// TODO documentation should guarantee that returned time is monotonic
	long getTime();
}
