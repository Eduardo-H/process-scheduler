package scheduler;

import java.util.ArrayList;
import java.util.List;

public class Execution {
	private int currentTime;
	private int lastArrival;

	private List<ProcessList> lists;
	private List<Process> executed;
	private List<GanttExecution> ganttList;

	public Execution() {
		this.currentTime = 0;
		this.lists = new ArrayList<ProcessList>();
		this.executed = new ArrayList<Process>();
		this.ganttList = new ArrayList<GanttExecution>();
	}

	public void execute() {
		List<Process> queue = new ArrayList<Process>();
		Boolean executed;

		while (true) {
			executed = false;
			// Adding the new processes to the queue
			queue = addInCurrentTime(queue);

			if (queue.size() > 0) {
				String alg = queue.get(0).getAlgorithm();
				executed = true;

				if (alg.contentEquals("FCFS")) {
					queue = runFCFS(queue);
				} else if (alg.contentEquals("SJF")) {
					int index = getListIndex(queue.get(0));
					queue = runSJF(lists.get(index).getProcesses(), queue);
				} else if (alg.contentEquals("RR")) {
					int index = getListIndex(queue.get(0));
					queue = runRR(queue, lists.get(index).getQuantum());
				} else {
					System.out.println("The current process in the queue has a not known algorithm");
					executed = false;
				}
			}

			if (!executed)
				this.currentTime++;

			if (currentTime > lastArrival && queue.isEmpty())
				break;
		}
	}

	public List<Process> addInCurrentTime(List<Process> queue) {
		for (int i = 0; i < lists.size(); i++) {
			for (Process p : lists.get(i).getProcesses()) {
				if (p.getArrivalTime() <= currentTime && !queue.contains(p) && !this.executed.contains(p)) {
					queue = addOrderd(p, queue);
				}
			}
		}

		return queue;
	}

	public List<Process> addInCurrentTime(List<Process> queue, Process process) {
		for (int i = 0; i < lists.size(); i++) {
			for (Process p : lists.get(i).getProcesses()) {
				if (p.getArrivalTime() <= currentTime && !queue.contains(p) && !this.executed.contains(p)
						&& p != process) {
					queue = addOrderd(p, queue);
				}
			}
		}

		return queue;
	}

	public List<Process> addOrderd(Process p, List<Process> queue) {
		if (queue.size() > 1) {
			// Verifing if needs to add in the last position
			if (queue.get(queue.size() - 1).getAlgorithmNumber() <= p.getAlgorithmNumber()) {
				queue.add(p);
				return queue;
			}

			for (int i = queue.size() - 1; i >= 0; i--) {
				if (i - 1 >= 0) {
					if (p.getAlgorithmNumber() >= queue.get(i - 1).getAlgorithmNumber()) {
						queue.add(i, p);
						break;
					}
				} else {
					queue.add(i, p);
					break;
				}

			}
		} else if (queue.size() > 0) {
			if (queue.get(0).getAlgorithmNumber() > p.getAlgorithmNumber())
				queue.add(0, p);
			else
				queue.add(p);
		} else {
			queue.add(p);
		}

		return queue;
	}

	public List<Process> runFCFS(List<Process> queue) {
		Process process = getLowerId(queue);
		GanttExecution ganttEx = new GanttExecution(process.getId(), this.currentTime);

		process.setConclusionTime(this.currentTime + process.getBashTime());
		process.updateRemaningTime(process.getBashTime());

		this.currentTime += process.getBashTime();
		queue.remove(process);
		this.executed.add(process);

		ganttEx.setEnd(this.currentTime);
		ganttList.add(ganttEx);

		return queue;
	}

	public List<Process> runSJF(List<Process> pList, List<Process> queue) {
		int minor = 999999999, index = 0;

		for (int i = 0; i < pList.size(); i++) {
			if (pList.get(i).getBashTime() < minor && pList.get(i).getArrivalTime() <= this.currentTime
					&& !this.executed.contains(pList.get(i))) {
				minor = pList.get(i).getBashTime();
				index = i;
			}
		}

		Process process = pList.get(index);
		GanttExecution ganttEx = new GanttExecution(process.getId(), this.currentTime);

		process.setConclusionTime(this.currentTime + process.getBashTime());
		process.updateRemaningTime(process.getBashTime());
		this.currentTime += process.getBashTime();

		queue.remove(process);
		this.executed.add(process);

		ganttEx.setEnd(this.currentTime);
		ganttList.add(ganttEx);

		return queue;
	}

	public List<Process> runRR(List<Process> queue, int quantum) {
		Process process = queue.get(0);
		GanttExecution ganttEx = new GanttExecution(process.getId(), this.currentTime);

		if (quantum <= process.getRemaningTime()) {
			process.updateRemaningTime(quantum);
			this.currentTime += quantum;
		} else {
			int remaningTime = process.getRemaningTime();
			process.updateRemaningTime(remaningTime);
			this.currentTime += remaningTime;
		}

		queue.remove(process);

		if (process.getRemaningTime() > 0) {
			queue = addInCurrentTime(queue, process);
			queue = addOrderd(process, queue);
		} else {
			process.setConclusionTime(this.currentTime);
			executed.add(process);
		}

		ganttEx.setEnd(this.currentTime);
		ganttList.add(ganttEx);

		return queue;
	}

	public void finish() {
		for (ProcessList pList : lists) {
			for (Process p : pList.getProcesses()) {
				p.finish();
			}
		}
	}

	public int getListIndex(Process process) {
		int index = 0;

		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getProcesses().contains(process)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public Process getLowerId(List<Process> queue) {
		Process p = queue.get(0);
		int index = 0;

		for (int i = 0; i < queue.size(); i++) {
			if (p.getAlgorithmNumber() == queue.get(i).getAlgorithmNumber() && p.getId() > queue.get(i).getId()
					&& p.getArrivalTime() == queue.get(i).getArrivalTime()) {
				index = i;
			}
		}

		return queue.get(index);
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public List<ProcessList> getLists() {
		return lists;
	}

	public void setLists(List<ProcessList> lists) {
		this.lists = lists;
	}

	public int getLastArrival() {
		return lastArrival;
	}

	public void setLastArrival(int lastArrival) {
		this.lastArrival = lastArrival;
	}

	public List<Process> getExecuted() {
		return executed;
	}

	public void setExecuted(List<Process> executed) {
		this.executed = executed;
	}

	public List<GanttExecution> getGanttList() {
		return ganttList;
	}

	public void setGanttList(List<GanttExecution> ganttList) {
		this.ganttList = ganttList;
	}

}
