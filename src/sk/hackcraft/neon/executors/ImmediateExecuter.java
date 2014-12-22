package sk.hackcraft.neon.executors;

import sk.hackcraft.neon.Event;
import sk.hackcraft.neon.Event.Executer;

/**
 * <p>Trivial implementation of {@link Executer}. Listener is called immediately after
 * {@link #execute(Event.Listener, Object)} method is called.</p>
 * 
 * <p>Use {@link #getInstance()} for retrieving instance of this class.</p>
 */
public final class ImmediateExecuter implements Executer
{
	private static final ImmediateExecuter INSTANCE = new ImmediateExecuter();

	/**
	 * Returns instance of this class.
	 * @return instance of this class
	 */
	public static ImmediateExecuter getInstance()
	{
		return INSTANCE;
	}

	private ImmediateExecuter()
	{
	}

	@Override
	public <T> void execute(Event.Listener<T> listener, T params)
	{
		listener.fired(params);
	}
}