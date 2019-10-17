package com.company;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;


public class Graph extends JFrame {
    JMenuBar menuBar;
    JTabbedPane tabPanel;
    JToolBar toolBar;

    JButton saveGraph;

    JButton addGraph;
    JButton removeVertex;
    JButton addVertex;
    int idEdge = -1;

    Vector<mxGraph> graph;

    int active_pane = -1;
    int action = 0; //0 - null, 1 - add vertex, 2 - remove vertex
    int graph_count = -1;

    public Graph() {
        super("myGraph!");
        CreateGui();
    }



    public void CreateGui() {

        Font font = new Font("Verdana", Font.PLAIN, 11);
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);

        JMenu newMenu = new JMenu("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);

        JMenuItem txtFileItem = new JMenuItem("Text file");
        txtFileItem.setFont(font);
        newMenu.add(txtFileItem);

        JMenuItem imgFileItem = new JMenuItem("Image file");
        imgFileItem.setFont(font);
        newMenu.add(imgFileItem);

        JMenuItem folderItem = new JMenuItem("Folder");
        folderItem.setFont(font);
        newMenu.add(folderItem);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setFont(font);
        fileMenu.add(openItem);

        JMenuItem closeItem = new JMenuItem("Close");
        closeItem.setFont(font);
        fileMenu.add(closeItem);

        JMenuItem closeAllItem = new JMenuItem("Close all");
        closeAllItem.setFont(font);
        fileMenu.add(closeAllItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(fileMenu);

        tabPanel = new JTabbedPane();
        toolBar = new JToolBar();

        addGraph = new JButton("Добавить граф");
        addVertex = new JButton("Добавить вершину");
        removeVertex = new JButton("Удалить вершину");
        saveGraph = new JButton("Сохранить");



        AddNewGraph action_add_graph = new AddNewGraph();
        AddNewVertex action_add_vertex = new AddNewVertex();
        RemoveVertex action_remove_vertex = new RemoveVertex();

        addGraph.addActionListener(action_add_graph);
        addVertex.addActionListener(action_add_vertex);
        removeVertex.addActionListener(action_remove_vertex);


        toolBar.add(addGraph);
        toolBar.add(addVertex);
        toolBar.add(removeVertex);

        toolBar.setFont(new Font("TimesRoman", Font.BOLD, 22));

        add(menuBar, BorderLayout.NORTH);
        add(toolBar, BorderLayout.SOUTH);
        add(tabPanel, BorderLayout.CENTER);

        graph = new Vector<>();

        tabPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Определяем индекс выделенной мышкой вкладки
                int idx = ((JTabbedPane) e.getSource()).indexAtLocation(e.getX(), e.getY());
                System.out.println("Выбрана вкладка " + idx);
                action = 0;
                active_pane = idx;
            }
        });

        tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    }

    //нажатие - добавить вершину
    public class AddNewVertex implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            action = 1;
            System.out.println("добавить вершину...");
        }
    }

    //нажатие - удалить вершину
    public class RemoveVertex implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            action = 2;
            System.out.println("удалить вершину...");
        }
    }

    public void setStyle(mxGraph gr) {
        Map<String, Object> edge = setEdgeStyle(active_pane % 4);
        Map<String, Object> vertex = setVertexStyle(active_pane % 6, active_pane % 4);

        mxStylesheet style = new mxStylesheet();

        style.setDefaultVertexStyle(vertex);
        style.setDefaultEdgeStyle(edge);

        gr.setStylesheet(style);
    }

    public class AddNewGraph implements java.awt.event.ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            graph_count++;
            active_pane = graph_count; //чтобы не было случайно на 0 указателя

            mxGraph tmpgraph = new mxGraph();

            tmpgraph.setAllowDanglingEdges(false); //нельзя висячие вершины
            tmpgraph.setAllowLoops(true); //можно петли
            setStyle(tmpgraph);

            graph.add(tmpgraph);

            mxGraphComponent graphComponent = new mxGraphComponent(graph.elementAt(graph_count));

            graphComponent.getGraphControl().addMouseListener(new MouseAdapter() { //обработка нажатий
                @Override
                public void mouseReleased(MouseEvent e) {

                    if (action == 1) { //если выбрано "добавить вершину"
                        Object parent = graph.elementAt(active_pane).getDefaultParent();
                        graph.elementAt(active_pane).getModel().beginUpdate();
                        try {
                            Object v1 = graph.elementAt(active_pane).insertVertex(parent, null, String.valueOf(graph_count), e.getX(), e.getY(), 50, 50);
                        } finally {
                            graph.elementAt(active_pane).getModel().endUpdate();
                        }
                    } else if (action == 2) {//если выбрано "удалить вершину"

                        graph.elementAt(active_pane).getModel().beginUpdate();

                        Object g = graphComponent.getCellAt(e.getX(), e.getY()); //получаем вершину, в которую тыкнулы
                        graph.elementAt(active_pane).removeCells(new Object[]{g}); //удаляем вершину

                        graph.elementAt(active_pane).getModel().endUpdate();
                    }
                }


            });

            String title = "graph" + String.valueOf(graph_count);
            tabPanel.addTab(title, graphComponent);
        }
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


    public static void main(String[] args) {
        Graph frame = new Graph();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

}
