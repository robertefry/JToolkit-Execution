
package rfry.jtoolkit.execution;

/**
 * A collection of methods for testing the speed of algorithms.
 * @author Robert E Fry
 * @date 2 Dec 2018
 * @time 20:22:52
 *
 */
public final class SpeedTester {
	
	/**
	 * Times how long a {@code Runnable} task takes to run {@value repeat} times.
	 * @param task the task to run.
	 * @param repeat how many times the task should run.
	 * @return how long a {@code Runnable} task takes to run {@value repeat} times.
	 */
	public static final long timeExecution(Runnable task, int repeat) {
		long initialtime = System.nanoTime();
		for (int i = 0; i < repeat; i++ ) {
			task.run();
		}
		return System.nanoTime() - initialtime;
	}
	
}
