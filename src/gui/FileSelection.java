package gui;

import algorithms.*;
import gui.tableresults.TableResults;
import processpackage.Prozess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FileSelection extends JFrame implements ActionListener {
    private JButton openButton;
    private JButton okButton;
    private JTextArea fileContentArea;
    private String algorithm;
    private ArrayList<Prozess> processes = new ArrayList<>();
    private ArrayList<GanttChartSection> ganttChartData = new ArrayList<>();
    private int quantum;
    double avgTurnaroundTime = 0.0;
    double avgWaitingTime = 0.0;

    public FileSelection(String algorithm) { //constructor
        super();
        this.algorithm = algorithm;

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);

        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());
        toolPanel.setMaximumSize(new Dimension(640, 50));
        basePanel.add(toolPanel);

        openButton = new JButton("Open a file");
        openButton.addActionListener(this);
        openButton.setMnemonic(KeyEvent.VK_O);
        toolPanel.add(openButton);

        okButton = new JButton("Ok");
        okButton.addActionListener(this);
        okButton.setMnemonic(KeyEvent.VK_S);
        toolPanel.add(okButton);

        fileContentArea = new JTextArea("");
        if (algorithm.equals("Round-Robin")) {
            fileContentArea.setText("Expected file pattern is as follows:\n" + "int(Quantum)\n"
                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                    + "e.g.\n"
                    + "3\nP1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1\n\n**note:\nPriority value will be ignored for the specified scheduling algorithm\nregardless if it's found or not.");
        } else if (algorithm.equals("Priority")) {
            fileContentArea.setText("Expected file pattern is as follows:\n"
                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                    + "e.g.\n" + "P1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1");
        } else {
            fileContentArea.setText("Expected file pattern is as follows:\n"
                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                    + "e.g.\n"
                    + "P1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1\n\n**note:\nPriority value will be ignored for the specified scheduling algorithm\nregardless if it's found or not.");
        }
        JScrollPane textScrollPane = new JScrollPane(fileContentArea);
        textScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        basePanel.add(textScrollPane);

        //pane.add(toolContainer, BorderLayout.PAGE_START);

        setTitle("File Selector");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLayout(new BorderLayout());
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            performOpen();
        } else if (e.getSource() == okButton) {
            performOk(algorithm, processes);
            super.dispose();
        }
    }

    //performOpen
    private void performOpen() {
        JFileChooser openChooser = new JFileChooser("Open Text File");
        int i = openChooser.showOpenDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            fileContentArea.setText("");
            FileReader reader = null;
            Scanner sc = null;
            try {
                reader = new FileReader(openChooser.getSelectedFile());
                sc = new Scanner(reader);
                StringBuilder builder = new StringBuilder();
                if (algorithm.equals("Round-Robin")) {
                    quantum = sc.nextInt();
                    sc.nextLine();
                    extractingInput(sc, builder, quantum);
                } else {
                    extractingInput(sc, builder, 0);
                }
                fileContentArea.setText(builder.toString());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                if (algorithm.equals("Round-Robin")) {
                    fileContentArea.setText(
                            "******\n******\n******\nTry again, make sure the selected file follows this pattern:\n\n"
                                    + "int(Quantum)\n"
                                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                                    + "e.g.\n"
                                    + "3\nP1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1\n\n**note:\nPriority value will be ignored for the specified scheduling algorithm\nregardless if it's found or not.");
                } else if (algorithm.equals("Priority")) {
                    fileContentArea.setText(
                            "******\n******\n******\nTry again, make sure the selected file follows this pattern:\n\n"
                                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                                    + "e.g.\n" + "P1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1");
                } else {
                    fileContentArea.setText(
                            "***Ops***\n***Ops***\n***Ops***\nTry again, make sure the selected file follows this pattern:\n\n"
                                    + "String(Process Name), int(Arrival Time), int(Burst Time), int(Priority)\n\n"
                                    + "e.g.\n"
                                    + "P1, 0, 5, 1\nP2, 1, 3, 2\nP3, 3, 8, 1\n\n**note:\nPriority value will be ignored for the specified scheduling algorithm\nregardless if it's found or not.");
                }
            } finally {
                if (sc != null) {
                    sc.close();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void extractingInput(Scanner sc, StringBuilder builder, int quantum) {
        if (quantum > 0) {
            builder.append("Quantum: ").append(quantum).append("\n");
        }
        String temp;
        String[] tempArray;

        while (sc.hasNextLine()) {
            temp = sc.nextLine();
            builder.append(temp);
            temp = temp.strip();
            tempArray = temp.split((", "));
            Prozess process;
            if (tempArray.length > 3) {
                process = new Prozess(tempArray[0], Integer.parseInt(tempArray[1]),
                        Integer.parseInt(tempArray[2]), Integer.parseInt(tempArray[3]));
            } else {
                process = new Prozess(tempArray[0], Integer.parseInt(tempArray[1]),
                        Integer.parseInt(tempArray[2]));
            }
            processes.add(process);
            builder.append(sc.hasNextLine() ? "\n" : "");
        }
    }

    private void performOk(String algorithm, ArrayList<Prozess> processes) {
        LinkedList<Prozess> processesLinkedList = new LinkedList<>(processes);
        switch (algorithm) {
            case "First Come First Serve":
                FCFS fcfs = new FCFS(processes);
                avgTurnaroundTime = fcfs.getAvgTurnaroundTime();
                avgWaitingTime = fcfs.getAvgWaitingTime();
                ganttChartData = fcfs.getGanttChartSections();
                break;
            case "Shortest Job First (non-preemptive)":
                SJF_nonPreemptive sjf_nonPreemptive = new SJF_nonPreemptive(processesLinkedList);
                avgTurnaroundTime = sjf_nonPreemptive.getAverageTurnaround();
                avgWaitingTime = sjf_nonPreemptive.getAverageWaiting();
                ganttChartData = sjf_nonPreemptive.getGanttChartData();
                break;
            case "Shortest Job First (preemptive)":
                SJF_Preemptive sjf_preemptive = new SJF_Preemptive(processesLinkedList);
                avgTurnaroundTime = sjf_preemptive.getAverageTurnaround();
                avgWaitingTime = sjf_preemptive.getAverageWaiting();
                ganttChartData = sjf_preemptive.getGanttChartData();
                break;
            case "Round-Robin":
                RoundRobin roundRobin = new RoundRobin(processes, quantum);
                avgTurnaroundTime = roundRobin.getAvgTurnaroundTime();
                avgWaitingTime = roundRobin.getAvgWaitingTime();
                ganttChartData = roundRobin.getGanttChartData();
                break;
            case "Priority (non-preemptive)":
                PriorityNonPreemptive priorityNonPreemptive = new PriorityNonPreemptive(processesLinkedList);
                avgTurnaroundTime = priorityNonPreemptive.getAverageTurnaround();
                avgWaitingTime = priorityNonPreemptive.getAverageWaiting();
                ganttChartData = priorityNonPreemptive.getGanttChartData();
                break;
            case "Priority (preemptive)":
                PriorityPreemptive priorityPreemptive = new PriorityPreemptive(processesLinkedList);
                avgTurnaroundTime = priorityPreemptive.getAverageTurnaround();
                avgWaitingTime = priorityPreemptive.getAverageWaiting();
                ganttChartData = priorityPreemptive.getGanttChartData();
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                TableResults tableResults =
                        new TableResults("Detailed Results", processes, ganttChartData,
                                avgTurnaroundTime, avgWaitingTime);
                tableResults.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //Display the window.
                tableResults.pack();
                tableResults.setVisible(true);
            }
        });
    }
}