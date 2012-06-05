package de.crowdcode.performance.threading;

import org.apache.log4j.Logger;

public class FibonacciProblem {

	private static final Logger LOG = Logger.getLogger(FibonacciProblem.class);

	private long number;

	public FibonacciProblem(long n) {
		this.number = n;
	}

	public long solve() {
		return fibonacci(number);
	}

	private long fibonacci(long n) {
		LOG.debug("Thread: " + Thread.currentThread().getName() + " calculates " + n);
		if (n <= 1L) {
			return n;
		} else {
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}

	public long getNumber() {
		return number;
	}
}
