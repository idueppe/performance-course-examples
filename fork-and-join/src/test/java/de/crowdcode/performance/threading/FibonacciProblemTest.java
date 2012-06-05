package de.crowdcode.performance.threading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.perf4j.StopWatch;

@RunWith(Parameterized.class)
public class FibonacciProblemTest {

	private static final Logger LOG = Logger.getLogger(FibonacciProblemTest.class);
	private int number;

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> params = new ArrayList<>();
		for (int i = 0; i < 35; i++) {
			Object[] number = new Object[1];
			number[0] = i;
			params.add(number);
		}
		return params;
	}

	public FibonacciProblemTest(int number) {
		this.number = number;
	}

	@Test
	public void testSillyWorker() {
		StopWatch stopWatch = new StopWatch();
		FibonacciProblem bigProblem = new FibonacciProblem(number);

		long result = bigProblem.solve();
		stopWatch.stop();

		LOG.info("Computing Fib number: " + number);
		LOG.info("Computed Result: " + result);
		LOG.info("Elapsed Time: " + stopWatch.getElapsedTime());
	}

	@Test
	public void testForkAndJoin() {
		int processors = Runtime.getRuntime().availableProcessors();
		LOG.info("Number of processors: " + processors);

		StopWatch stopWatch = new StopWatch();
		FibonacciProblem problem = new FibonacciProblem(number);

		FibonacciTask task = new FibonacciTask(problem);
		ForkJoinPool pool = new ForkJoinPool(processors);
		pool.invoke(task);

		long result = task.getResult();
		stopWatch.stop();

		LOG.info("Computing Fib number: " + number);
		LOG.info("Computed Result: " + result);
		LOG.info("Elapsed Time: " + stopWatch.getElapsedTime());
	}

}
