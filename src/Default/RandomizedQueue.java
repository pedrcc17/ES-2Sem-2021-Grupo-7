package Default;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] randomQueue;

	// construct an empty randomized queue
	public RandomizedQueue() {
		randomQueue = (Item[]) new Object[2];
		size = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		if (size == randomQueue.length - 1) {
			Item[] copy = (Item[]) new Object[randomQueue.length * 2];
			for (int i = 0; i != randomQueue.length; i++) {
				copy[i] = randomQueue[i];
			}
			randomQueue = copy;
		}
		randomQueue[size] = item;
		size++;
	}

	// remove and return a random item
	public Item dequeue()
	{
		if (isEmpty()) {
			System.out.println("The queue is empty");
		}
		if (size == 1) {
			Item last = randomQueue[0];
			randomQueue[0] = null;
			size--;
			return last;
		}
		int random = StdRandom.uniform(size);
		Item dequeued = randomQueue[random];
		for (int i = random; i != size - 1; i++) {
			randomQueue[i] = randomQueue[i + 1];
		}
		size--;
		return dequeued;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is already empty");
		}
		int random = StdRandom.uniform(size);
		return randomQueue[random];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new randomQueueIterator();
	}

	// testing (required)
	public static void main(String[] args) {
		RandomizedQueue<Integer> teste = new RandomizedQueue<>();
		teste.enqueue(1);
		teste.dequeue();

		teste.enqueue(1);
		teste.enqueue(2);
		teste.enqueue(3);
		teste.enqueue(4);
		teste.enqueue(5);
		teste.enqueue(6);
		teste.enqueue(7);
		teste.enqueue(8);
		teste.enqueue(9);
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
		teste.dequeue();
	}

	private class randomQueueIterator implements Iterator<Item> {
		private int current;
		private int[] allPosition;

		public randomQueueIterator() {
			for (int i = 0; i != size; i++) {
				allPosition[i] = i;
			}
			StdRandom.shuffle(allPosition);
		}

		@Override
		public boolean hasNext() {
			return current < size;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				Item iterated = randomQueue[allPosition[current]];
				current++;
				return iterated;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}