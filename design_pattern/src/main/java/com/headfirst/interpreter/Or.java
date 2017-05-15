package com.headfirst.interpreter;

/**
 * @描述：代表逻辑“或”操作的Or类，代表由两个布尔表达式通过逻辑“或”操作给出一个新的布尔表达式的操作
 * @创建时间：2015-10-26 上午10:23:20
 */
public class Or extends Expression {
	private Expression left, right;

	public Or(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Or) {
			return this.left.equals(((Or) obj).left)
					&& this.right.equals(((Or) obj).right);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean interpret(Context ctx) {
		return left.interpret(ctx) || right.interpret(ctx);
	}

	@Override
	public String toString() {
		return "(" + left.toString() + " OR " + right.toString() + ")";
	}

}