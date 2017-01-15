package orre.resources;

public enum ResourceState {
	UNLOADED,
	QUEUED,
	LOADING,
	AWAITING_COMPLETION,
	COMPLETING,
	LOADED,
	AWAITING_DELETION,
}
