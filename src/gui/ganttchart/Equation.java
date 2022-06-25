package gui.ganttchart;

import javax.swing.*;
import java.awt.*;

public abstract class Equation {
    protected double scale;
    protected Graphics g;
    protected int width;
    protected int height;

    public Equation(double scale) {
        this.scale = scale;
    }

    public boolean isReady() {
        return g != null;
    }

    public void reset() {
        g = null;
    }

    public void init(Graphics g, JPanel panel) {
        this.g = g;
        width = panel.getWidth();
        height = panel.getHeight();
    }

    protected double minX() {
        return -(width / 2) / scale;
    }

    protected double scaler(double v) {
        return v * scale;
    }


    protected int convertX(double x) {
        x = scaler(x);
        x = x + width / 2;

        return (int) x;
    }

    protected int convertY(double y) {
        y = scaler(y);
        y = height / 2 - y;

        return (int) y;
    }

    protected void drawLine(double x1, double y1, double x2, double y2) {
        g.drawLine(convertX(x1), convertY(y1), convertX(x2), convertY(y2));
    }

    protected void drawCenteredString(String str, double x, double y) {
        FontMetrics metrics = g.getFontMetrics();
        double w = metrics.stringWidth(str) / scale;
        double h = metrics.getHeight() / scale;
        drawCenteredString(str, x - w / 2, y, w, h);
    }

    protected void drawCenteredString(String str, double x, double y, double w, double h) {
        FontMetrics metrics = g.getFontMetrics();
        double _x, _y;
        if (w < metrics.stringWidth(str) / scale) {
            _x = x + (metrics.stringWidth(str) / scale) / 2.0;
        } else {
            _x = x + (w - metrics.stringWidth(str) / scale) / 2.0;
        }
        if (h < metrics.getHeight() / scale) {
            _y = y + (metrics.getHeight() / scale) / 2.0;
        } else {
            _y = y + (h - metrics.getHeight() / scale) / 2.0;
        }

        g.drawString(str, convertX(_x), convertY(_y));
    }

    public abstract void paint();
}
