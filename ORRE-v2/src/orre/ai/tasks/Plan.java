package orre.ai.tasks;

import java.util.Arrays;

public final class Plan {
	private static final class ImpossibleAction extends Action {

		@Override
		public boolean isExecutionPossible() { return false; }

		@Override
		public void update() {}

		@Override
		public boolean isFinished() { return false; }

		@Override
		public double getCost() { return 0; }

		@Override
		public void start() {}

		@Override
		public void end() {}

	}

	private int activeAction = 0;
	private final Action[] actions;
	public static final Plan impossiblePlan = new Plan(new Action[]{new ImpossibleAction()});
	
	public Plan(Action[] actions) {
		this.actions = actions;
	}
	
	public boolean isExecutionPossible() {
		for(Action action : actions) {
			if(!action.isExecutionPossible()) {
				return false;
			}
		}
		return true;
	}
	
	public void update() {
		this.actions[activeAction].update();
		if(actions[activeAction].isFinished() && (activeAction < actions.length - 1)) {
			actions[activeAction].end();
			activeAction++;
			actions[activeAction].start();
		}
		if(actions[activeAction].isFinished()) {
			actions[activeAction].end();
		}
	}
	
	public boolean isFinished() {
		return ((activeAction == actions.length - 1) && (actions[activeAction].isFinished()));
	}
	
	public double getPlanCost() {
		double costSum = 0;
		for(Action action : actions) {
			costSum += action.getCost();
		}
		return costSum;
	}

	public Plan append(Plan plan) {
		Action[] allActions = new Action[actions.length + plan.actions.length];
		System.arraycopy(actions, 0, allActions, 0, actions.length);
		System.arraycopy(plan.actions, 0, allActions, actions.length, plan.actions.length);
		return new Plan(allActions);
	}
	
	public String toString() {
		return "Plan " + Arrays.toString(actions);
	}

	public void start() {
		actions[0].start();
	}
}
