package com.company;

import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Graphp {

    private ListenableGraph<String, DefaultEdge> graph;
    private JGraphXAdapter<String, DefaultEdge> gadap;
    private mxGraphComponent graphcomp;
    private ArrayList<JGraphXAdapter> historygraphs;
    private int indexofhistory;
    private Map<Integer, String> styles;
    private mxParallelEdgeLayout layout;

    private mxUndoManager undoManager;

    Graphp() {

        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        graph = new DefaultListenableGraph<>(g);
        gadap = new JGraphXAdapter<String, DefaultEdge>(graph);
        graphcomp = new mxGraphComponent(gadap);
        SetStyle(gadap);
        layout = new mxParallelEdgeLayout(gadap);


        // set default count of steps in history!
        undoManager = new mxUndoManager(10);


        Undo undolistener = new Undo();
        graphcomp.getGraph().getModel().addListener(mxEvent.UNDO, undolistener);
        graphcomp.getGraph().getView().addListener(mxEvent.UNDO, undolistener);

        styles = new HashMap<>();

        styles.put(0, mxConstants.SHAPE_RECTANGLE);
        styles.put(1, mxConstants.SHAPE_ELLIPSE);
        styles.put(2, mxConstants.SHAPE_CYLINDER);
        styles.put(3, mxConstants.SHAPE_TRIANGLE);
        styles.put(4, mxConstants.SHAPE_HEXAGON);
        styles.put(5, mxConstants.SHAPE_RHOMBUS);
        styles.put(6, mxConstants.SHAPE_DOUBLE_ELLIPSE);
        styles.put(7, mxConstants.SHAPE_CLOUD);
        styles.put(8, mxConstants.SHAPE_ACTOR);
        styles.put(9, mxConstants.SHAPE_SWIMLANE);
        styles.put(10, mxConstants.SHAPE_DOUBLE_RECTANGLE);

    }

    public class Undo implements mxEventSource.mxIEventListener {
        @Override
        public void invoke(Object o, mxEventObject mxEventObject) {
            undoManager.undoableEditHappened((mxUndoableEdit) mxEventObject.getProperty("edit"));
            layout.execute(graphcomp.getGraph().getDefaultParent());
        }
    }

    // go to one step back
    public void undo() {
        undoManager.undo();
    }

    // go to one step front
    public void redo() {
        undoManager.redo();
    }

    // add vertex
    public void AddVertex(int x, int y) {
        Object parent = graphcomp.getGraph().getDefaultParent();
        graphcomp.getGraph().getModel().beginUpdate();
        try {
            Object v1 = graphcomp.getGraph().insertVertex(parent, null, "", x, y, 50, 50);

        } finally {
            graphcomp.getGraph().getModel().endUpdate();
        }
        System.out.println("added vertex");
    }

    // remove selected vertex
    public void RemoveVertex(int x, int y) {
        Object parent = graphcomp.getGraph().getDefaultParent();
        graphcomp.getGraph().getModel().beginUpdate();

        Object g = graphcomp.getCellAt(x, y);//gcomp.getCellAt(x, y);
        graphcomp.getGraph().removeCells(new Object[]{g});

        graphcomp.getGraph().getModel().endUpdate();
        System.out.println("remove vertex");
    }

    // remove selected edge
    public void RemoveEdge(int x, int y) {
        graphcomp.getGraph().getModel().beginUpdate();

        Object g = graphcomp.getCellAt(x, y);//gcomp.getCellAt(x, y);
        graphcomp.getGraph().removeCells(new Object[]{g});

        graphcomp.getGraph().getModel().endUpdate();
        System.out.println("remove edge");
    }


    public mxGraphComponent getComp() {
        return graphcomp;
    }

    public void SetStyle(mxGraph gr) {

        gr.setAllowLoops(true);
        gr.setAllowDanglingEdges(false);
        gr.setCellsCloneable(true);

        // set default edge style
        Map<String, Object> edge = new HashMap<>();
        //edge.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_TOPTOBOTTOM);
        edge.put(mxConstants.STYLE_ROUNDED, true);
        edge.put(mxConstants.STYLE_ORTHOGONAL, true);
        edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        edge.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
        edge.put(mxConstants.STYLE_FONTCOLOR, "#430000");
       // edge.put(mxConstants.STYLE_DIRECTION, "ssss");
        //edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CURVE);

        // set default vertex style
        Map<String, Object> vertex = new HashMap<>();
        vertex.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        vertex.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        vertex.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        vertex.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        vertex.put(mxConstants.STYLE_FILLCOLOR, "#9ACD32");
        vertex.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        vertex.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        mxStylesheet style = new mxStylesheet();

        style.setDefaultVertexStyle(vertex);
        style.setDefaultEdgeStyle(edge);

        gr.setStylesheet(style);
    }

    public void SetStyleForVertex(int x, int y, int choiceStyle) {
        graphcomp.getGraph().getModel().beginUpdate();
        Object g = graphcomp.getCellAt(x, y);
        graphcomp.getGraph().setCellStyles(mxConstants.STYLE_SHAPE, styles.get(choiceStyle), new Object[]{g});
        graphcomp.refresh();
        graphcomp.getGraph().getModel().endUpdate();
    }
    public void SetColorVertex(int x, int y, String hex){
        graphcomp.getGraph().getModel().beginUpdate();
        Object g = graphcomp.getCellAt(x, y);

        graphcomp.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, hex, new Object[]{g});
        graphcomp.refresh();

        graphcomp.getGraph().getModel().endUpdate();
    }
    public void SetColorEdge(int x, int y, String hex){
        graphcomp.getGraph().getModel().beginUpdate();
        Object g = graphcomp.getCellAt(x, y);
        graphcomp.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, hex, new Object[]{g});
        graphcomp.refresh();

        graphcomp.getGraph().getModel().endUpdate();
    }
    public void AddLoop(int x, int y){
        graphcomp.getGraph().getModel().beginUpdate();

        Object g = graphcomp.getCellAt(x, y);
        Object parent = graphcomp.getGraph().getDefaultParent();
        graphcomp.getGraph().insertEdge(parent, null, "", g, g);
        graphcomp.refresh();

        graphcomp.getGraph().getModel().endUpdate();
    }
   public void ChangeDirectionOff(int x, int y){
       graphcomp.getGraph().getModel().beginUpdate();
       Object g = graphcomp.getCellAt(x, y);
       graphcomp.getGraph().setCellStyles(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR, new Object[]{g});
       graphcomp.getGraph().setCellStyles(mxConstants.STYLE_ENDARROW, "ss", new Object[]{g});
       graphcomp.refresh();

       graphcomp.getGraph().getModel().endUpdate();
  }

    public void ChangeDirectionOn(int x, int y){
        graphcomp.getGraph().getModel().beginUpdate();
        Object g = graphcomp.getCellAt(x, y);
        graphcomp.getGraph().setCellStyles(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR, new Object[]{g});
        graphcomp.getGraph().setCellStyles(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC, new Object[]{g});
        graphcomp.refresh();

        graphcomp.getGraph().getModel().endUpdate();
    }
}
