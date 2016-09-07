import java.util.PriorityQueue;

public class PriorityQueueChainManager extends ChainManager {

	
	PriorityQueue<Chain> queue; 
	public PriorityQueueChainManager() {
		this.queue=new PriorityQueue<Chain>();
	}
	@Override
	public void add(Chain chain) {
		queue.add(chain);
		if (queue.size()>this.maxSize()) this.setMaxSize(queue.size());
	}
	
	
	@Override
	public Chain next() {
		this.incrementNumNexts();
		return queue.poll();
	}

}
