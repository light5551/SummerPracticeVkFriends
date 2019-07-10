package Visualizator;

import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import java.util.ArrayList;
import Main.Main;
import VkApi.VkAPI;
import VkApi.VKUser;

public class Visualizator extends JFrame {

    final int xRoot = 600, yRoot = 20, xBorder = 20;

    public Visualizator() {

        super("Visualizator"); //call jframe constructor

        mxGraph friendsGraph = new mxGraph();
            Object parent = friendsGraph.getDefaultParent();
            friendsGraph.getModel().beginUpdate();
            try {
                    ArrayList<VKUser> friendList = Main.getList();
                    VkAPI currentApi = new VkAPI();
                    VKUser currentUser = currentApi.getUser(VkAPI.getCurrentUser().userId, null);
                    String in =  currentUser.firstName + " " + currentUser.lastName;
                    ArrayList vertexes = new ArrayList();
                    vertexes.add(friendsGraph.insertVertex(parent, null, in, xRoot, yRoot, 80, 30));
                    int y = -1, x = -1;
                    for (int i = 0; i<friendList.size(); ){
                        mxCell c = (mxCell)vertexes.get(i);
                        in = friendList.get(i).firstName + " " + friendList.get(i).lastName;
                        if(c.getValue().toString().charAt(0) == in.charAt(0)) {
                            if(i==0) x++;
                            vertexes.add(friendsGraph.insertVertex(parent, null, in, xBorder + x * 130,  yRoot + 80 + (y + 1)* 50, 80, 30));
                            i++;
                            y++;
                            friendsGraph.insertEdge(parent, null, "", vertexes.get(i - 1), vertexes.get(i));
                        }
                        else{
                            y = 0;
                            x++;
                            vertexes.add(friendsGraph.insertVertex(parent, null, in,  xBorder + x * 130, yRoot + 80 + y * 50, 80, 30));
                            i++;
                            friendsGraph.insertEdge(parent, null, "", vertexes.get(0), vertexes.get(i));
                        }
                    }
            } finally {
                friendsGraph.getModel().endUpdate();
            }
            mxGraphComponent graphComponent = new mxGraphComponent(friendsGraph);
            getContentPane().add(graphComponent);
        }

    public static void initGUI(){
        Visualizator frame = new Visualizator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,600));
        frame.setLocation(200, 100);
        frame.pack();
        frame.setVisible(true);
    }
}