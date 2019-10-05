package com.company;

import javax.swing.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class Graph extends JFrame {
    private JTabbedPane tabPanel;
    private JToolBar toolBar;
    private JButton addGraph;
    private JButton addVertex;
    Vector<mxGraph> graph;

    int active_pane = -1;
    int vertex_count = 0;
    int graph_count = -1;

    public Graph() {
        super("myGraph!");
        CreateGui();
        //getContentPane().add(p);
    }

    public void CreateGui() {
        tabPanel = new JTabbedPane();
        toolBar = new JToolBar();
        //p = new JPanel();
        addGraph = new JButton("Добавить граф");
        addVertex = new JButton("Добавить вершину");
        AddNewGraph action_add_graph = new AddNewGraph();
        AddNewVertex action_add_vertex = new AddNewVertex();
        addGraph.addActionListener(action_add_graph);
        addVertex.addActionListener(action_add_vertex);

        toolBar.add(addGraph);
        toolBar.add(addVertex);
        add(toolBar, BorderLayout.NORTH);
        add(tabPanel, BorderLayout.CENTER);

        graph = new Vector<>();

        tabPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Определяем индекс выделенной мышкой вкладки
                int idx = ((JTabbedPane) e.getSource()).indexAtLocation(e.getX(), e.getY());
                System.out.println("Выбрана вкладка " + idx);
                vertex_count = 0;
                active_pane = idx;
            }
        });

        tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    }

    //нажатие добавить вершину
    public class AddNewVertex implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            vertex_count = 1;
            System.out.println("добавить вершину...");
        }
    }

    public class AddNewGraph implements java.awt.event.ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("touch!");
            graph_count++;
            active_pane = graph_count;
            mxGraph tmpgraph = new mxGraph();
            graph.add(tmpgraph);
//                graph.insertEdge(parent, null, "Дуга", v1, v2);

            mxGraphComponent graphComponent = new mxGraphComponent(graph.elementAt(graph_count));
            graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (vertex_count==1){
                        Object parent = graph.elementAt(active_pane).getDefaultParent();
                        graph.elementAt(active_pane).getModel().beginUpdate();
                        try {
                            Object v1 = graph.elementAt(active_pane).insertVertex(parent, null, "Habra", e.getX(), e.getY(), 50, 50);
                        } finally {
                            graph.elementAt(active_pane).getModel().endUpdate();
                        }
                    }

                }

            });
            String title = "graph" + String.valueOf(graph_count);
            tabPanel.addTab(title, graphComponent);
        }
    }


    public static void main(String[] args) {
        Graph frame = new Graph();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

    }
}
