package gui.tableresults;

import algorithms.GanttChartSection;
import gui.ganttchart.gantChartPainter;
import processpackage.Prozess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TableResults extends JFrame implements ActionListener {
    private final JPanel panel;

    private final GridBagConstraints gbc;
    private final GridBagLayout layout;
    private final JButton closeButton;
    private final JButton ganttChart;
    ArrayList<GanttChartSection> ganttChartData;

    public TableResults(String title, ArrayList<Prozess> processes,
            ArrayList<GanttChartSection> ganttChartData, double avgTurnaroundTime,
            double avgWaitingTime) {
        super(title);
        this.ganttChartData = ganttChartData;
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 7, 3);
        layout = new GridBagLayout();

        panel = new JPanel();
        panel.setLayout(layout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Container pane = getContentPane();
        pane.add(panel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new GridLayout(1, 0));
        tablePanel.setOpaque(true);
        DataSet dataSet = new DataSet();
        TableResults.sampleData(dataSet, processes);

        JTable table = new JTable(dataSet);
        table.setPreferredScrollableViewportSize(new Dimension(850, 130));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        tablePanel.add(scrollPane);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        addComponent(tablePanel, 0, 0, 5, 1);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(closeButton, 2, 3, 4, 1);

        ganttChart = new JButton("Gantt chart");
        ganttChart.addActionListener(this);
        addComponent(ganttChart, 2, 0, 2, 1);

        JLabel avgTurnaroundTimeLabel = new JLabel("Average Turnaround time: " + avgTurnaroundTime);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(avgTurnaroundTimeLabel, 1, 0, 2, 1);

        JLabel avgWaitingTimeLabel = new JLabel("Average Waiting time: " + avgWaitingTime);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(avgWaitingTimeLabel, 1, 2, 2, 1);
    }

    private void addComponent(Component component, int row, int column, int width, int height) {
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        layout.setConstraints(component, gbc);
        panel.add(component);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            super.dispose();
        } else if (e.getSource() == ganttChart) {
            gantChartPainter gantChartPainter = new gantChartPainter("Gantt Chart", ganttChartData);
            gantChartPainter.setSize(750, 400);
            gantChartPainter.setVisible(true);
            gantChartPainter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    public static void sampleData(DataSet data, ArrayList<Prozess> processes) {
        data.addColumn(new ColumnInfo("Processes Names", ColumnType.String));
        data.addColumn(new ColumnInfo("Arrival Times", ColumnType.Integer));
        data.addColumn(new ColumnInfo("Burst Times", ColumnType.Integer));
        data.addColumn(new ColumnInfo("Termination Times", ColumnType.Integer));
        data.addColumn(new ColumnInfo("Turnaround Times", ColumnType.Integer));
        data.addColumn(new ColumnInfo("Waiting Times", ColumnType.Integer));
        data.addColumn(new ColumnInfo("Response Times", ColumnType.Integer));

        for (int i = 0; i < processes.size(); i++) {
            data.addRow();
        }

        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getPName(), i, 0);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getArrivalTime(), i, 1);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getCopyBurstTime(), i, 2);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getTerminationTime(), i, 3);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getTurnaroundTime(), i, 4);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getWaitingTime(), i, 5);
        }
        for (int i = 0; i < data.getRowCount(); i++) {
            data.setValueAt(processes.get(i).getResponseTime(), i, 6);
        }

    }
}
