package sk.hackcraft.neon.executors;

import java.util.ArrayDeque;
import java.util.Queue;

import sk.hackcraft.neon.Event.Executer;
import sk.hackcraft.neon.Event.Listener;

/**
 * Implementation of {@link Executer}, which is dispatching listeners serially
 * on background thread.</p>
 * 
 * <p>Background thread is run as daemon thread.</p>
 */
public final class ReuseThreadExecuter implements Executer
{
	private final Thread thread;

	private Queue<Runnable> tasks = new ArrayDeque<>();

	/**
	 * Construct this executer. Background thread is started directly in this constructor.
	 */
	public ReuseThreadExecuter()
	{
		thread = new Thread(new Worker());
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public <T> void execute(Listener<T> listener, T params)
	{
		synchronized (thread)
		{
			tasks.add(() -> listener.fired(params));
			thread.notify();
		}
	}

	private class Worker implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					Runnable r;

					synchronized (thread)
					{
						if (tasks.isEmpty())
						{
							thread.wait();
							continue;
						}

						r = tasks.remove();
					}

					r.run();
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
