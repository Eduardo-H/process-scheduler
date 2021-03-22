package scheduler;

public class GanttExecution {
	private int id;
	private int start;
	private int end;
	
	public GanttExecution(int id, int start) {
		this.id = id;
		this.start = start;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

}
