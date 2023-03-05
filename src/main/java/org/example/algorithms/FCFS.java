package org.example.algorithms;

import org.example.Process;

import java.util.ArrayList;
import java.util.Comparator;

public class FCFS {
    private double avgWaitingTime;
    private double avgTurnaroundTime;
    private final ArrayList<GanttChartSection> ganttChartSections = new ArrayList<>();
    public FCFS(ArrayList<Process> processes) {
        if(processes.size() > 0) {
            processes.sort(Comparator.comparingInt(Process::getArrivalTime));
            int timeLine = processes.get(0).getArrivalTime();
            for (Process process : processes) {
                process.setResponseTime(timeLine);
                process.setWaitingTime(timeLine - process.getArrivalTime());
                ganttChartSections.add(
                        new GanttChartSection(timeLine, timeLine + process.getBurstTime(), process.getPName()));
                timeLine += process.getBurstTime();
                process.setTurnaroundTime(timeLine - process.getArrivalTime());
                process.setTerminationTime(timeLine);
            }
            avgWaitingTime = avgWaitingTime(processes);
            avgTurnaroundTime = avgTurnaroundTime(processes);
        }
        else
            System.out.println("No processes in the arrayList");
    }

    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }
    public double getAvgTurnaroundTime() {
        return avgTurnaroundTime;
    }

    private double avgWaitingTime(ArrayList<Process> processes) {
        double avgWaitingTime = 0.0;

        for(Process process: processes) {
            avgWaitingTime += process.getWaitingTime();
        }
        return avgWaitingTime / processes.size();
    }

    private double avgTurnaroundTime(ArrayList<Process> processes) {
        double avgTurnaroundTime = 0.0;

        for(Process process: processes) {
            avgTurnaroundTime += process.getTurnaroundTime();
        }
        return avgTurnaroundTime / processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartSections() {
        return ganttChartSections;
    }
}