package sk.hackcraft.neoevents.executors;

import sk.hackcraft.neoevents.Event.Executer;
import sk.hackcraft.neoevents.Event.Listener;
import sk.hackcraft.neoevents.MessageQueue;

public class MessageQueueExecuter implements Executer
{
	private final MessageQueue messageQueue;
	
	public MessageQueueExecuter(MessageQueue messageQueue)
	{
		this.messageQueue = messageQueue;
	}
	
	@Override
	public <T> void execute(Listener<T> listener, T params)
	{
		messageQueue.post(() -> listener.fired(params));
	}
}
