
package rfry.jtoolkit.execution;

import java.util.function.Consumer;

/**
 * A range of functions for nesting loops.
 * @author Robert E Fry
 * @date 6 Mar 2018
 * @time 08:02:59
 *
 */
public final class NestedExecution {
	
	/**
	 * Simulates n nested for loops where each index ranges from 0 (inclusive) to its limit (exclusive), 
	 * such that the nested for structure:
	 * <pre> {@code}
	 * for (int i = 0; i < limit[0]; i++)
	 *     for (int j = 0; j < limit[1]; j++)
	 *         for (int k = 0; k < limit[2]; k++)
	 *         
	 *             consumer.accept( new int[]{i, j, k} );
	 * </pre>
	 * will function the same as the single call to:
	 * <pre> {@code}
	 * nestedfor(limit, consumer);
	 * </pre>
	 * 
	 * This function will run on a single stack frame for any number of nested loops of any integer limit.
	 * 
	 * @param limit the sequential array of when to break its related nth loop.
	 * @param consumer the consumer to accept at every loop.
	 */
	public static final void nestedexcludedfor(int[] limits, Consumer<int[]> consumer) {
		nestedfor(new int[limits.length], limits, consumer, false);
	}
	
	/**
	 * Simulates n nested for loops where each index ranges from 0 (inclusive) to its limit (inclusive), 
	 * such that the nested for structure:
	 * <pre> {@code}
	 * for (int i = 0; i < limit[0]; i++)
	 *     for (int j = 0; j < limit[1]; j++)
	 *         for (int k = 0; k < limit[2]; k++)
	 *         
	 *             consumer.accept( new int[]{i, j, k} );
	 * </pre>
	 * will function the same as the single call to:
	 * <pre> {@code}
	 * nestedfor(limit, consumer);
	 * </pre>
	 * 
	 * This function will run on a single stack frame for any number of nested loops of any integer limit.
	 * 
	 * @param limit the sequential array of when to break its related nth loop.
	 * @param consumer the consumer to accept at every loop.
	 */
	public static final void nestedincludedfor(int[] limits, Consumer<int[]> consumer) {
		nestedfor(new int[limits.length], limits, consumer, true);
	}
	
	private static final void nestedfor(int[] current, final int[] limits, final Consumer<int[]> consumer, boolean inclusive) {
		while ((inclusive && current[0] <= limits[0]) || (!inclusive && current[0] < limits[0])) {
			consumer.accept(current);
			current[current.length - 1]++; // increment leading unit
			for (int i = current.length - 1; i >= 1; i--) { // increment and wrap preceding units if needed
				if ((inclusive && current[i] > limits[i]) || (!inclusive && current[i] >= limits[i])) {
					current[i] = 0;
					current[i - 1]++;
				}
			}
		}
	}
	
}
