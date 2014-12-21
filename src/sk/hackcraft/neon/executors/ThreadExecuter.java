package sk.hackcraft.neon.executors;

import sk.hackcraft.neon.Event;
import sk.hackcraft.neon.Event.Listener;

public class ThreadExecuter implements Event.Executer
{
	private static final ThreadExecuter INSTANCE = new ThreadExecuter();
	
	public static ThreadExecuter getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public <T> void execute(Listener<T> listener, T params)
	{
		Runnable r =  () -> {
			listener.fired(params);
		};

		new Thread(r).start();
	}
}
