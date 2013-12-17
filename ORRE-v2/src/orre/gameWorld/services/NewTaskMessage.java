package orre.gameWorld.services;

import orre.ai.tasks.Task;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class NewTaskMessage extends Message<Task> {

	public NewTaskMessage(Task task) {
		super(MessageType.NEW_TASK, task);
	}

}
