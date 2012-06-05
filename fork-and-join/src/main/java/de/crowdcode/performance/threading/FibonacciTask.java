package de.crowdcode.performance.threading;

import java.util.concurrent.RecursiveTask;

import org.apache.log4j.Logger;

public class FibonacciTask extends RecursiveTask<Long> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(FibonacciTask.class);

	private static final int THRESHOLD = 5;

	private FibonacciProblem problem;

	private long result;

	public FibonacciTask(FibonacciProblem problem) {
		this.problem = problem;
	}

	@Override
	protected Long compute() {
		if (problem.getNumber() < THRESHOLD) {
			result = problem.solve();
		} else {
			LOG.debug("Forking problem "+problem.getNumber());
			FibonacciTask worker1 = new FibonacciTask(new FibonacciProblem(problem.getNumber() - 1));
			FibonacciTask worker2 = new FibonacciTask(new FibonacciProblem(problem.getNumber() - 2));
			worker1.fork();
			result = worker2.compute() + worker1.join();
		}
		return result;
	}

	public long getResult() {
		return result;
	}

}
