package gui.ganttchart;

import algorithms.GanttChartSection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GanttChartEquation extends Equation {

    private List<GanttChartSection> items;

    public GanttChartEquation(double scale) {
        super(scale);
        items = new ArrayList<>();
    }

    public void add(GanttChartSection ganttChartSection) {
        items.add(ganttChartSection);
    }

    @Override
    public void paint() {
        g.setColor(Color.RED);

        double init_x = minX() + 1;
        double max_x = init_x;
        drawLine(init_x, 0, init_x, -1);
        drawCenteredString("0", init_x, -2.5);

        for (GanttChartSection item : items) {
            double x_b = init_x + item.getBeginTime();
            double x_e = init_x + item.getEndTime();
            max_x = Math.max(init_x, x_e);
            drawLine(x_b, 0, x_b, -1);
            drawCenteredString(item.getBeginTime() + "", x_b, -2.5);
            drawLine(x_e, 0, x_e, -1);
            drawCenteredString(item.getEndTime() + "", x_e, -2.5);
            drawCenteredString(item.getPName(), x_b, 0.0, x_e - x_b, 1);
        }

        drawLine(init_x, 0, max_x, 0);
    }

}