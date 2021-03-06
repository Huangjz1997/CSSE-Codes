package evaluator;

import java.util.Stack;

public class InfixEvaluator extends Evaluator {

	private Stack<String> stack = new Stack<>();

	public int getlv(String c) { // get the level of different operations
		switch (c) {
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
			return 2;
		case "^":
			return 3;
		case "(":
		case ")":
			return -1;
		default:
			break;
		}
		return 0;
	}

	@Override
	public int evaluate(String expression) throws ArithmeticException {
		try {
			PostfixEvaluator evaluator = new PostfixEvaluator();
			return (evaluator.evaluate(this.convertToPostfix(expression)));
		} catch (ArithmeticException e) {
			throw new ArithmeticException();
		}
	}

	@SuppressWarnings("static-access")
	public String convertToPostfix(String exp) {
		String[] elements = exp.split(" ");
		String anString = new String();
		boolean isdigital = false;
		for (String c : elements) { // isdigital to justify is there any
									// continuous
									// operation
			if (this.isOperator(c)) { // if it is a operation
				if ((!c.equals("(")) && (!c.equals(")"))) {
					if (!isdigital)
						throw new ArithmeticException();
					isdigital = false;
				}
				if (stack.isEmpty()) {
					stack.push(c);
					continue;
				}
				if (c.equals("(")) {
					stack.push(c);
					continue;
				}
				if (c.equals(")")) { // if we meet ")" we need pop the element
										// until "("
					boolean flag = false;
					while ((!stack.empty()) && (!flag)) {
						if (!stack.peek().equals("("))
							anString = anString + stack.pop() + " ";
						else {
							stack.pop();
							flag = true;
						}
					}
					if (!flag)
						throw new ArithmeticException();
				} else { // common operation, if the level of peek is higher, do
							// the peek
							// operater else push it into stack
					String a = stack.peek();
					while (this.getlv(c) <= this.getlv(a)) {
						anString = anString + stack.pop() + " ";
						if (stack.isEmpty())
							break;
						a = stack.peek();
					}
					stack.push(c);
				}
			} else {
				if (isdigital)
					throw new ArithmeticException();
				isdigital = true;
				anString = anString + c + " ";
			}
		}
		while (!stack.isEmpty()) {
			if (stack.peek().equals("("))
				throw new ArithmeticException();
			anString = anString + stack.pop() + " ";
		}
		return anString.substring(0, anString.length() - 1);
	}

}