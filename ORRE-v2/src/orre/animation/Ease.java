package orre.animation;

public enum Ease {
	EASE_IN, EASE_OUT, NO_EASE;

	public double calculateAnimationPercentage(double percentElapsed) {
		switch(this) {
		case EASE_IN:
			percentElapsed = 1 - percentElapsed;
			return 1 - (percentElapsed * percentElapsed);
		case EASE_OUT:
			return (percentElapsed * percentElapsed);
		case NO_EASE:
			return percentElapsed;
		default:
			return 0;
		}
	}
}
