package sk.hackcraft.neon.util;

import java.util.concurrent.TimeUnit;

/**
 * <p>{@link EnvironmentTime} implementation using {@link System#nanoTime()} as backend.</p>
 */
public final class RealNanoEnvironmentTime implements EnvironmentTime
{
	@Override
	public long getTime()
	{
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}
}
