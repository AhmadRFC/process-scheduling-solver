package gui.ganttchart;

import algorithms.GanttChartSection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class gantChartPainter extends JFrame {

    private JPanel panel;
    private Canvas canvas;
    private GridBagConstraints gbc;
    private GridBagLayout layout;
    private GanttChartEquation gantt_chart;

    public gantChartPainter(String title, ArrayList<GanttChartSection> ganttChartData) {
        super(title);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        layout = new GridBagLayout();

        panel = new JPanel();
        panel.setLayout(layout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Container pane = getContentPane();
        pane.add(panel, BorderLayout.CENTER);

        canvas = new Canvas();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        addComponent(canvas, 0, 0, 6, 1);



        gantt_chart = new GanttChartEquation(23);


        sorting(ganttChartData);

        for (int i = 0; i < ganttChartData.size(); i++) {
            gantt_chart.add(ganttChartData.get(i));
        }

        canvas.setEquation(gantt_chart);
    }

    private void addComponent(Component component, int row, int column, int width, int height) {
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        layout.setConstraints(component, gbc);
        panel.add(component);
    }

    private void sorting(ArrayList<GanttChartSection> arrayList) {
        arrayList.sort((o1, o2) -> {
            if (o1.getBeginTime() == o2.getBeginTime())
                return 0;
            return o1.getBeginTime() < o2.getBeginTime() ? -1 : 1;
        });
    }

}
