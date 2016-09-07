import java.util.Stack;

public class StackChainManager extends ChainManager {

	Stack<Chain> stack;
	

	public StackChainManager() {
		super();
		stack=new Stack<Chain>();
		this.setMaxSize(0);
		
	}
	@Override
	public void add(Chain chain) {
		stack.push(chain);
		if (stack.size()>this.maxSize()) this.setMaxSize(stack.size());
	}

	@Override
	public Chain next() {
		this.incrementNumNexts();
		if (!stack.isEmpty()) return stack.pop();
		return null;
	}

}
