package org.example;

import org.example.gui.Layout;
import javax.swing.*;

public class App
{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Unable to set look and feel as windows!");
        }
        new Layout();
    }
}
