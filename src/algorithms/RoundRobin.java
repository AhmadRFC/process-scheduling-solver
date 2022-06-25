package algorithms;

import java.util.*;

import processpackage.Prozess;

public class RoundRobin {
    private final List<Prozess> processes;
    private final Queue<Prozess> queue = new LinkedList<>();
    private ArrayList<GanttChartSection> ganttChartData;
    int quantum;

    public RoundRobin(List<Prozess> processes, int quantum) {
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
            Prozess x = queue.peek();

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
        for (Prozess prozess : processes) {
            prozess.setWaitingTime(prozess.getTurnaroundTime() - prozess.getCopyBurstTime());
        }
    }

    public double getAvgTurnaroundTime() {
        double result = 0.0;
        for (Prozess p : processes) {
            result += p.getTurnaroundTime();
        }
        return result / processes.size();
    }

    public double getAvgWaitingTime() {
        double result = 0.0;
        for (Prozess p : processes) {
            result += p.getWaitingTime();
        }
        return result / processes.size();
    }

    private void nextProcess(List<Prozess> processes, int timeLine) {
        List<Prozess> arrayList = new ArrayList<>();

        for (Prozess p : processes) {
            if (p.getArrivalTime() <= timeLine && !queue.contains(p))
                arrayList.add(p); //adding all processes to the arraylist which are less than or equal to the timeline
        }
        sorting(arrayList); //sorting all the processes in the arraylist in terms of the arrival time (first comes)

        Prozess j = queue.peek();
        if (!queue.isEmpty())
            queue.poll();
        queue.addAll(arrayList);
        if (j != null && j.getBurstTime() != 0 && !arrayList.contains(j))
            queue.add(j);
    }

    private void sorting(List<Prozess> arrayList) {
        arrayList.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime())
                return 0;
            return o1.getArrivalTime() < o2.getArrivalTime() ? -1 : 1;
        });
    }

    private boolean doneFlag(List<Prozess> processList) {
        for (Prozess p : processList) {
            if (p.getBurstTime() != 0)
                return true;
        }
        return false;
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }

}