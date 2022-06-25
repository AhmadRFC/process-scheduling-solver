package algorithms;

public class GanttChartSection {
    private int beginTime;
    private int endTime;
    private String pName;

    public GanttChartSection(int beginTime, int endTime, String pName) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.pName = pName;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getPName() {
        return pName;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}