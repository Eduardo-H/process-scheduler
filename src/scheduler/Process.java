package scheduler;

public class Process {
	private int id;
	private String algorithm;
	private int algorithmNumber;
	private int arrivalTime;
	private int bashTime;
	private int conclusionTime;
	private int turnAroundTime;
	private int waitingTime;
	
	private int executionTime;
	private int remaningTime;
	
	
	public Process(int id, int arrivalTime, int bashTime, int algorithmNumber, String algorithm) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.bashTime = bashTime;
		this.remaningTime = bashTime;
		this.algorithm = algorithm;
		this.algorithmNumber = algorithmNumber;
		this.executionTime = 0;
	}
	
	public void finish() {
		this.turnAroundTime = this.conclusionTime - this.arrivalTime;
		this.waitingTime = this.turnAroundTime - this.bashTime;
	}
	
	public void updateRemaningTime(int executionTime) {
		this.remaningTime -= executionTime;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBashTime() {
		return bashTime;
	}

	public void setBashTime(int bashTime) {
		this.bashTime = bashTime;
	}

	public int getConclusionTime() {
		return conclusionTime;
	}

	public void setConclusionTime(int conclusionTime) {
		this.conclusionTime = conclusionTime;
	}

	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}

	public int getRemaningTime() {
		return remaningTime;
	}

	public void setRemaningTime(int remaningTime) {
		this.remaningTime = remaningTime;
	}

	public int getAlgorithmNumber() {
		return algorithmNumber;
	}

	public void setAlgorithmNumber(int algorithmNumber) {
		this.algorithmNumber = algorithmNumber;
	}
}
