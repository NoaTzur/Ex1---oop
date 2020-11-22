package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_Algo_Test {

    weighted_graph theGraph = new WGraph_DS();

    void CreatingBigG(){ //Creates graph with 100,000 nodes and 10 times edges

        long t1= System.currentTimeMillis();

        for(int i=0;i<100000;i++) // insert nodes
            theGraph.addNode(i);

        for (int i=0;i<1000000;i++){ //insert edges
            double weightOfEdge = Math.random()*22;

            int node1 = (int)(Math.random()*100000);
            int node2 = (int)(Math.random()*100000);

            theGraph.connect(node1,node2,weightOfEdge);
        }
        long t2 = System.currentTimeMillis();
        long ans = (long) ((t2-t1)/1000.0);
        assertTrue(ans < 10);
    }


    void createConBigG(){ //creates graph with 100,000 nodes and  2*100,000 edges

        for(int i=0;i<100000;i++) // insert nodes
            theGraph.addNode(i);

        for (int i=0;i<100000-4;i++){ //insert edges
            double weightOfEdge = Math.random()*22;
            theGraph.connect(i,i+1,weightOfEdge);
            theGraph.connect(i,i+4,weightOfEdge);
        }
    }


    @Test
    void copyGraph(){ //checks if deep copy func working well
        theGraph.addNode(4);
        theGraph.addNode(2);
        theGraph.addNode(45);
        theGraph.addNode(7);
        theGraph.addNode(8);
        theGraph.addNode(5);
        theGraph.addNode(3);
        theGraph.connect(4,2, 5.5);
        theGraph.connect(2,8, 6);
        theGraph.connect(8,4, 8);
        weighted_graph_algorithms origin = new WGraph_Algo();
        origin.init(theGraph);

        weighted_graph newG = origin.copy();

        //didnt override equals- should check if the node is pointing to the same memory cell
        assertNotEquals(newG.getNode(3), theGraph.getNode(3));
        System.out.println("Deep copy ex1.tests.test number 1: passed  - TRUE");

        theGraph.removeNode(4);
        assertNotNull(newG.getNode(4));
        System.out.print("Deep copy ex1.tests.test number 2: passed  - TRUE");

    }

    @Test
    void save_load_Graph(){ //checks the save func

        String fileName= "C:\\Users\\Noa\\Desktop\\tests\\mygraph.txt";
        theGraph.addNode(4);
        theGraph.addNode(2);
        theGraph.addNode(45);
        theGraph.addNode(7);
        theGraph.addNode(8);
        theGraph.addNode(5);
        theGraph.addNode(3);
        theGraph.connect(4,2, 5.5);
        theGraph.connect(2,8, 6);
        theGraph.connect(8,4, 8);

        weighted_graph_algorithms a1 = new WGraph_Algo();
        a1.init(theGraph);

        long t1= System.currentTimeMillis();
        assertTrue(a1.save(fileName));
        long t2= System.currentTimeMillis();
        System.out.println("Save graph to file. passed  - TRUE");
        System.out.println("Save graph to file, time it take: "+(t2-t1)/1000.0);

        weighted_graph_algorithms a2 = new WGraph_Algo();

        t1= System.currentTimeMillis();
        a2.load(fileName);
        assertTrue(a2.load(fileName));
        t2= System.currentTimeMillis();

        weighted_graph myG = a2.getGraph();

        assertTrue(myG.hasEdge(4,2));

        assertEquals(5.5, myG.getEdge(4,2));

        System.out.println("Load graph from file. passed  - TRUE");
        System.out.print("Load graph from file, time it take: "+(t2-t1)/1000.0);


    }


    //save and load on big graph
    @Test
    void saveAndLoadBigG(){ //checks the save and loaf function in a row + running time

        String fileName= "C:\\Users\\Noa\\Desktop\\tests\\mygraph.txt";
        CreatingBigG();

        long t1= System.currentTimeMillis();
        weighted_graph_algorithms a1 = new WGraph_Algo();
        a1.init(theGraph);
        a1.save(fileName);
        assertTrue(a1.save(fileName));
        System.out.println("Save graph to file-BIG graph. passed  - TRUE");

        weighted_graph_algorithms a2 = new WGraph_Algo();
        a2.load(fileName);
        assertTrue(a2.load(fileName));
        System.out.println("Load graph from file-BIG graph. passed  - TRUE");


        long t2 = System.currentTimeMillis();
        long ans = (long) ((t2-t1));
        assertTrue(ans/1000.0 < 14);
        System.out.println("Save graph to file and load it from file to new graph, took less then 14 seconds - TRUE");

    }

    @Test
    void creatingBigGraph(){ // checks the running time of creating 100,000 nodes and 10*100,000 edges graph
        long t1= System.currentTimeMillis();
        CreatingBigG();
        long t2= System.currentTimeMillis();
        assertTrue((t2-t1)/1000.0 < 10);
        System.out.println("Creating graph with 100,000 nodes and 1,000,000 edges took less then 10 sec - TRUE");
        System.out.print("time it takes: "+(t2-t1)/1000.0);
    }

    void creatingConnectedG(){ //creates connected graph - (picture is added)
        theGraph.addNode(10);
        theGraph.addNode(2);
        theGraph.addNode(9);
        theGraph.addNode(1);
        theGraph.addNode(0);
        theGraph.addNode(4);
        theGraph.addNode(6);
        theGraph.addNode(21);
        theGraph.connect(10,2,8.2);
        theGraph.connect(10,9,0.8);
        theGraph.connect(9,2,10.01);
        theGraph.connect(9,1,5.6);
        theGraph.connect(9,21,18.3);
        theGraph.connect(1,0,7.2);
        theGraph.connect(0,4,8.4);
        theGraph.connect(0,21,3.2);
        theGraph.connect(0,6,1.0);
    }

    @Test
    void connectivity(){//checks the connectivity of the graph
        creatingConnectedG();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        assertTrue(algo.isConnected());
        System.out.print("Connectivity ex1.tests.test - Small graph - TRUE");

    }
    @Test
    void shortestPathDist(){ //checks the shortestPathDist func
        creatingConnectedG();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(10,21);
        assertEquals(16.8, shortestDist);
        System.out.println("Shortest path destination ex1.tests.test, number 1- passed - TRUE");
    }

    @Test
    void shortestPathDistTime(){ // checks the running time of shortestPathDistTime
        createConBigG();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        long t1= System.currentTimeMillis();
        algo.shortestPathDist(0,99999);
        long t2= System.currentTimeMillis();
        assertTrue((t2-t1)/1000.0 < 10);
        System.out.println("Time it takes for searching the shortest path in a BIG graph: " + (t2-t1)/1000.0 + " TRUE");
        List<node_info> aa = algo.shortestPath(0,10000);
        System.out.println("Number of nodes in path: "+aa.size());
    }

    @Test
    void shortestPathDist2(){ //checks the shortestPathDist func after adding edge with smaller weight, should change the answer
        creatingConnectedG();
        theGraph.connect(6,21,1.2);
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(10,21);
        double EPS = 0.001;
        assertTrue(15.8 - shortestDist < EPS);
        System.out.println("Shortest path destination ex1.tests.test, number 2: passed - TRUE");
    }

    @Test
    void shortestPath(){//checks shortestPath func
        creatingConnectedG();
        weighted_graph_algorithms aa = new WGraph_Algo();
        aa.init(theGraph);
        List<node_info> ans = aa.shortestPath(10, 21);
        assertEquals(10, ans.get(0).getKey());
        System.out.println("First node int path is :"+ans.get(0).getKey());
        assertEquals(21, ans.get(4).getKey());
        System.out.println("Last node in path: "+ans.get(4).getKey());
        assertEquals(5, ans.size());
        System.out.println("Number of nodes in path: "+ ans.size());
    }


}
