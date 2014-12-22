package sk.hackcraft.neon.util;

public interface EnvironmentTime
{
	/**
	 * <p>Returns actual time value. Returned value is monotonic, so value of every
	 * next call will be equals or more than previous value.</p>
	 * 
	 * <p>This method is thread safe.</p>
	 * 
	 * @return actual time value
	 */
	long getTime();
}
