package org.example.algorithms;

import java.util.*;

import org.example.Process;

public class RoundRobin {
    private final List<Process> processes;
    private final Queue<Process> queue = new LinkedList<>();
    private final ArrayList<GanttChartSection> ganttChartData;
    int quantum;

    public RoundRobin(List<Process> processes, int quantum) {
        this.processes = processes;
        this.quantum = quantum;
        ganttChartData = new ArrayList<>();
        execute();
    }

    private void execute() {
        int temp, timeLine = 0;
        boolean flag;

        do {
            temp = quantum;
            nextProcess(processes, timeLine);
            Process x = queue.peek();

            if (x != null) {
                if (!x.getResponseTimeFlag()) {
                    x.setResponseTime(timeLine - x.getArrivalTime());
                    x.setResponseTimeFlag(true);
                }
                if (x.getBurstTime() > temp) {
                    ganttChartData.add(
                            new GanttChartSection(timeLine, timeLine + temp, x.getPName()));
                    x.setBurstTime(x.getBurstTime() - temp);
                    timeLine += temp;
                } else if (x.getBurstTime() > 0) {
                    temp = x.getBurstTime();
                    ganttChartData.add(
                            new GanttChartSection(timeLine, timeLine + temp, x.getPName()));
                    x.setBurstTime(0);
                    timeLine += temp;
                    x.setTurnaroundTime(timeLine - x.getArrivalTime());
                }
                if (!x.getTerminationTimeFlag() && x.getBurstTime() == 0) {
                    x.setTerminationTime(timeLine);
                    x.setTerminationTimeFlag(true);
                }
            } else
                timeLine++;
            flag = doneFlag(processes);
        } while (flag);

        //initializing Waiting Time for each process
        for (Process process : processes) {
            process.setWaitingTime(process.getTurnaroundTime() - process.getCopyBurstTime());
        }
    }

    public double getAvgTurnaroundTime() {
        double result = 0.0;
        for (Process p : processes) {
            result += p.getTurnaroundTime();
        }
        return result / processes.size();
    }

    public double getAvgWaitingTime() {
        double result = 0.0;
        for (Process p : processes) {
            result += p.getWaitingTime();
        }
        return result / processes.size();
    }

    private void nextProcess(List<Process> processes, int timeLine) {
        List<Process> arrayList = new ArrayList<>();

        for (Process p : processes) {
            if (p.getArrivalTime() <= timeLine && !queue.contains(p))
                arrayList.add(p); //adding all processes to the arraylist which are less than or equal to the timeline
        }
        sorting(arrayList); //sorting all the processes in the arraylist in terms of the arrival time (first comes)

        Process j = queue.peek();
        if (!queue.isEmpty())
            queue.poll();
        queue.addAll(arrayList);
        if (j != null && j.getBurstTime() != 0 && !arrayList.contains(j))
            queue.add(j);
    }

    private void sorting(List<Process> arrayList) {
        arrayList.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime())
                return 0;
            return o1.getArrivalTime() < o2.getArrivalTime() ? -1 : 1;
        });
    }

    private boolean doneFlag(List<Process> processList) {
        for (Process p : processList) {
            if (p.getBurstTime() != 0)
                return true;
        }
        return false;
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }

}