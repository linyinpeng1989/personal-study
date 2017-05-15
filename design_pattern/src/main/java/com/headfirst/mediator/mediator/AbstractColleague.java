package com.headfirst.mediator.mediator;

/**
 * @描述：抽象同事类
 * @创建时间：2015-10-26 下午2:29:54
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
	 * 注意这里的参数不再是同事类，而是一个中介者
	 * 
	 * @param number
	 * @param am
	 */
	public abstract void setNumber(int number, AbstractMediator am);
	
}

class ColleagueA extends AbstractColleague {

	@Override
	public void setNumber(int number, AbstractMediator am) {
		this.number = number;
		am.AaffectB();
	}
}

class ColleagueB extends AbstractColleague {

	@Override
	public void setNumber(int number, AbstractMediator am) {
		this.number = number;
		am.BaffectA();
	}
}
