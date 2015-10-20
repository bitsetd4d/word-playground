package d3bug.poc.threads;

public interface UniverseRunningListener {

	void onQueueLength(int size);
	void onScheduled(String job,int position);
	void onExecute(String job);
	void onExecuted(String job,long timetaken);
	
}
