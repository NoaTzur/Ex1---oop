# Ex1-oop - Weighted graphs

## Short description
In this exercise(given by Ariel University - OOP class) i had to build a weighted graph, and performs number of algorithms such as finding the shortest path 
from a source node to a destination node. 

![IMG-0275](https://user-images.githubusercontent.com/72066777/99148849-ca892b80-2692-11eb-8d53-59f4aa8ca73d.jpg)

##### Websites i used: 
- **[Dijkstra's Shortest Path Algorithm | Graph Theory](https://www.youtube.com/watch?v=pSqmAO-m7Lk&t=439s&ab_channel=WilliamFiset)**.
- **[Class HashMap<K,V>-Java Platform](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)**.

#### Classes in this Ex: 

- WGraph_DS class: This class represents an undirectional weighted graph.
  - NodeInfo class : inner class - nodes that represnts vertex of a graph. each node has its own unique ID.
- WGraph_Algo class: This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
  - `clone` (deep copy)
  - `init` - initiate the graph on which this set of algorithms operates on. 
  - `isConnected` - checks if the graph is connected (Linked)
  - `shortestPathDist(int src, int dest)` - return the shortest path(weight based) from source node to destination node.
  - `shortestPath(int src, int dest)` - returns List of the shortest path from source node to dest node; src-->n1-->n2---->dest.
  - `save(file)` - save the current graph to a given file name.
  - `load(String file)` - load graph information from given file name and creates new graph on which this algorithms is performed.
  
**for more** information about each Class,please visit **[My project javaDoc](https://noatzur.github.io/Ex1-OOP/)**.


  
##### The data structure i used to build such graph:
```javascript
>HashMap<Integer, node_info> myWGraph = new HashMap<>(); //all nodes in the graph <node_if, the node>
>HashMap<Integer, HashMap<node_info, Double>> edgesWeight = new HashMap<>(); // edges <node1, <node2, weight of edge>>

```

### Tests Classes

In order to check whether this project represent a valid undirectional weighted graph, and to verify that the Algorithms written well (and works on a big graphs), i wrote this 2 classes:

- WGraphTest
- WGraph_Algo_Test

Example for a small test that examine the `shortestPathDist(int src, int dest)` function:

```javascript
 @Test
    void shortestPathDist(){ //checks the shortestPathDist func
        creatingConnectedG();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(10,21);
        assertEquals(16.8, shortestDist);
        System.out.println("Shortest path destination test, number 1- passed - TRUE");
    }
```
The `creatingConnectedG();` function, generate this graph :
![IMG-0276](https://user-images.githubusercontent.com/72066777/99148868-f1476200-2692-11eb-9e88-d7a6b9f55eae.jpg)

