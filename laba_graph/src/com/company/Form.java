package com.company;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Form extends JFrame {
    private JTabbedPane tabbedPanel;
    private JPanel panel1;
    private JButton addGraph_btn;
    private JButton addVertex_btn;
    private JButton removeVertex_btn;
    private JTabbedPane tabPanel;


    private Vector<Graph> graph;
    int x, y, n;

    public Form() {
        setContentPane(panel1);
        setMenu();
        graph = new Vector<>();
        setListeners();
        //setPopupMenu();

    }

    public void setListeners() {
        addGraph_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create new graph
                System.out.println("новый граф...");
                Graph tmp = new Graph();
                graph.add(tmp);
                //создать счетчик вкладок
                int ind = tabPanel.getSelectedIndex();
                ind++;
                tabPanel.addTab("graph " + String.valueOf(ind), tmp.getComp());
                tabPanel.setSelectedIndex(ind);
                setPopupMenu();

            }
        });

        addVertex_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setMenu() {
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenuBar menuBar = new JMenuBar();

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

        setJMenuBar(menuBar);
        //graphs = new Vector<mxGraphComponent>();

    }

    public void setPopupMenu() {

        System.out.println("popup");
        JPopupMenu popupV = new JPopupMenu();
        JPopupMenu popupE = new JPopupMenu();
        JPopupMenu popupAddV = new JPopupMenu();


        JMenuItem ColorEItem = new JMenuItem("Установить цвет ребра");
        JMenuItem ColorVItem = new JMenuItem("Установить цвет вершины");
        JMenu StyleVMenu = new JMenu("Установить стиль вершины");
        JMenuItem StyleR = new JMenuItem("Прямоугольник");
        JMenuItem StyleE = new JMenuItem("Кружок");
        JMenuItem StyleC = new JMenuItem("Цилиндр");
        JMenuItem StyleT = new JMenuItem("Треугольник");
        JMenuItem StyleH = new JMenuItem("Шестиугольник");

        JMenuItem StyleRh = new JMenuItem("Ромб");
        JMenuItem StyleDE = new JMenuItem("Кружок с ободком");
        JMenuItem StyleK = new JMenuItem("Облачко)");
        JMenuItem StyleA = new JMenuItem("Кто-то");
        JMenuItem StyleS = new JMenuItem("Частично окрашенный прямоугольник");
        JMenuItem StyleDR = new JMenuItem("Прямоугольник с ободком");

        StyleVMenu.add(StyleR);
        StyleVMenu.add(StyleE);
        StyleVMenu.add(StyleC);
        StyleVMenu.add(StyleT);
        StyleVMenu.add(StyleH);
        StyleVMenu.add(StyleRh);
        StyleVMenu.add(StyleDE);
        StyleVMenu.add(StyleK);
        StyleVMenu.add(StyleA);
        StyleVMenu.add(StyleDR);
        StyleVMenu.add(StyleS);


        JMenuItem deleteVItem = new JMenuItem("Удалить вершину");

        deleteVItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);

                int ind = tabPanel.getSelectedIndex();
                mxGraphComponent gc = graph.elementAt(ind).getComp();
                gc.getGraph().getModel().beginUpdate();

                Object g = gc.getCellAt(x, y);//gcomp.getCellAt(x, y);
                gc.getGraph().removeCells(new Object[]{g});

                gc.getGraph().getModel().endUpdate();

            }
        });


        JMenuItem deleteEItem = new JMenuItem("Удалить ребро");

        deleteEItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                mxGraphComponent gc = graph.elementAt(ind).getComp();
                Object parent = gc.getGraph().getDefaultParent();
                gc.getGraph().getModel().beginUpdate();

                Object g = gc.getCellAt(x, y);//gcomp.getCellAt(x, y);
                gc.getGraph().removeCells(new Object[]{g});

                gc.getGraph().getModel().endUpdate();
            }
        });


        JMenuItem addVItem = new JMenuItem("Добавить вершину");

        addVItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);


                int ind = tabPanel.getSelectedIndex();
                System.out.println("selected pane" + ind);
                mxGraphComponent gc = graph.elementAt(ind).getComp();
                Object parent = gc.getGraph().getDefaultParent();

                gc.getGraph().getModel().beginUpdate();
                try {
                    Object v1 = gc.getGraph().insertVertex(parent, null, "", x, y, 50, 50);

                } finally {
                    gc.getGraph().getModel().endUpdate();
                }

            }
        });

        popupAddV.add(addVItem);
        popupE.add(deleteEItem);
        popupE.add(ColorEItem);
        popupV.add(deleteVItem);
        popupV.add(StyleVMenu);
        popupV.add(ColorVItem);

        mxGraphComponent gc = graph.elementAt(tabPanel.getSelectedIndex()).getComp();

        gc.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);
                Object o = gc.getCellAt(e.getX(), e.getY());
                mxCell obj = (mxCell) o;
                //right click
                if (e.getButton() == MouseEvent.BUTTON3) {
                    x = e.getX();
                    y = e.getY();
                    if (obj == null) {
                        popupAddV.show(gc, e.getX(), e.getY());
                    } else if (obj.isEdge()) {
                        popupE.show(gc, e.getX(), e.getY());
                    } else if (obj.isVertex()) {
                        popupV.show(gc, e.getX(), e.getY());
                    }

                }
            }
        });

//        graph[tabPanel.getSelectedIndex()]..addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                super.mouseReleased(e);
//                Object o = gc.getCellAt(e.getX(), e.getY());
//                mxCell obj = (mxCell) o;
//
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    x = e.getX();
//                    y = e.getY();
//                    if (obj == null) {
//                        popupAddV.show(gc, e.getX(), e.getY());
//                    } else if (obj.isEdge()) {
//                        popupE.show(gc, e.getX(), e.getY());
//                    } else if (obj.isVertex()) {
//                        popupV.show(gc, e.getX(), e.getY());
//                    }
//
//
//                }

        //       }
        //      });

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        addGraph_btn = new JButton();
        addGraph_btn.setText("Add graph");
        panel1.add(addGraph_btn, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addVertex_btn = new JButton();
        addVertex_btn.setText("Add vertex");
        panel1.add(addVertex_btn, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeVertex_btn = new JButton();
        removeVertex_btn.setText("Remove vertex");
        panel1.add(removeVertex_btn, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabPanel = new JTabbedPane();
        panel1.add(tabPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
