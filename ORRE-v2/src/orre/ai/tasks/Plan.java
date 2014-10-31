package orre.ai.tasks;

public final class Plan {
	private int activeAction = 0;
	private final Action[] actions;
	
	public Plan(Action[] actions) {
		this.actions = actions;
	}
	
	public boolean isExecutionPossible() {
		for(Action action : actions) {
			if(action.isExecutionPossible()) {
				return false;
			}
		}
		return true;
	}
	
	public void update() {
		this.actions[activeAction].update();
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
}
