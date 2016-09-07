package evaluator;

import java.util.Stack;

public class PostfixEvaluator extends Evaluator {

	@Override
	public int evaluate(String expression) throws ArithmeticException {
		String[] elements = expression.split(" ");
		Stack<Integer> stack = new Stack<>();
		for (String k : elements) {
			if (this.isOperator(k)) { // if it is a operator we get numbers of
										// stack and calculate it.
				int a, b;
				if (stack.size() < 2)
					throw new ArithmeticException();
				b = stack.pop();
				a = stack.pop(); // get two numbers
				switch (k) { // calculate and push the ans into stack
				case "+":
					stack.push(a + b);
					break;
				case "-":
					stack.push(a - b);
					break;
				case "*":
					stack.push(a * b);
					break;
				case "/":
					if (b == 0)
						throw new ArithmeticException();
					stack.push(a / b);
					break;
				case "^":
					stack.push((int) Math.pow(a, b));
					break;
				default:
					throw new ArithmeticException();
				}
			} else {
				try {
					int a = Integer.parseInt(k);
					stack.push(a); // it is a number, push it into stack
				} catch (NumberFormatException e) {
					throw new ArithmeticException();
				}

			}
		}
		if (stack.size() > 1)
			throw new ArithmeticException();

		return stack.pop();
	}

}
