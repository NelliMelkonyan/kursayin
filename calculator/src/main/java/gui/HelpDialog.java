package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class HelpDialog
extends JDialog
implements TreeSelectionListener {
    private JEditorPane txtInfo;
    private JTree treeHelp;

    public HelpDialog(MainGUI mainGUI) {
        super(mainGUI, "Logic Calculator help", false);
        DefaultMutableTreeNode nodeRoot = new DefaultMutableTreeNode("Help");
        this.createNodes(nodeRoot);
        this.treeHelp = new JTree(nodeRoot);
        this.treeHelp.getSelectionModel().setSelectionMode(1);
        this.treeHelp.setShowsRootHandles(true);
        this.treeHelp.addTreeSelectionListener(this);
        JScrollPane scrollHelp = new JScrollPane(this.treeHelp);
        scrollHelp.getViewport().setBackground(null);
        scrollHelp.getViewport().setOpaque(false);
        this.txtInfo = new JEditorPane();
        this.txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(this.txtInfo);
        JSplitPane splitPane = new JSplitPane(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setLeftComponent(scrollHelp);
        splitPane.setRightComponent(scrollInfo);
        splitPane.setDividerLocation(180);
        splitPane.setPreferredSize(new Dimension(500, 300));
        this.getContentPane().add((Component)splitPane, "Center");
        this.setDefaultCloseOperation(2);
        this.pack();
        this.setBounds(300, 200, 650, 425);
        this.setDefaultCloseOperation(2);
        this.assignFile(this.txtInfo, "help/main.html");
    }

    private void createNodes(DefaultMutableTreeNode nodeRoot) {
        DefaultMutableTreeNode topic = new DefaultMutableTreeNode(new Topic("Main window", "help/main.html"), false);
        nodeRoot.add(topic);
        topic = new DefaultMutableTreeNode(new Topic("Logic mode", "help/logic.html"), false);
        nodeRoot.add(topic);
        topic = new DefaultMutableTreeNode(new Topic("Deduction mode", "help/deduction.html"), false);
        nodeRoot.add(topic);
        topic = new DefaultMutableTreeNode(new Topic("Normal form converter", "help/conversion.html"), false);
        nodeRoot.add(topic);
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeHelp.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        Object objNodoInfo = node.getUserObject();
        if (node.isLeaf()) {
            Topic topic = (Topic)objNodoInfo;
            this.assignFile(this.txtInfo, topic.fileName);
        }
    }

    private void assignFile(JEditorPane txt, String file) {
        URL url = null;
        try {
            url = this.getClass().getResource(file);
            if (url == null) {
                url = new URL("file:///" + System.getProperty("user.dir") + "/" + file);
            }
            txt.setPage(url);
        }
        catch (MalformedURLException e) {
            System.err.println("Error al crear URL para : " + file + "\n" + e);
        }
        catch (IOException e) {
            System.err.println("Error al cargar URL : " + url + "\n" + e);
        }
    }

    private class Topic {
        private String title;
        private String fileName;

        Topic(String title, String fileName) {
            this.title = title;
            this.fileName = fileName;
        }

        public String toString() {
            return this.title;
        }
    }

}

