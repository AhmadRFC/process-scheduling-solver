package org.example.algorithms;

import org.example.Process;

import java.util.ArrayList;
import java.util.LinkedList;

public class PriorityPreemptive {

    private final LinkedList<Process> Processes;
    private final ArrayList<GanttChartSection> ganttChartData;

    public PriorityPreemptive(LinkedList<Process> Processes) {
        this.Processes = Processes;
        ganttChartData = new ArrayList<>();
        Calculate();
    }

    //
    public void Calculate() {
        int counter = 0;
        Process pro = null;
        GanttChartSection last = null;
        LinkedList<Process> readyP = new LinkedList<>();
        for (Process process : this.Processes) {
            readyP.add(new Process(process));
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
                    if (pro != null && process.getPriority() < pro.getPriority()) {
                        pro = process;
                    }
                }
            }

            if (pro != null) {
                if (!pro.isStarted()) {
                    for (Process process : this.Processes) {
                        if (process.getPName().matches(pro.getPName())) {
                            process.setResponseTime(counter - process.getArrivalTime());
                            pro.setStarted(true);
                        }
                    }
                }
                //gantt chart implementation
                if (last == null) {
                    GanttChartSection temp =
                            new GanttChartSection(counter, counter + 1, pro.getPName());
                    ganttChartData.add(temp);
                    last = temp;
                } else if (pro.getPName().equals(last.getPName())) {
                    last.setEndTime(last.getEndTime() + 1);
                } else {
                    GanttChartSection temp =
                            new GanttChartSection(counter, counter + 1, pro.getPName());
                    ganttChartData.add(temp);
                    last = temp;
                }

                pro.setBurstTime(pro.getBurstTime() - 1);
                counter++;
                if (pro.getBurstTime() == 0) {
                    for (Process process : this.Processes) {
                        if (process.getPName().matches(pro.getPName())) {
                            process.setTerminationTime(counter);
                            process.setTurnaroundTime(counter - process.getArrivalTime());
                            process.setWaitingTime(
                                    process.getTurnaroundTime() - process.getBurstTime());
                            for (int j = 0; j < readyP.size(); j++) {
                                if (readyP.get(j).getPName().matches(pro.getPName())) {
                                    readyP.remove(j);
                                    break;
                                }
                            }
                            break;
                        }
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
