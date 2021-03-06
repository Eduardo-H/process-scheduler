package scheduler;

import java.util.ArrayList;
import java.util.List;

public class ProcessList {
	private String algorithm;
	private int quantum;
	private List<Process> processes;

	public ProcessList() {

	}

	public ProcessList(String algorithm, int quantum) {
		super();
		this.algorithm = algorithm;
		this.quantum = quantum;
		this.processes = new ArrayList<Process>();
	}

	public ProcessList(String algorithm, int quantum, ArrayList<Process> processes) {
		super();
		this.algorithm = algorithm;
		this.quantum = quantum;
		this.processes = processes;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	
	public void orderById() {
		quickSort(processes, 0, processes.size() - 1, 0);
	}

	public void orderByArrival() {
		quickSort(processes, 0, processes.size() - 1, 1);
	}

	private static void quickSort(List<Process> list, int start, int end, int type) {
		if (start < end) {
			int pivot = 0;
			switch (type) {
				case 0:
					pivot = splitId(list, start, end);
					break;
				case 1:
					pivot = splitArrival(list, start, end);
					break;
				default:
					System.out.println("Invalid type");
					break;
			}
				
			quickSort(list, start, pivot - 1, type);
			quickSort(list, pivot + 1, end, type);
		}
	}

	private static int splitArrival(List<Process> list, int start, int end) {
		Process pivot = list.get(start);
		int i = start + 1, f = end;
		
		while (i <= f) {
			if (list.get(i).getArrivalTime() <= pivot.getArrivalTime())
				i++;
			else if (pivot.getArrivalTime() < list.get(f).getArrivalTime())
				f--;
			else {
				Process change = list.get(i);
				list.set(i, list.get(f));
				list.set(f, change);
				i++;
				f--;
			}
		}
		
		list.set(start, list.get(f));
		list.set(f, pivot);
		return f;
	}
	
	private static int splitId(List<Process> list, int start, int end) {
		Process pivot = list.get(start);
		int i = start + 1, f = end;
		
		while (i <= f) {
			if (list.get(i).getId() <= pivot.getId())
				i++;
			else if (pivot.getId() < list.get(f).getId())
				f--;
			else {
				Process change = list.get(i);
				list.set(i, list.get(f));
				list.set(f, change);
				i++;
				f--;
			}
		}
		
		list.set(start, list.get(f));
		list.set(f, pivot);
		return f;
	}

}
