package sk.hackcraft.neoevents.executors;

import sk.hackcraft.neon.Event.Executer;
import sk.hackcraft.neon.Event.Listener;
import sk.hackcraft.neon.MessageQueue;

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
