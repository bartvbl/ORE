package orre.gameWorld.messages;

import orre.ai.tasks.Assignment;
import orre.ai.tasks.Task;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class NewAssignmentMessage extends Message<Assignment> {

	public NewAssignmentMessage(Assignment assignment) {
		super(MessageType.ASSIGN_TASK, assignment);
	}

}
