package tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

import java.util.Random;

public class test {


    public static void main(String[] args) {
        int v=100000, e=100000;
        Random rnd = new Random(1);
        weighted_graph graph= new WGraph_DS();
        for (int i = 0; i < v; i++) {
            graph.addNode(i);
        }
        int n1=0,n2=0;
        for (int i=0;i<e;i++){
            double w = Math.random()*30;
            n1 = rnd.nextInt((v-1));
            n2 = rnd.nextInt((v-1));
            graph.connect(n1,n2, w);
        }
        weighted_graph_algorithms ga1 = new WGraph_Algo();
        ga1.init(graph);
        long BeforeConnected = System.currentTimeMillis();
        ga1.isConnected();
        long endConnected = System.currentTimeMillis();
        System.out.println("time to check if the graph is connected ("+(endConnected-BeforeConnected)/1000.0+" sec)");
        n1 = 1+rnd.nextInt((v-1));
        long beforeSD = System.currentTimeMillis();
        ga1.shortestPathDist(n1,n1-1);
        long endSd = System.currentTimeMillis();
        System.out.println("time to check if the shortestPathDist: ("+(endSd-beforeSD)/1000.0+" sec)");
        long beforeSP = System.currentTimeMillis();
        ga1.shortestPath(n1,n1-1);
        long endsp = System.currentTimeMillis();
        System.out.println("time to check if the shortestPath: ("+(endsp-beforeSP)/1000.0);

    }

}
