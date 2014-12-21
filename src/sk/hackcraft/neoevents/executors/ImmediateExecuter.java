package sk.hackcraft.neoevents.executors;

import sk.hackcraft.neoevents.Event;
import sk.hackcraft.neoevents.Event.Executer;

public class ImmediateExecuter implements Executer
{
	@Override
	public <T> void execute(Event.Listener<T> listener, T params)
	{
		listener.fired(params);
	}
}