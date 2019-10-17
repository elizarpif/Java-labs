package com.company;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.util.Hashtable;
import java.util.Map;

public class Main extends JFrame {

    mxGraph graph = new mxGraph();

    public Main() {
        super("myGraph!");
        setStyle();
    }

    //установить стиль вершины
    protected Map<String, Object> setVertexStyle(int figureVertex, int colorVertex) //figure - 0-6, color - 0-3
    {
        Map<String, Object> style = new Hashtable<String, Object>();

        switch (figureVertex) {
            case 0:
                style.put(mxConstants.STYLE_SHAPE, "rectangle");
                break;
            case 1:
                style.put(mxConstants.STYLE_SHAPE, "ellipse");
                break;
            case 2:
                style.put(mxConstants.STYLE_SHAPE, "cylinder");
                break;
            case 3:
                style.put(mxConstants.STYLE_SHAPE, "triangle");
                break;
            case 4:
                style.put(mxConstants.STYLE_SHAPE, "hexagon");
                break;
            case 5:
                style.put(mxConstants.STYLE_SHAPE, "rhombus");
                break;
            case 6:
                style.put(mxConstants.STYLE_SHAPE, "doubleEllipse");
                break;
        }

        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);

        switch (colorVertex) {
            case 0:
                style.put(mxConstants.STYLE_FILLCOLOR, "#FFB6C1");
                break;
            case 1:
                style.put(mxConstants.STYLE_FILLCOLOR, "#AFEEEE");
                break;
            case 2:
                style.put(mxConstants.STYLE_FILLCOLOR, "#FFA500");
                break;
            case 3:
                style.put(mxConstants.STYLE_FILLCOLOR, "#9ACD32");
                break;
        }

        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        return style;
    }

    //установить стиль ребра
    protected Map<String, Object> setEdgeStyle(int choiceStyleEdge) //0-3, better - 2
    {
        Map<String, Object> edge = new Hashtable<String, Object>();
        edge.put(mxConstants.STYLE_ROUNDED, true);
        edge.put(mxConstants.STYLE_ORTHOGONAL, false);

        switch (choiceStyleEdge) {
            case 0:
                edge.put(mxConstants.STYLE_EDGE, "horizontalEdgeStyle");
                break;
            case 1:
                edge.put(mxConstants.STYLE_EDGE, "orthogonalEdgeStyle");
                break;
            case 2:
                edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
                break;
            case 3:
                edge.put(mxConstants.STYLE_EDGE, "entityEdgeStyle");
                break;
        }

        edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        edge.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
        edge.put(mxConstants.STYLE_FONTCOLOR, "#430000");

        return edge;
    }

    public void setStyle() {

        Object parent = graph.getDefaultParent();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);

        graph.setAllowDanglingEdges(false);
        graph.setAllowLoops(true);

        Map<String, Object> edge = setEdgeStyle(2);
        Map<String, Object> vertex = setVertexStyle(2, 0);

        mxStylesheet style = new mxStylesheet();

        style.setDefaultVertexStyle(vertex);
        style.setDefaultEdgeStyle(edge);

        graph.setStylesheet(style);

        //проверка на отрисовке графов
        Object o1 = graph.insertVertex(parent, null, "муха", 20, 15, 50, 50);
        Object o2 = graph.insertVertex(parent, null, "муха2", 90, 75, 50, 50);

        graph.insertEdge(parent, null, "", o1, o2);
        add(graphComponent);
    }

    public static void main(String[] args) {
        // write your code here
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
