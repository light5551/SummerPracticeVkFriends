package Visualizator;

import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import java.util.ArrayList;
import VkApi.VkAPI;
import VkApi.VKUser;
import java.awt.Font;
import java.awt.event.*;

public class Visualizator extends JFrame {

    private static final String[] requestArgs = {"photo_50", "education"};
    private static final String orderFriends = "name";
    static int userID;
    static int friendID;
    //final int userID = 179878269;
    final int xRoot = 600, yRoot = 20, xBorder = 20;
    mxGraphComponent graphComponent;
    JTextField userField;
    JTextField friendField;
    JPanel mainPanel;
    JScrollPane scrollPane = new JScrollPane(graphComponent);
    JPanel checkPanel = new JPanel();
    JPanel exitPanel = new JPanel();
    VkAPI vk = new VkAPI();

    public Visualizator() {

        super("Visualizator"); //call jframe constructor

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new JPanel(new VerticalLayout());
        JPanel interfacePanel = initInterface();
        mainPanel.add(interfacePanel);
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(980,400));
        setLocation(200, 200);
        pack();
        setVisible(true);

    }

    public JPanel initInterface (){

        userField = new JTextField("Введите ID", 15);
        userField.setToolTipText("Введите ID");
        userField.setFont(new Font("Dialog", Font.PLAIN, 14));
        userField.setHorizontalAlignment(JTextField.LEFT);

        friendField = new JTextField("Введите ID друга", 20);
        friendField.setToolTipText("Введите ID друга");
        friendField.setFont(new Font("Dialog", Font.PLAIN, 14));
        friendField.setHorizontalAlignment(JTextField.LEFT);

        JPanel interfacePanel = new JPanel(new FlowLayout());
        interfacePanel.add(userField);

        JButton buildButton = new JButton("Построить граф");
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        userID = Integer.parseInt(userField.getText());
                    } catch (Exception ex) {
                        userID = vk.getIdByUrl(userField.getText());
                    }
                    clearPanel(scrollPane);
                    clearPanel(exitPanel);
                    clearPanel(checkPanel);
                    initGraphPanel();
                    mainPanel.updateUI();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Visualizator.this,"Пожалуйста, введите корректный ID");
                }
            }
        });

        JButton buildCommonButton = new JButton("Построить граф общих друзей");
        buildCommonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        friendID = Integer.parseInt(friendField.getText());
                    } catch (Exception ex) {
                        friendID = vk.getIdByUrl(friendField.getText());
                    }
                    clearPanel(scrollPane);
                    clearPanel(exitPanel);
                    clearPanel(checkPanel);
                    initCommonFriendsGraph();
                    mainPanel.updateUI();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Visualizator.this,"Пожалуйста, введите корректный ID");
                }
            }
        });

        interfacePanel.add(buildButton);
        interfacePanel.add(friendField);
        interfacePanel.add(buildCommonButton);
        return interfacePanel;
    }


    public void initGraphPanel () {

        mxGraph friendsGraph = new mxGraph();
        Object parent = friendsGraph.getDefaultParent();
        friendsGraph.getModel().beginUpdate();
        try {
            VkAPI.updateCurrentUser(userID);
            String id = ""+userID;
            ArrayList<VKUser> friendList = vk.getFriends(VkAPI.getCurrentUser().userId, orderFriends, requestArgs);
            VKUser currentUser = VkAPI.getCurrentUser();
            String in = currentUser.firstName + " " + currentUser.lastName;
            ArrayList vertexes = new ArrayList();
            String ava = "shape=image;image="+currentUser.urlImage_50+";verticalLabelPosition=bottom";
            vertexes.add(friendsGraph.insertVertex(parent, id, in, xRoot, yRoot, 80, 40, ava));
            int y = -1, x = -1;
            for (int i = 0; i < friendList.size(); ) {
                mxCell c = (mxCell) vertexes.get(i);
                id = ""+friendList.get(i).userId;
                in = friendList.get(i).firstName + " " + friendList.get(i).lastName;
                ava = "shape=image;image="+friendList.get(i).urlImage_50+";verticalLabelPosition=bottom";
                if (c.getValue().toString().charAt(0) == in.charAt(0)) {
                    if (i == 0) x++;
                    vertexes.add(friendsGraph.insertVertex(parent, id, in,  xBorder + x * 130, yRoot + 80 + (y + 1) * 70, 80, 40, ava));
                    i++;
                    y++;
                    friendsGraph.insertEdge(parent, null, "", vertexes.get(i - 1), vertexes.get(i));
                } else {
                    y = 0;
                    x++;
                    vertexes.add(friendsGraph.insertVertex(parent, id, in, xBorder + x * 130, yRoot + 80 + y * 70, 80, 40, ava));
                    i++;
                    friendsGraph.insertEdge(parent, null, "", vertexes.get(0), vertexes.get(i));
                }
            }
            /*in = currentUser.firstName + " " + currentUser.lastName;
            ava = "shape=image;image="+currentUser.urlImage_50+";verticalLabelPosition=bottom";
            mxRectangle pSize = friendsGraph.getMinimumGraphSize();
            mxCell c = (mxCell) vertexes.get(0);
            c.removeFromParent();
            c = (mxCell) friendsGraph.insertVertex(parent, null, in, xRoot, yRoot, 80, 40, ava);
            for (int i = 1; i < friendList.size() - 1; i++)
                if (((mxCell)vertexes.get(i)).getValue().toString().charAt(0) == ((mxCell)vertexes.get(i + 1)).getValue().toString().charAt(0))
                    friendsGraph.insertEdge(parent, null, "", c, vertexes.get(i));*/
        } finally {
            friendsGraph.getModel().endUpdate();
        }
        graphComponent = new mxGraphComponent(friendsGraph);
        graphComponent.getViewport().setOpaque(true);
        Color newColor = new Color(192, 192, 192);
        graphComponent.getViewport().setBackground(newColor);
        // Handle only mouse click events
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mxCell cell = (mxCell)graphComponent.getCellAt(e.getX(), e.getY());
                if (cell != null) {
                    friendID = Integer.parseInt(cell.getId());
                    clearPanel(scrollPane);
                    clearPanel(exitPanel);
                    clearPanel(checkPanel);
                    initCommonFriendsGraph();
                    mainPanel.updateUI();
                }
            }
        });
        scrollPane = new JScrollPane(graphComponent);
        scrollPane.setWheelScrollingEnabled(true);
        checkPanel = new JPanel();
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        sSize.width-=80;
        sSize.height-=180;
        scrollPane.setPreferredSize(sSize);
        exitPanel = new JPanel();
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(scrollPane);
                clearPanel(exitPanel);
                clearPanel(checkPanel);
            }
        });
        exitPanel.add(exitButton);
        mainPanel.add(exitPanel);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        final JCheckBox checkBox1 = new JCheckBox("Show vertical scrollbar");
        checkBox1.setSelected(true);
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
            }
        });
        checkPanel.add(checkBox1);

        final JCheckBox checkBox2 = new JCheckBox("Show horizontal scrollbar");
        checkBox2.setSelected(true);
        checkBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox2.isSelected()) {
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                } else {
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
            }
        });
        checkPanel.add(checkBox2);

        mainPanel.add(checkPanel, BorderLayout.SOUTH);
    }

    public void clearPanel(Component comp){
        mainPanel.remove(comp);
        mainPanel.updateUI();
    }

    public void initCommonFriendsGraph(){

        mxGraph CommonfriendsGraph = new mxGraph();
        Object parent = CommonfriendsGraph.getDefaultParent();
        CommonfriendsGraph.getModel().beginUpdate();
        try {
            VKUser currentUser = VkAPI.getCurrentUser();
            vk.updateCurrentUser(friendID);
            VKUser currentFriend = VkAPI.getCurrentUser();
            ArrayList vertexes = new ArrayList();
            String ava = "shape=image;image="+currentFriend.urlImage_50+";verticalLabelPosition=bottom;";
            String in = currentFriend.firstName + " " + currentFriend.lastName;
            vertexes.add(CommonfriendsGraph.insertVertex(parent, null, in, xBorder, yRoot, 80, 40, ava));
            ArrayList<VKUser> friendList = vk.getCommonFriends(userID, friendID);
            in = currentUser.firstName + " " + currentUser.lastName;
            ava = "shape=image;image="+currentUser.urlImage_50+";verticalLabelPosition=bottom";
            vertexes.add(CommonfriendsGraph.insertVertex(parent, null, in, xRoot, yRoot, 80, 40, ava));
            int y = -1, x = -1;
            for (int i = 1; i < friendList.size() + 1; ) {
                mxCell c = (mxCell) vertexes.get(i);
                in = friendList.get(i-1).firstName + " " + friendList.get(i-1).lastName;
                ava = "shape=image;image="+friendList.get(i-1).urlImage_50+";verticalLabelPosition=bottom";
                if (c.getValue().toString().charAt(0) == in.charAt(0)) {
                    if (i == 1) x++;
                    vertexes.add(CommonfriendsGraph.insertVertex(parent, null, in,  xBorder + x * 130, yRoot + 80 + (y + 1) * 70, 80, 40, ava));
                    i++;
                    y++;
                    CommonfriendsGraph.insertEdge(parent, null, "", vertexes.get(i - 1), vertexes.get(i));
                } else {
                    y = 0;
                    x++;
                    vertexes.add(CommonfriendsGraph.insertVertex(parent, null, in, xBorder + x * 130, yRoot + 80 + y * 70, 80, 40, ava));
                    i++;
                    CommonfriendsGraph.insertEdge(parent, null, "", vertexes.get(1), vertexes.get(i));
                }
            }
        } finally {
            CommonfriendsGraph.getModel().endUpdate();
        }
        mxGraphComponent commonGraphComponent = new mxGraphComponent(CommonfriendsGraph);
        commonGraphComponent.getViewport().setOpaque(true);
        Color newColor = new Color(192, 192, 192);
        commonGraphComponent.getViewport().setBackground(newColor);
        final JScrollPane scrollPane = new JScrollPane(commonGraphComponent);
        JPanel checkPanel = new JPanel();
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        sSize.width-=80;
        sSize.height-=180;
        scrollPane.setPreferredSize(sSize);
        JPanel exitPanel = new JPanel();
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(scrollPane);
                clearPanel(exitPanel);
                clearPanel(checkPanel);
            }
        });
        exitPanel.add(exitButton);
        mainPanel.add(exitPanel);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        final JCheckBox checkBox1 = new JCheckBox("Show vertical scrollbar");
        checkBox1.setSelected(true);
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
            }
        });
        checkPanel.add(checkBox1);

        final JCheckBox checkBox2 = new JCheckBox("Show horizontal scrollbar");
        checkBox2.setSelected(true);
        checkBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox2.isSelected()) {
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                } else {
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
            }
        });
        checkPanel.add(checkBox2);

        mainPanel.add(checkPanel, BorderLayout.SOUTH);
    }

    public static void initGUI(){
        Visualizator frame = new Visualizator();
    }
}