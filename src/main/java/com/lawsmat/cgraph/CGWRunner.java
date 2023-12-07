package com.lawsmat.cgraph;

import java.util.ArrayList;

public class CGWRunner {
    public static void main(String[] args)
    {

        Graph g = new Graph(5);

        g.connect(0,1,5);
        g.connect(0,4,6);
        g.connect(1,2,7);
        g.connect(1,3,4);
        g.connect(1,4,9);
        g.connect(2,3,12);
        g.connect(3,4,3);

        for(int i = 0; i<g.size(); i++){
            Node n = g.getNodes()[i];
            System.out.println(i + " --- ");
            ArrayList<Edge> temp = n.wedges;
            for(Edge t: temp)
                System.out.println(t.to + " w: " + t.value);
        }
    }
}
