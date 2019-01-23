
package rfry.jtoolkit.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.BooleanSupplier;

/**
 * Methods for scheduling job execution
 * @author Robert E Fry
 * @date 12 Dec 2018
 * @time 05:51:13
 *
 */
public final class JobScheduler {
	
	/**
	 * Immediately runs the job on a new thread.
	 * @param <R> the return type.
	 * @param job the job to run.
	 * @return a {@link Future} instance encapsulating the result.
	 * @author Robert E Fry
	 * @see {@link Callable}
	 */
	public static final <R> Future<R> schedule(Callable<R> job) {
		final FutureTask<R> task = new FutureTask<>(job);
		new Thread(() -> {
			task.run();
		}).start();
		return task;
	}
	
	/**
	 * Executes the job on a new thread when the {@code sync} object is notified.
	 * @param <R> the return type.
	 * @param sync the sync object
	 * @param job the job to run
	 * @return a {@link Future} instance encapsulating the result.
	 * @author Robert E Fry
	 * @see {@link Callable}, {@link Object#wait()}, {@link Object#notify()}
	 */
	public static final <R> Future<R> schedule(Object sync, Callable<R> job) {
		final FutureTask<R> task = new FutureTask<>(job);
		new Thread(() -> {
			synchronized (sync) {
				try {
					sync.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				task.run();
			}
		}).start();
		return task;
	}
	
	/**
	 * Executes the job on a new thread when the condition returns true.
	 * @param <R> the return type.
	 * @param condition the condition.
	 * @param job the job to run.
	 * @return a {@link Future} instance encapsulating the result.
	 * @author Robert E Fry
	 * @see {@link BooleanSupplier}, {@link Callable}
	 */
	public static final <R> Future<R> schedule(BooleanSupplier condition, Callable<R> job) {
		final FutureTask<R> task = new FutureTask<>(job);
		new Thread(() -> {
			while (!condition.getAsBoolean());
			task.run();
		}).start();
		return task;
	}
	
	/**
	 * Executed the job on a new thread after {@code time} milliseconds.
	 * @param <R> the return type.
	 * @param time how many milliseconds to wait.
	 * @param job the job to run.
	 * @return a {@link Future} instance encapsulating the result.
	 * @author Robert E Fry
	 * @see {@link Callable}
	 */
	public static final <R> Future<R> schedule(int time, Callable<R> job) {
		final FutureTask<R> task = new FutureTask<>(job);
		new Thread(() -> {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			task.run();
		}).start();
		return task;
	}
	
}
