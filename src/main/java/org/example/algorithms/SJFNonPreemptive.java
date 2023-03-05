package org.example.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import org.example.Process;

public class SJFNonPreemptive {
    private final LinkedList<Process> processes;
    private final ArrayList<GanttChartSection> ganttChartData;

    public SJFNonPreemptive(LinkedList<Process> processes) {
        this.processes = processes;
        ganttChartData = new ArrayList<>();
        Calculate();
    }

    //
    public void Calculate() {
        int counter = 0;
        int max = 0;
        Process pro = null;
        LinkedList<Process> readyP = new LinkedList<>();
        for (int i = 0; i < this.processes.size(); i++) {
            readyP.add(new Process(this.processes.get(i)));
            max += readyP.get(i).getBurstTime();
        }
        while (!readyP.isEmpty()) {

            if (readyP.get(0).getArrivalTime() <= counter) {
                pro = readyP.get(0);
            }

            for (Process process : readyP) {
                if (process.getArrivalTime() <= counter) {
                    pro = process;
                    break;
                }
            }
            for (Process process : readyP) {
                if (process.getArrivalTime() <= counter) {
                    if (pro != null && process.getBurstTime() < pro.getBurstTime()) {
                        pro = process;
                    }
                }
            }
            if (pro != null) {
                //gantt chart implementation
                GanttChartSection temp =
                        new GanttChartSection(counter, counter + pro.getBurstTime(),
                                pro.getPName());
                ganttChartData.add(temp);
                //
                for (Process process : this.processes) {
                    if (process.getPName().matches(pro.getPName())) {
                        process.setResponseTime(counter - process.getArrivalTime());
                        counter += process.getBurstTime();
                        process.setTerminationTime(counter);
                        process.setTurnaroundTime(
                                process.getTerminationTime() - process.getArrivalTime());
                        process.setWaitingTime(
                                process.getTurnaroundTime() - process.getBurstTime());
                        pro.setStarted(true);
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
        for (Process process : this.processes) {
            sum += process.getWaitingTime();
        }
        return sum / this.processes.size();
    }

    public double getAverageTurnaround() {
        double sum = 0;
        for (Process process : this.processes) {
            sum += process.getTurnaroundTime();
        }
        return sum / this.processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }
}
