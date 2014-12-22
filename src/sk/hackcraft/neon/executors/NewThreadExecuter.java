package sk.hackcraft.neon.executors;

import sk.hackcraft.neon.Event;
import sk.hackcraft.neon.Event.Executer;
import sk.hackcraft.neon.Event.Listener;

/**
 * <p>
 * Implementation of {@link Executer}, which is dispatching listeners on new
 * threads. For every listener call, thread is created and listener is run on
 * that thread.
 * </p>
 * 
 * <p>
 * Use {@link #getInstance()} for retrieving instance of this class.
 * </p>
 */
public final class NewThreadExecuter implements Event.Executer
{
	private static final NewThreadExecuter INSTANCE = new NewThreadExecuter();

	/**
	 * Returns instance of this class.
	 * 
	 * @return instance of this class
	 */
	public static NewThreadExecuter getInstance()
	{
		return INSTANCE;
	}

	private NewThreadExecuter()
	{
	}

	@Override
	public <T> void execute(Listener<T> listener, T params)
	{
		Runnable r = () -> {
			listener.fired(params);
		};

		new Thread(r).start();
	}
}
