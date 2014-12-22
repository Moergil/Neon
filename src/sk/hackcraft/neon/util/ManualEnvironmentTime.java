package sk.hackcraft.neon.util;

/**
 * <p>
 * {@link EnvironmentTime} implementation, where time updates are called
 * manually. Main purpose of this class is for debugging and cases, where time
 * doesn't depend on real time.
 * </p>
 * 
 * <p>
 * Updating time value of this implementation is not thread safe.
 * </p>
 */
public final class ManualEnvironmentTime implements EnvironmentTime
{
	private volatile long time;

	/**
	 * <p>
	 * Adds passed value to actual time.
	 * </p>
	 * 
	 * <p>
	 * This method is not thread safe.
	 * </p>
	 * 
	 * @param value
	 *            value to add to current time
	 */
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
