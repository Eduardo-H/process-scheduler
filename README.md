# Process Scheduler ⏱️

This is project created for the class of Operational Systems in my university. It's purpose is to read data from a file that contains the selected algorithms and the processes information.

## File with data configuration
The file that has the data have to be named "data.txt" and needs to follow certain rules for the right execution of the algorithm. The rules are:
* The first line of the file have to inform the number of stacks and the number of process that will exist. <br>
<strong>Example: "2 7"</strong>
* The next M lines have inform the name of the algorithm and the value of quantum. The supported algorithm are: FCFS, RR, SJF. The only algorithm that accepts a quantum value is the RR. <br>
<strong>Example: "RR 2"</strong>
* The next N lines will contain the process data, and each line needs to have four information. The first is the process id, the second is the index of the stack that it will execute, the third is the arrival time and the fourth is the bash time. <br>
<strong>Example: "1 0 3 9"</strong>

## Full example
Here is an example of what your data should look like. <br>
<code>
2 4<br>
RR 4<br>
FCFS<br>
1 0 0 3<br>
2 1 2 5<br>
3 1 4 2<br>
4 0 10 6<br>
</code>

## Execution
The software will read the data from the file and execute the processes according to the stack's algorithm that they're in. The software algorithm will give priority for the stacks that were first declared in the data file.

## Results
After executing all the process, it will created a file named "output.txt" having the results, wich are: 
* Gantt graph 
* Processes table 
* Average execution time
* Average waiting time.

### Example
The results for the data used in the example would be:<br>
<code>
Gantt graph<br>
P1 (0-3) | P2 (3-8) | P3 (8-10) | P4 (10-14) | P4 (14-16) | <br><br>
Processes table

| ID: 1, PR: 0, AT: 0, BT: 3, CT: 3, TAT: 3, WT: 0 |<br>
| ID: 4, PR: 0, AT: 10, BT: 6, CT: 16, TAT: 6, WT: 0 |<br>
| ID: 2, PR: 1, AT: 2, BT: 5, CT: 8, TAT: 6, WT: 1 |<br>
| ID: 3, PR: 1, AT: 4, BT: 2, CT: 10, TAT: 6, WT: 4 |<br>
<br>
Average time in the system<br>
5.25

Average waiting time<br>
1.25
</code>