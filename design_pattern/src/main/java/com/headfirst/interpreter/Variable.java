package com.headfirst.interpreter;

/**
 * @描述：一个Variable对象代表一个有名变量
 * @创建时间：2015-10-26 上午10:14:54
 */
public class Variable extends Expression {
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}

	@Override
	public boolean interpret(Context ctx) {
		return ctx.lookup(this);
	}
	
	@Override
    public boolean equals(Object obj) {
        
        if(obj != null && obj instanceof Variable)
        {
            return this.name.equals(
                    ((Variable)obj).name);
        }
        return false;
    }
	
	@Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
