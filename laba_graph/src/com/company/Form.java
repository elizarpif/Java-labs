package com.company;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

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
    private JTabbedPane tabPanel;
    private JButton undo_btn;
    private JButton redo_btn;

    private Vector<Graphp> graph;
    int x, y, n;

    public Form() {
        setContentPane(panel1);
        setMenu();
        graph = new Vector<>();
        setListeners();

    }

    public void setListeners() {
        addGraph_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create new graph
                System.out.println("новый граф...");
                Graphp tmp = new Graphp();
                graph.add(tmp);
                //создать счетчик вкладок
                int ind = tabPanel.getSelectedIndex();
                ind++;
                tabPanel.addTab("graph " + String.valueOf(ind), tmp.getComp());
                tabPanel.setSelectedIndex(ind);
                setPopupMenu();

            }
        });
        undo_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = tabPanel.getSelectedIndex();
                graph.get(ind).undo();
            }
        });
        redo_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = tabPanel.getSelectedIndex();
                graph.get(ind).redo();
            }
        });
    }

    public void setMenu() {

        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setFont(font);

        JMenuItem aboutProgramMenu = new JMenuItem("About program");
        aboutProgramMenu.setFont(font);
        aboutMenu.add(aboutProgramMenu);

        // Todo: make one function for about-listeners
        aboutProgramMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutProgram dialog = new AboutProgram();
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        JMenuItem aboutAuthorsMenu = new JMenuItem("About authors");
        aboutAuthorsMenu.setFont(font);
        aboutMenu.add(aboutAuthorsMenu);

        // Todo: make one function for about-listeners
        aboutAuthorsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutAuthors dialog = new AboutAuthors();
                dialog.pack();
                dialog.setVisible(true);
            }
        });

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
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

    }

    public void setStyleListener(JMenuItem StyleC, int choiceStyle) {
        StyleC.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int index = tabPanel.getSelectedIndex();
                graph.elementAt(index).SetStyleForVertex(x, y, choiceStyle);
            }
        });
    }

    public void setPopupMenu() {

        System.out.println("popup");
        JPopupMenu popupV = new JPopupMenu();
        JPopupMenu popupE = new JPopupMenu();
        JPopupMenu popupAddV = new JPopupMenu();

        JMenuItem ColorEItem = new JMenuItem("Установить цвет ребра");
        // TODO : use one MouseListener function
        ColorEItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int index = tabPanel.getSelectedIndex();
                Color color = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
                graph.elementAt(index).SetColorEdge(x, y, hex);
            }
        });

        JMenuItem ColorVItem = new JMenuItem("Установить цвет вершины");
        // TODO : use one MouseListener function
        ColorVItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);

                int index = tabPanel.getSelectedIndex();

                Color color = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
                graph.elementAt(index).SetColorVertex(x, y, hex);

            }
        });
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

        setStyleListener(StyleR, 0);
        setStyleListener(StyleE, 1);
        setStyleListener(StyleC, 2);
        setStyleListener(StyleT, 3);
        setStyleListener(StyleH, 4);
        setStyleListener(StyleRh, 5);
        setStyleListener(StyleDE, 6);
        setStyleListener(StyleK, 7);
        setStyleListener(StyleA, 8);
        setStyleListener(StyleS, 9);
        setStyleListener(StyleDR, 10);

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
                graph.elementAt(ind).RemoveEdge(x, y);
            }
        });

        JMenuItem addLoop = new JMenuItem("Добавить петлю");
        addLoop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                graph.elementAt(ind).AddLoop(x, y);
            }
        });

        JMenuItem deleteEItem = new JMenuItem("Удалить ребро");

        deleteEItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                graph.elementAt(ind).RemoveVertex(x, y);
            }
        });


        JMenuItem addVItem = new JMenuItem("Добавить вершину");

        addVItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                //System.out.println("selected pane" + ind);
                graph.elementAt(ind).AddVertex(x, y);
            }
        });

        JMenuItem directItemOff = new JMenuItem("Сделать ребро ненаправленным");
        directItemOff.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                graph.elementAt(ind).ChangeDirectionOff(x, y);

            }
        });

        JMenuItem directItemOn = new JMenuItem("Сделать ребро направленным");
        directItemOn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int ind = tabPanel.getSelectedIndex();
                graph.elementAt(ind).ChangeDirectionOn(x, y);

            }
        });

        popupAddV.add(addVItem);
        popupE.add(deleteEItem);
        popupE.add(ColorEItem);
        popupE.add(directItemOff);
        popupE.add(directItemOn);
        popupV.add(deleteVItem);
        popupV.add(StyleVMenu);
        popupV.add(ColorVItem);
        popupV.add(addLoop);

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
        tabPanel = new JTabbedPane();
        panel1.add(tabPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        undo_btn = new JButton();
        undo_btn.setText("Undo");
        panel1.add(undo_btn, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        redo_btn = new JButton();
        redo_btn.setText(" Redo");
        panel1.add(redo_btn, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}