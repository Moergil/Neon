package sk.hackcraft.neon;

import sk.hackcraft.neoevents.messagequeue.ThreadMessageQueue;

public class Test
{
	public static void main(String[] args)
	{
		ThreadMessageQueue mq = new ThreadMessageQueue();
		new Thread(() -> mq.run()).start();
		
		mq.post(() -> System.out.println("1"));
		mq.post(() -> System.out.println("2"));
		mq.post(() -> System.out.println("3"));
		mq.postDelayed(() -> System.out.println("d1"), 100);
		mq.postDelayed(() -> System.out.println("d2"), 1000);
		mq.postDelayed(() -> System.out.println("d3"), 500);
		
		
		/*MessageQueueExecuter mqe = new MessageQueueExecuter(mq);
		
		Listener<Data> l = (d) -> System.out.println(d.getNum() + " " + d.getName());
		
		Event<Data> e = new FastStaticEvent<>();
		
		e.addListener(l);
		e.addListener(l, ThreadExecuter.getInstance());
		e.addListener(l, mqe);
		
		e.fire(new Data(5, "hura"));*/
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
