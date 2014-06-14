package orre.input;

public class InputEvent {
	public final double value;
	public final double delta;
	public final KeyType type;

	private boolean isConsumed;

	public InputEvent(final KeyType type, final double value, final double delta) {
		this.value = value;
		this.delta = delta;
		this.isConsumed = false;
		this.type = type;
	}

	public void consume() {
		this.isConsumed = true;
	}

	public boolean isConsumed() {
		return isConsumed;
	}
}
