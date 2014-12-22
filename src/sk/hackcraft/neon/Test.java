package sk.hackcraft.neon;

import sk.hackcraft.neon.Event.Listener;
import sk.hackcraft.neon.events.FastStaticEvent;
import sk.hackcraft.neon.executors.MessageQueueExecuter;
import sk.hackcraft.neon.executors.NewThreadExecuter;
import sk.hackcraft.neon.executors.ReuseThreadExecuter;
import sk.hackcraft.neon.messagequeue.ThreadMessageQueue;

public class Test
{
	public static void main(String[] args)
	{
		ThreadMessageQueue mq = new ThreadMessageQueue();
		new Thread(() -> mq.run()).start();
		
		
		MessageQueueExecuter mqe = new MessageQueueExecuter(mq);
		
		Listener<Data> l = (d) -> System.out.println(d.getNum() + " " + d.getName());
		
		Event<Data> e = new FastStaticEvent<>();

		e.attachListener(l);
		e.attachListener(l, NewThreadExecuter.getInstance());
		e.attachListener(l, mqe);
		
		ReuseThreadExecuter rte = new ReuseThreadExecuter();
		e.attachListener(l, rte);
		e.attachListener(l, rte);
		e.attachListener(l, rte);
		
		e.fire(new Data(5, "hura"));
		
		mq.requestStop();
	}
	
	private static class Data
	{
		private final int num;
		private final String name;
		
		public Data(int num, String name)
		{
			this.num = num;
			this.name = name;
		}
		
		public int getNum()
		{
			return num;
		}
		
		public String getName()
		{
			return name;
		}
	}
}
