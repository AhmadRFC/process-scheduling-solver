package processpackage;

public class Prozess implements Comparable<Prozess> {
    private String pName;
    private int arrivalTime;
    private int burstTime;
    private int copyBurstTime;
    private int waitingTime;
    private int turnaroundTime;
    private int terminationTime;
    private boolean terminationTimeFlag;
    private int responseTime;
    private boolean responseTimeFlag;
    private boolean started;
    private int priority;


    public Prozess(String pName, int arrivalTime, int burstTime) {
        this.pName = pName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.copyBurstTime = burstTime;
    }

    public Prozess(String pName, int arrivalTime, int burstTime, int priority) {
        this(pName, arrivalTime, burstTime);
        this.priority = priority;
    }

    public Prozess(Prozess pro) {
        this.pName = pro.getPName();
        this.arrivalTime = pro.getArrivalTime();
        this.burstTime = pro.getBurstTime();
        this.priority = pro.getPriority();
        this.copyBurstTime = burstTime;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getCopyBurstTime() {
        return this.copyBurstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return this.waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return this.turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getTerminationTime() {
        return this.terminationTime;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public int getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getTerminationTimeFlag() {
        return this.terminationTimeFlag;
    }

    public void setTerminationTimeFlag(boolean terminationTimeFlag) {
        this.terminationTimeFlag = terminationTimeFlag;
    }

    public boolean getResponseTimeFlag() {
        return this.responseTimeFlag;
    }

    public void setResponseTimeFlag(boolean responseTimeFlag) {
        this.responseTimeFlag = responseTimeFlag;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public String toString() {
        return "pName='" + pName + '\'' + ", arrivalTime=" + arrivalTime
                + ", burstTime=" + copyBurstTime + ", waitingTime=" + waitingTime + ", turnaroundTime="
                + turnaroundTime + ", terminationTime=" + terminationTime + ", responseTime="
                + responseTime;
    }

    @Override
    public int compareTo(Prozess o) {
        return Integer.compare(this.arrivalTime, o.getArrivalTime());
    }
}
