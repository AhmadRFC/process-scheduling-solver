package org.example.algorithms;

import org.example.Process;

import java.util.ArrayList;
import java.util.LinkedList;

public class PriorityNonPreemptive {

    private LinkedList<Process> Processes;
    private ArrayList<GanttChartSection> ganttChartData;

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
        for (int i = 0; i < this.Processes.size(); i++) {
            readyP.add(new Process(this.Processes.get(i)));
        }
        while (!readyP.isEmpty()) {

            if (readyP.get(0).getArrivalTime() <= counter) {
                pro = readyP.get(0);
            }

            for (int i = 0; i < readyP.size(); i++) {
                if (readyP.get(i).getArrivalTime() <= counter) {
                    pro = readyP.get(i);
                    break;
                }
            }


            for (int i = 0; i < readyP.size(); i++) {
                if (readyP.get(i).getArrivalTime() <= counter) {
                    if (readyP.get(i).getPriority() < pro.getPriority()) {
                        pro = readyP.get(i);
                    } else if (readyP.get(i).getPriority() == pro.getPriority()
                            && readyP.get(i).getArrivalTime() < pro.getArrivalTime()) {
                        pro = readyP.get(i);
                    }
                }
            }
            if (pro != null) {
                for (int i = 0; i < this.Processes.size(); i++) {
                    if (this.Processes.get(i).getPName().matches(pro.getPName())) {
                        //gantt chart implementation
                        GanttChartSection temp =
                                new GanttChartSection(counter, counter + pro.getBurstTime(), pro.getPName());
                        ganttChartData.add(temp);

                        this.Processes.get(i)
                                .setResponseTime(counter - this.Processes.get(i).getArrivalTime());
                        counter += this.Processes.get(i).getBurstTime();
                        this.Processes.get(i).setTerminationTime(counter);
                        this.Processes.get(i)
                                .setTurnaroundTime(this.Processes.get(i).getTerminationTime()
                                        - this.Processes.get(i).getArrivalTime());
                        this.Processes.get(i)
                                .setWaitingTime(this.Processes.get(i).getTurnaroundTime()
                                        - this.Processes.get(i).getBurstTime());
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
        for (int i = 0; i < this.Processes.size(); i++) {
            sum += this.Processes.get(i).getWaitingTime();
        }
        return sum / this.Processes.size();
    }

    public double getAverageTurnaround() {
        double sum = 0;
        for (int i = 0; i < this.Processes.size(); i++) {
            sum += this.Processes.get(i).getTurnaroundTime();
        }
        return sum / this.Processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }
}

