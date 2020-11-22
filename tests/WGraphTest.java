package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WGraphTest {
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


    @Test
    void addingNode(){ //checks if addNode function is successfully adding node to the graph
        theGraph.addNode(4);
        theGraph.addNode(5);
        assertEquals(theGraph.getNode(5).getKey(), 5);
        System.out.print("1.checks addNode function supposed to return true, answer is - TRUE");//if its fails - does not reach this line
    }

    @Test
    void hasEdge(){ //checks if connect function is successfully adding edges to the graph
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
        assertTrue(theGraph.hasEdge(4, 2));
        System.out.println("2.checks hasEdge function. passed  - TRUE");
        assertTrue(theGraph.hasEdge(8, 2));
        System.out.println("3.checks hasEdge function. passed  - TRUE");

        assertFalse(theGraph.hasEdge(5, 2));
        System.out.print("4.checks hasEdge function. passed  - TRUE");

    }
    @Test
    void noEdge(){ //checks if removeNode function is successfully adding edges to the graph
        theGraph.addNode(5);
        theGraph.addNode(7);
        theGraph.addNode(8);
        theGraph.addNode(3);
        assertEquals(0, theGraph.edgeSize());
        System.out.println("5.checks the count of the edges in the graph. passed  - TRUE");
        theGraph.connect(5,7,6.7);
        theGraph.connect(5,8,6.1);
        assertEquals(2, theGraph.edgeSize());
        System.out.print("6.checks the count of the edges in the graph. passed  - TRUE");

    }
    @Test
    void edgeSizeBigG(){ //checks the connect func on a BIG graph
        CreatingBigG();
        assertTrue( theGraph.edgeSize() > 999800); //because im using random keys, there is a little collisions
        System.out.print("7.checks the count of the edges in BIG graph. passed  - TRUE");

    }

    @Test
    void nodeSize(){
        theGraph.addNode(4);
        theGraph.addNode(2);
        theGraph.addNode(45);
        theGraph.addNode(7);
        theGraph.addNode(8);
        theGraph.addNode(5);
        theGraph.addNode(3);
        assertEquals(7, theGraph.nodeSize());
        System.out.print("8.checks the count of the nodes in SMALL graph. passed  - TRUE");

    }
    @Test
    void nodeSizeBigG(){
        CreatingBigG();
        assertEquals(100000, theGraph.nodeSize());
        System.out.print("9.checks the count of the nodes in BIG graph. passed  - TRUE");

    }

    @Test
    void getEdge() { //checks the getEdge function in small graph
        theGraph.addNode(5);
        theGraph.addNode(7);
        theGraph.addNode(8);

        theGraph.addNode(3);
        theGraph.connect(5,7,5.4);
        assertEquals(5.4, theGraph.getEdge(5,7));
        System.out.print("10.checks the getEdge function in the graph. passed  - TRUE");


    }

    @Test
    void removeEdge() { //checks the removeEdge function in small graph
        theGraph.addNode(5);
        theGraph.addNode(7);
        theGraph.addNode(8);

        theGraph.addNode(3);
        theGraph.connect(5,7,5.4);
        theGraph.removeEdge(5,7);
        assertEquals(-1, theGraph.getEdge(5,7));
        System.out.print("11.checks the removeEdge function in the graph. passed  - TRUE");

    }

    @Test
    void removeNode() { //another ex1.tests.test to check the removeNode func

        theGraph.addNode(5);
        theGraph.addNode(7);
        theGraph.addNode(8);
        theGraph.addNode(3);
        theGraph.connect(5,7,5.4);
        theGraph.removeNode(8);
        assertNull(theGraph.getNode(8));
        assertFalse(theGraph.getV().contains(8));
        System.out.print("12.checks the removeEdge function in the graph. passed  - TRUE");

    }

    @Test
    void noNodes(){
        assertEquals(0, theGraph.nodeSize());

    }
    @Test
    void edgeSize(){ //checks the addition of an existing edge
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
        assertEquals(3, theGraph.edgeSize());
        theGraph.connect(8,4, 8); // adding an existing edge
        assertEquals(3, theGraph.edgeSize());
        System.out.print("13.checks the addition of an existing edge. passed  - TRUE");
    }

}
