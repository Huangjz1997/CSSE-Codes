import java.util.LinkedList;

public class QueueChainManager extends ChainManager {

	private LinkedList<Chain> queue;
	
	public QueueChainManager() {
		super();
		queue=new LinkedList<Chain>();
	}
	
	@Override
	public void add(Chain chain) {
		queue.add(chain);
		if (queue.size()>this.maxSize()) this.setMaxSize(queue.size());
	}

	
	@Override
	public Chain next() {
		this.incrementNumNexts();
		if (!queue.isEmpty()) return queue.pollFirst();
		return null;
	}

}
