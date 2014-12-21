package sk.hackcraft.neoevents.executors;

import sk.hackcraft.neon.Event;
import sk.hackcraft.neon.Event.Executer;

public class ImmediateExecuter implements Executer
{
	@Override
	public <T> void execute(Event.Listener<T> listener, T params)
	{
		listener.fired(params);
	}
}