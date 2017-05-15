package com.headfirst.mediator.normal;

/**
 * @描述：抽象同事类
 * @创建时间：2015-10-26 下午2:01:07
 */
abstract class AbstractColleague {
	protected int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 抽象方法，修改数字时同时修改关联对象
	 * 
	 * @param number
	 * @param coll
	 */
	public abstract void setNumber(int number, AbstractColleague coll);
}

class ColleagueA extends AbstractColleague {

	@Override
	public void setNumber(int number, AbstractColleague coll) {
		this.number = number;
		coll.setNumber(number * 100);
	}

}

class ColleagueB extends AbstractColleague {

	@Override
	public void setNumber(int number, AbstractColleague coll) {
		this.number = number;
		coll.setNumber(number / 100);
	}

}