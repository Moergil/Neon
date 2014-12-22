package sk.hackcraft.neon.executors;

import sk.hackcraft.neon.Event.Executer;
import sk.hackcraft.neon.Event.Listener;
import sk.hackcraft.neon.MessageQueue;

/**
 * Implementation of {@link Executer} which is dispatching listeners on specified {@link MessageQueue}.
 */
public final class MessageQueueExecuter implements Executer
{
	private final MessageQueue messageQueue;
	
	/**
	 * Constructs new executer backed with passed {@link MessageQueue}.
	 * @param messageQueue {@link MessageQueue} to use with this {@link Executer}
	 */
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
