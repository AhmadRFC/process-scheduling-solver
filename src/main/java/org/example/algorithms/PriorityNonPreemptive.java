package org.example.algorithms;

import org.example.Process;

import java.util.ArrayList;
import java.util.LinkedList;

public class PriorityNonPreemptive {

    private final LinkedList<Process> Processes;
    private final ArrayList<GanttChartSection> ganttChartData;

    public PriorityNonPreemptive(LinkedList<Process> Processes) {
        this.Processes = Processes;
        ganttChartData = new ArrayList<>();
        Calculate();
    }

    //
    public void Calculate() {
        int counter = 0;
        Process pro = null;
        LinkedList<Process> readyP = new LinkedList<>();
        for (Process element : this.Processes) {
            readyP.add(new Process(element));
        }
        while (!readyP.isEmpty()) {

            if (readyP.get(0).getArrivalTime() <= counter) {
                pro = readyP.get(0);
            }

            for (Process item : readyP) {
                if (item.getArrivalTime() <= counter) {
                    pro = item;
                    break;
                }
            }


            for (Process value : readyP) {
                if (value.getArrivalTime() <= counter) {
                    if (pro != null && value.getPriority() < pro.getPriority()) {
                        pro = value;
                    }
                }
            }
            if (pro != null) {
                for (Process process : this.Processes) {
                    if (process.getPName().matches(pro.getPName())) {
                        //gantt chart implementation
                        GanttChartSection temp =
                                new GanttChartSection(counter, counter + pro.getBurstTime(),
                                        pro.getPName());
                        ganttChartData.add(temp);

                        process.setResponseTime(counter - process.getArrivalTime());
                        counter += process.getBurstTime();
                        process.setTerminationTime(counter);
                        process.setTurnaroundTime(
                                process.getTerminationTime() - process.getArrivalTime());
                        process.setWaitingTime(
                                process.getTurnaroundTime() - process.getBurstTime());
                    }
                }
                for (int i = 0; i < readyP.size(); i++) {
                    if (readyP.get(i).getPName().matches(pro.getPName())) {
                        readyP.remove(i);
                        break;
                    }
                }
            } else {
                counter++;
            }
        }
    }

    public double getAverageWaiting() {
        double sum = 0;
        for (Process process : this.Processes) {
            sum += process.getWaitingTime();
        }
        return sum / this.Processes.size();
    }

    public double getAverageTurnaround() {
        double sum = 0;
        for (Process process : this.Processes) {
            sum += process.getTurnaroundTime();
        }
        return sum / this.Processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }
}

