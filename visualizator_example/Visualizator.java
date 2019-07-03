import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Visualizator extends JFrame {
    public Visualizator() {
        super("Visualizator"); //call jframe constructor

        mxGraph ex_graph = new mxGraph();
            Object parent = ex_graph.getDefaultParent();
            ex_graph.getModel().beginUpdate();
            try {
                try(BufferedReader br = new BufferedReader(new FileReader("./input.txt")))
                {
                    String in;
                    in=br.readLine();
                    ArrayList vertexes = new ArrayList();
                    int x_root = 600, y_root = 20, x_border = 20;
                    vertexes.add(ex_graph.insertVertex(parent, null, in, x_root, y_root, 80, 30));
                    int i = 0, y = 0, x = -1;
                    while((in=br.readLine())!=null){
                        mxCell c = (mxCell)vertexes.get(i);
                        if(c.getValue().toString().charAt(0) == in.charAt(0)) {
                            vertexes.add(ex_graph.insertVertex(parent, null, in, x_border + x * 130,  y_root + 80 + (y + 1)* 50, 80, 30));
                            i++;
                            y++;
                            ex_graph.insertEdge(parent, null, "", vertexes.get(i - 1), vertexes.get(i));
                        }
                        else{
                            x++;

                            vertexes.add(ex_graph.insertVertex(parent, null, in,  x_border + x * 130, y_root + 80 + y * 50, 80, 30));
                            i++;

                            ex_graph.insertEdge(parent, null, "", vertexes.get(0), vertexes.get(i));
                        }
                    }
                    //System.out.println(in);
                }


                 catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            } finally {
                ex_graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(ex_graph);
            getContentPane().add(graphComponent);
        }

    public static void initGUI(){
        Visualizator frame = new Visualizator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(200,200));
        frame.setLocation(550, 200);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        initGUI();
    }
}