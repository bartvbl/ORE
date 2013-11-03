package orre.ai.pathFinding;

import java.util.ArrayList;

public interface State<DataType> {
	public ArrayList<State<DataType>> getSuccessors();
	public double estimateHeuristicTo(DataType other);
	public double getDistanceToSuccessor(DataType successor);
	public boolean isEqualTo(DataType otherState);
}
