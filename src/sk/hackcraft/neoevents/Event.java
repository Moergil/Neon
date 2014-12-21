package sk.hackcraft.neoevents;

import sk.hackcraft.neoevents.executors.ImmediateExecuter;

/**
 * <p>
 * Flexible event listener mechanism for minimize boilerplate code and to
 * simplify events in multithread environment.
 * </p>
 * 
 * <p>
 * Every event consists of two parts: attached listeners and event firing
 * mechanism. Event allows attachment of multiple listeners, and their detaching
 * when needed. Attaching and detaching listeners is thread safe. Firing
 * mechanism allows call to all listeners with specified parameters. Firing
 * event is thread save, and it allows removal or adding of listeners during
 * firing phase. Listeners added during firing phase will not be called, and
 * listeners removed during firing phase will be still called.
 * </p>
 * 
 * <p>
 * Listeners can be called directly, or through executer object. Executer object
 * is controlling how listener is called, and can either call listener directly,
 * in new thread, through {@link MessageQueue}, etc.
 * </p>
 * 
 * @param <T>
 *            type of parameter passed to listeners
 */
public interface Event<T>
{
	/**
	 * <p>
	 * Variant of {@link #attachListener(Listener, Executer)}, with
	 * {@link ImmediateExecuter} used as {@link Executer}.
	 * </p>
	 * 
	 * <p>
	 * Attach listener to this event with specified executer. This method is
	 * thread safe and can be called event during event firing phase. In that
	 * case, attached listener will not be called.
	 * </p>
	 * 
	 * @param listener
	 *            listener to attach
	 */
	void attachListener(Listener<T> listener);

	/**
	 * <p>
	 * *Attach listener to this event with specified executer. This method is
	 * thread safe and can be called event during event firing phase. In that
	 * case, attached listener will not be called.
	 * </p>
	 *
	 * @param listener
	 *            listener to attach
	 * @param executer
	 *            executer used to call listener
	 */
	void attachListener(Listener<T> listener, Executer executer);

	/**
	 * <p>
	 * Removes listener from this event. This method is thread safe and can be
	 * called event during event firing phase. In that case, removed listener
	 * will still be called.
	 * </p>
	 *
	 * @param listener
	 *            listener to remove
	 */
	void removeListener(Listener<T> listener);

	/**
	 * <p>
	 * Fires this event with specified parameters.
	 * </p>
	 * 
	 * <p>
	 * All currently attached listeners will be called in unspecified order,
	 * with passsed <code>params</code> value.
	 * </p>
	 * 
	 * <p>
	 * This method is thread safe. It's possible to remove or attach listeners
	 * during firing phase. In that case, added listeners will not be called and
	 * removed listeners will still be called.
	 * </p>
	 * 
	 * @param params
	 *            event parameters
	 */
	void fire(T params);

	/**
	 * <p>
	 * Event listener definition.
	 * </p>
	 *
	 * @param <T>
	 *            type of parameter passed to listener
	 */
	@FunctionalInterface
	public interface Listener<T>
	{
		/**
		 * <p>
		 * Called when event is fired.
		 * </p>
		 * 
		 * @param params
		 *            parameters of fired event
		 */
		void fired(T params);
	}

	/**
	 * <p>
	 * Executing mechanism for listeners. Various implementations can modify,
	 * how listener will be called, for example directly, in another thread or
	 * through {@link MessageQueue}.
	 * </p>
	 */
	public interface Executer
	{
		/**
		 * <p>Called by {@link Event} implementations, implementation should decide
		 * when listener {@link Listener#fired(Object)} method will be called
		 * with passed parameters.</p>
		 * 
		 * @param listener
		 *            listener to execute
		 * @param params
		 *            parameters for listener
		 */
		<T> void execute(Listener<T> listener, T params);
	}
}
