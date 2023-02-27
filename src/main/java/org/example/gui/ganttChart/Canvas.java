package org.example.gui.ganttChart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Canvas extends JPanel implements ComponentListener {

    private Equation equation;

    public Canvas() {
        super();
        addComponentListener(this);
    }

    public void setEquation(Equation equation) {
        this.equation = equation;
        if (this.equation != null) {
            this.equation.reset();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (equation != null) {

            if (!equation.isReady()) {
                equation.init(g, this);
            }
            equation.paint();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (equation != null) {
            equation.reset();
        }
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
