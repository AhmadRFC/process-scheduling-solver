package org.example.gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Layout {
    public Layout() {
        String[] optionsToChoose = {"First Come First Serve", "Shortest Job First (preemptive)", "Shortest Job First (non-preemptive)", "Round-Robin", "Priority (preemptive)", "Priority (non-preemptive)"};

        JFrame jFrame = new JFrame();

        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 80, 280, 40);

        JButton jButtonSelectFile = new JButton("Select a file");
        jButtonSelectFile.setBounds(150,200, 130, 30);

        JLabel jLabel = new JLabel("Choose the scheduling algorithm:");
        jLabel.setBounds(84, 22, 400, 100);


        jFrame.add(jComboBox);
        jFrame.add(jButtonSelectFile);
        jFrame.add(jLabel);

        jFrame.setLayout(null);
        jFrame.setSize(450, 300);
        jFrame.setVisible(true);

        jButtonSelectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jButtonSelectFile) {
                    new FileSelection(jComboBox.getItemAt(jComboBox.getSelectedIndex()));
                }
            }
        });

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
