package com.headfirst.mediator.mediator;

/**
 * @描述：抽象中介者
 * @创建时间：2015-10-26 下午2:31:10
 */
abstract class AbstractMediator {
	protected AbstractColleague A;
	protected AbstractColleague B;

	public AbstractMediator(AbstractColleague a, AbstractColleague b) {
		this.A = a;
		this.B = b;
	}

	public abstract void AaffectB();

	public abstract void BaffectA();
}

class Mediator extends AbstractMediator {

	public Mediator(AbstractColleague a, AbstractColleague b) {
		super(a, b);
	}

	@Override
	public void AaffectB() {
		int number = A.getNumber();
		B.setNumber(number * 100);

	}

	@Override
	public void BaffectA() {
		int number = B.getNumber();
		A.setNumber(number / 100);
	}

}
