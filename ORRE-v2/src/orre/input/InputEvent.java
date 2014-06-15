package orre.input;

public class InputEvent {
	public final double value;
	public final double delta;
	public final String command;

	private boolean isConsumed;

	public InputEvent(final String command, final double value, final double delta) {
		this.value = value;
		this.delta = delta;
		this.isConsumed = false;
		this.command = command;
	}

	public void consume() {
		this.isConsumed = true;
	}

	public boolean isConsumed() {
		return isConsumed;
	}
}
