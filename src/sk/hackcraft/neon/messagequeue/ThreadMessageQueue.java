package sk.hackcraft.neon.messagequeue;

import java.util.PriorityQueue;
import java.util.Queue;

import sk.hackcraft.neon.MessageQueue;
import sk.hackcraft.neon.util.EnvironmentTime;
import sk.hackcraft.neon.util.RealNanoEnvironmentTime;

public class ThreadMessageQueue implements MessageQueue, Runnable
{
	private final EnvironmentTime time;

	private volatile boolean running;

	private final Queue<MessageHolder> queue = new PriorityQueue<>();

	public ThreadMessageQueue()
	{
		this.time = new RealNanoEnvironmentTime();
	}

	public ThreadMessageQueue(EnvironmentTime time)
	{
		this.time = time;
	}

	@Override
	public void run()
	{
		running = true;

		while (running)
		{
			try
			{
				MessageHolder mh;
				long delay;
				
				synchronized (this)
				{
					if (queue.isEmpty())
					{
						wait();
						continue;
					}

					mh = queue.peek();

					delay = mh.getDispatchTime() - time.getTime();
					if (delay > 0)
					{
						wait(delay);
						continue;
					}

					queue.remove();
				}
				
				mh.getMessage().run();
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void post(Runnable runnable)
	{
		postDelayed(runnable, 0);
	}

	@Override
	public void postDelayed(Runnable runnable, long delay)
	{
		long dispatchTime = time.getTime() + delay;

		MessageHolder mh = new MessageHolder(runnable, dispatchTime);

		synchronized (this)
		{
			queue.add(mh);
			notify();
		}
	}

	public void stop()
	{
		post(() -> running = false);
	}

	private static class MessageHolder implements Comparable<MessageHolder>
	{
		private final Runnable message;
		private final long dispatchTime;

		public MessageHolder(Runnable message, long dispatchTime)
		{
			this.message = message;
			this.dispatchTime = dispatchTime;
		}

		public Runnable getMessage()
		{
			return message;
		}

		public long getDispatchTime()
		{
			return dispatchTime;
		}

		@Override
		public int compareTo(MessageHolder o)
		{
			return Long.signum(dispatchTime - o.dispatchTime);
		}
	}
}
