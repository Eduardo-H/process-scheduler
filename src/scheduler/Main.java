package scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		int pId = 0, priority = 0, bashTime = 0, arrivalTime = 0, mostLate = -1;
		String line[], algorithm;
		String fileLocation = "./data.txt";

		Execution execution = new Execution();

		try {
			File file = new File(fileLocation);
			if (file.exists()) {
				Scanner scanner = new Scanner(file);

				// Reading the first line
				String firstLine[] = scanner.nextLine().split(" ");
				int stackNumber = Integer.parseInt(firstLine[0]);
				int processesNumber = Integer.parseInt(firstLine[1]);

				// Reading the queues
				for (int i = 0; i < stackNumber; i++) {
					line = scanner.nextLine().split(" ");
					if (line.length > 1) {
						execution.getLists().add(new ProcessList(line[0], Integer.parseInt(line[1])));
					} else {
						execution.getLists().add(new ProcessList(line[0], -1));
					}
				}

				// Reading the processes
				for (int i = 0; i < processesNumber; i++) {
					line = scanner.nextLine().split(" ");

					pId = Integer.parseInt(line[0]);
					priority = Integer.parseInt(line[1]);
					arrivalTime = Integer.parseInt(line[2]);
					bashTime = Integer.parseInt(line[3]);
					algorithm = execution.getLists().get(priority).getAlgorithm();

					execution.getLists().get(priority).getProcesses()
							.add(new Process(pId, arrivalTime, bashTime, priority, algorithm));

					if (arrivalTime > mostLate)
						mostLate = arrivalTime;
				}

				scanner.close();
				
				// Ordering the stack's processes by ID
				for (ProcessList pList : execution.getLists()) {
					pList.orderById();
				}
				
				execution.setLastArrival(mostLate);

				// Executing
				execution.execute();
				execution.finish();
				
				// Formating the data and calculating the averages
				float sAvg = 0, wAvg = 0;
				String gantt = "Gantt graph\n\n", pTable = "Processes table\n\n", sysAvg = "Average time in the system\n",
						waitAvg = "Average waiting time\n";

				for (GanttExecution ganttEx : execution.getGanttList()) {
					gantt += "P" + ganttEx.getId() + " (" + ganttEx.getStart() + "-" + ganttEx.getEnd() + ") | ";
				}

				for (ProcessList pList : execution.getLists()) {
					for (Process p : pList.getProcesses()) {
						sAvg += p.getTurnAroundTime();
						wAvg += p.getWaitingTime();

						pTable += "| ID: " + p.getId() + ", PR: " + p.getAlgorithmNumber() + ", AT: " + p.getArrivalTime()
								+ ", BT: " + p.getBashTime() + ", CT: " + p.getConclusionTime() + ", TAT: "
								+ p.getTurnAroundTime() + ", WT: " + p.getWaitingTime() + " |\n";
					}
				}

				sysAvg += sAvg / processesNumber;
				waitAvg += wAvg / processesNumber;

				String finalString = gantt + "\n\n" + pTable + "\n" + sysAvg + "\n\n" + waitAvg;

				// Creating and outputing the data in to a file
				try {
					FileWriter output = new FileWriter("output.txt");
					output.write(finalString);
					output.close();
					System.out.println("Operation successfull! Check the results in the file \"output.txt\".");
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			} else {
				System.err.println("File \"" + fileLocation + "\" is missing.");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error while opening file \"" + fileLocation + "\".");
		}
	}
}
