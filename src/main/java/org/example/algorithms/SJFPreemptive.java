package org.example.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import org.example.Process;
public class SJFPreemptive {
    private LinkedList<Process> processes;
    private ArrayList<GanttChartSection> ganttChartData;

    public SJFPreemptive(LinkedList<Process> processes) {
        this.processes = processes;
        ganttChartData = new ArrayList<>();
        Calculate();
    }

    //
    public void Calculate() {
        int counter = 0;
        Process pro = null;
        GanttChartSection last = null;

        LinkedList<Process> readyP = new LinkedList<>();
        for (int i = 0; i < this.processes.size(); i++) {
            readyP.add(new Process(this.processes.get(i)));
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
                    if (readyP.get(i).getBurstTime() < pro.getBurstTime()) {
                        pro = readyP.get(i);
                    } else if (readyP.get(i).getBurstTime() == pro.getBurstTime()
                            && readyP.get(i).getArrivalTime() < pro.getArrivalTime()) {
                        pro = readyP.get(i);
                    }
                }
            }

            if (pro != null) {
                if (!pro.isStarted()) {
                    for (int i = 0; i < this.processes.size(); i++) {
                        if (this.processes.get(i).getPName().matches(pro.getPName())) {
                            this.processes.get(i)
                                    .setResponseTime(
                                            counter - this.processes.get(i).getArrivalTime());
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
                    for (int i = 0; i < this.processes.size(); i++) {
                        if (this.processes.get(i).getPName().matches(pro.getPName())) {
                            this.processes.get(i).setTerminationTime(counter);
                            this.processes.get(i)
                                    .setTurnaroundTime(
                                            counter - this.processes.get(i).getArrivalTime());
                            this.processes.get(i)
                                    .setWaitingTime(this.processes.get(i).getTurnaroundTime()
                                            - this.processes.get(i).getBurstTime());
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
        for (int i = 0; i < this.processes.size(); i++) {
            sum += this.processes.get(i).getWaitingTime();
        }
        return sum / this.processes.size();
    }

    public double getAverageTurnaround() {
        double sum = 0;
        for (int i = 0; i < this.processes.size(); i++) {
            sum += this.processes.get(i).getTurnaroundTime();
        }
        return sum / this.processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }
}
