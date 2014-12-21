package sk.hackcraft.neoevents;

/**
 * Execution mechanism for serially executing runnables, with option of delayed
 * execution.
 */
public interface MessageQueue
{
	/**
	 * <p>Serially executes passed runnable. This method is thread safe.</p>
	 * 
	 * @param runnable
	 *            runnable to execute
	 */
	void post(Runnable runnable);

	/**
	 * <p>Serially executes sometimes after passed delay. This method is thread safe.</p*
	 * 
	 * @param runnable
	 *            runnable to execute
	 * @param delay
	 *            delay to wait at least before executing passed runnable
	 */
	void postDelayed(Runnable runnable, long delay);
}
