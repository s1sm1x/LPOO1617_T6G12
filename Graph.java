package com.example.arduinosensors;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import static android.R.attr.colorBackground;
import static android.R.attr.colorForeground;

/**
 * Class responsible for encapsulating the chart and all its definitions
 */
public class Graph {

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private final int pointsOnGraph = 150;

    /**
     * Class constructor
     *
     * @param graphic graph from GraphView library
     */
    public Graph(GraphView graphic) {
        this.graph = graphic;
    }

    /**
     * Initial definition (labels, colors, dynamic window, grid, bounds)
     */
    public void setInitialDefinitions() {
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        series.setBackgroundColor(colorForeground);
        Viewport viewport = graph.getViewport();
        viewport.setMinX(lastX - pointsOnGraph);
        viewport.computeScroll();
        viewport.setXAxisBoundsManual(true);
        viewport.setMaxX(lastX); // para contornar o scrollToEnd
    }

    /**
     * Set graph in pause and reset series
     */
    public void setPauseState() {
        series.resetData(new DataPoint[]{});
        series.setBackgroundColor(colorBackground);
    }

    /**
     * Update graph in Resume mode
     *
     * @param ppgWaveBuffer ArrayList with new points
     */
    public void update(ArrayList<Double> ppgWaveBuffer) {
        series.appendData(new DataPoint(lastX, ppgWaveBuffer.get(0)), true, pointsOnGraph);
        ppgWaveBuffer.remove(0);
        lastX++;
    }
}

