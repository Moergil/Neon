package sk.hackcraft.neon.events;

import java.util.ArrayList;
import java.util.List;

import sk.hackcraft.neon.Event;
import sk.hackcraft.neon.executors.ImmediateExecuter;

public class FastStaticEvent<T> implements Event<T>
{
	private static final ImmediateExecuter defaultExecuter = new ImmediateExecuter();
	
	private final ArrayList<ListenerExecutorPair<T>> pairs = new ArrayList<>(1);
	
	@Override
	public void attachListener(sk.hackcraft.neon.Event.Listener<T> listener)
	{
		attachListener(listener, defaultExecuter);
	}

	@Override
	public synchronized void attachListener(Event.Listener<T> listener, Event.Executer executer)
	{
		ListenerExecutorPair<T> pair = new ListenerExecutorPair<>(listener, executer);
		
		pairs.add(pair);
	}

	@Override
	public synchronized void removeListener(Event.Listener<T> listener)
	{
		for (int i = 0; i < pairs.size(); i++)
		{
			ListenerExecutorPair<T> pair = pairs.get(i);
			
			if (pair.getListener() == listener)
			{
				pairs.remove(i);
				break;
			}
		}
	}

	@Override
	public void fire(T params)
	{
		List<ListenerExecutorPair<T>> listenersCopy;
		
		synchronized (this)
		{
			listenersCopy = new ArrayList<>(pairs);
		}
		
		for (ListenerExecutorPair<T> pair : listenersCopy)
		{
			pair.execute(params);
		}
	}
	
	private static class ListenerExecutorPair<T>
	{
		private final Listener<T> listener;
		private final Executer executer;
		
		public ListenerExecutorPair(Event.Listener<T> listener, Event.Executer executer)
		{
			this.listener = listener;
			this.executer = executer;
		}
		
		public void execute(T params)
		{
			executer.execute(listener, params);
		}
		
		public Listener<T> getListener()
		{
			return listener;
		}
	}
}
