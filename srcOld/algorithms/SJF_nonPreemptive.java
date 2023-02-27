package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import processpackage.Prozess;

public class SJF_nonPreemptive {
    private LinkedList<Prozess> processes;
    private ArrayList<GanttChartSection> ganttChartData;

    public SJF_nonPreemptive(LinkedList<Prozess> processes) {
        this.processes = processes;
        ganttChartData = new ArrayList<>();
        Calculate();
    }

    //
    public void Calculate() {
        int counter = 0;
        int max = 0;
        Prozess pro = null;
        LinkedList<Prozess> readyP = new LinkedList<>();
        for (int i = 0; i < this.processes.size(); i++) {
            readyP.add(new Prozess(this.processes.get(i)));
            max += readyP.get(i).getBurstTime();
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
                //gantt chart implementation
                GanttChartSection temp =
                        new GanttChartSection(counter, counter + pro.getBurstTime(),
                                pro.getPName());
                ganttChartData.add(temp);
                //
                for (int i = 0; i < this.processes.size(); i++) {
                    if (this.processes.get(i).getPName().matches(pro.getPName())) {
                        this.processes.get(i)
                                .setResponseTime(counter - this.processes.get(i).getArrivalTime());
                        counter += this.processes.get(i).getBurstTime();
                        this.processes.get(i).setTerminationTime(counter);
                        this.processes.get(i)
                                .setTurnaroundTime(this.processes.get(i).getTerminationTime()
                                        - this.processes.get(i).getArrivalTime());
                        this.processes.get(i)
                                .setWaitingTime(this.processes.get(i).getTurnaroundTime()
                                        - this.processes.get(i).getBurstTime());
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
