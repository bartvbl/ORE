package orre.ai.tasks;

public abstract class TaskRequest {
	public final int targetID;
	public final Enum<?>[] acceptableTaskTypes;

	public TaskRequest(int id, Enum<?>[] assignableTaskTypes) {
		this.targetID = id;
		this.acceptableTaskTypes = assignableTaskTypes;
	}
}
