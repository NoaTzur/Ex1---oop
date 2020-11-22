package ex1.src;

import java.io.*;
import java.util.*;

/**
 *This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 *  0. clone(); (copy)
 *  1. init(graph);
 *  2. isConnected();
 *  3. double shortestPathDist(int src, int dest);
 *  4. List of nodes shortestPath(int src, int dest);
 *  5. Save(file);
 *  6. Load(file);
 */

public class WGraph_Algo implements weighted_graph_algorithms {

    weighted_graph currGraph = new WGraph_DS();
    Map<Integer, Integer> parents = new HashMap<>(); //<kid,parent>

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g - given graph
     */
    @Override
    public void init(weighted_graph g) {
        currGraph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return - graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.currGraph;
    }

    /**
     * Compute a deep copy of this weighted graph.There is a function in weighted_graph class that performs the copy.
     * @return - new graph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph newGraph = new WGraph_DS(currGraph);
        return newGraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each node.
     * In this function, I used the BFS algorithm. this function take an arbitrary source node, iterates through all the nodes
     * that connected to it. to each node - if this node has not been visited yet - mark it as visited, put it in a queue, and
     * reduce a counter that initialize with the number of nodes in the graph, by one.
     * After the iterating all source "neighbors", this steps occurs again, with the next node from the queue.
     * If the queue becomes empty, and there is nodes that has not been visited (counter !=0), it means the grapgh has more then 1 components (not connected) in the graph.
     * and thats means the graph is not connected.
     * @return - true if the graph is connected, false if not. more then one components.
     */
    @Override
    public boolean isConnected() {
        if (currGraph.nodeSize() < 2) //graph with 0 or 1 node is linked
            return true;

        int counter = currGraph.nodeSize() - 1;
        Map<Integer, Integer> hasVisited = new HashMap<>();//<Integer-key, 0/1> - 0 =not visited, 1=visited
        Collection<node_info> randomNode = currGraph.getV();
        int srcKey = 0;
        for (node_info it : randomNode) {
            hasVisited.put(it.getKey(), 0); // all nodes is not marked yet
            srcKey = it.getKey(); // src is going to be some arbitrary node
        }
        Queue<Integer> allNeigh = new LinkedList<>(); // im going to use a queue in order to traverse all nodes by levels(not really necessary here)
        allNeigh.add(srcKey); // put in the queue the src key

        hasVisited.put(srcKey, 1); // mark the src node as visited

        while (!allNeigh.isEmpty()) {
            node_info tempNode = currGraph.getNode(allNeigh.remove()); //removes the next element in queue
            for (node_info it : currGraph.getV(tempNode.getKey())) { //traverse all his neighbors and marked them as visited if not already
                if (hasVisited.get(it.getKey()) == 0) {
                    counter--; //every node that hasnt been checked - is a new node the we can reach
                    hasVisited.put(it.getKey(), 1); // marked
                    allNeigh.add(it.getKey());  // add him to the queue - for checking his neighbors
                }
            }
            if (counter == 0) //if we checked all n-1 nodes left (without src) then we can reach any node in the graph = connected
                return true;
        }
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest.
     * This function using Dijkstra algorithm.
     * @param src - start node.
     * @param dest - end (target) node.
     * @return length of the shortest path, -1 if there is no path at all.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return Dijkstra(src, dest);
    }

    /**
     * Dijkstra algorithm - at the beginning of the code, with foreach loop, marked all nodes in the graph with Tag = INFINITY.
     * the Tag label will represent the distance from the src node. src node Tag ==0.
     * I used a PriorityQueue as a min-Heap, that will sort the values by the Tag label(compareTo function is added to the node_info class).
     * first, the src node pushed into the queue. while the queue is not empty, iterate through all nodes that connected to src node,
     * and update its Tag to = parent_node_Tag(src in the beginning) + weight of parent--connected_nodes edge.
     * After we going through all the nodes "neighbors" - mark it as visited so it wont checks it again.
     * to each node, we updates its parent (in HashMap that designated for it).
     * When arriving to the dest node - return its Tag . its Tag will be the summary of the shortest distance from src to dest
     * thanks to the priority queue that poll the nodes that holds the smallest Tag(distance).
     * @param src - node to begin
     * @param dest - destination node
     * @return - dest.Tag(shortest path), -1 if there is not such path.
     */
    public double Dijkstra(int src, int dest) {
        Map<Integer, Integer> hasVisited = new HashMap<>();

        for (node_info it1 : currGraph.getV()) {
            it1.setTag(Double.POSITIVE_INFINITY);
        }
        currGraph.getNode(src).setTag(0);

        PriorityQueue<node_info> allNodes = new PriorityQueue<node_info>();
        allNodes.offer(currGraph.getNode(src));

        while (!allNodes.isEmpty()) {
            node_info tempNode = allNodes.poll();
            if (hasVisited.get(tempNode.getKey()) == null) { //not visited yet
                for (node_info it : currGraph.getV(tempNode.getKey())) {
                    double dist = tempNode.getTag() + currGraph.getEdge(tempNode.getKey(), it.getKey()); // parent.dist+weight(parent,neighbor)
                    if (dist < it.getTag() && hasVisited.get(it.getKey()) == null) { // -1 = infinity, it means we never didnt change his tag
                        it.setTag(dist); //update the tag
                        allNodes.offer(it); // puts it in the queue
                        parents.put(it.getKey(), tempNode.getKey()); //update his parent
                    }
                }
                if (tempNode.getKey() == dest) //arrived to the dest node = return its tag = the cumulative amount of weight
                    return tempNode.getTag();
                hasVisited.put(tempNode.getKey(), 1);
            }
        }
        return -1; //there is no such path
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes: src-- n1--n2--...dest.
     * This function uses the parent HashMap that the Dijkstra() function fills.
     * Beginning with the destination node, we going "up" in the HashMap and by its key, pull out its parent.
     * this parent, thank to Dijkstra() has the smallest Tag(smallest distance) from all potential other parents of the dest node.
     * In this order, it pulls the parent  till we arrive to the "final" parent - the src node.
     * now its looks like: dest--dest.parent--dest.parent.parent-------src.
     * with the help of reverse() function in the Collection class, the list can be reversed and this is the path.
     * @param src - start node
     * @param dest - end (target) node
     * @return - list of nodes that represent the shortest path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> shortestPath = new LinkedList<>();

        if (shortestPathDist(src, dest) == -1)
            return null;
        if (src == dest) {
            shortestPath.add(currGraph.getNode(src));
            return shortestPath;
        }

        shortestPath.add(currGraph.getNode(dest));  //put destination node in the list
        node_info parent = currGraph.getNode(parents.get(dest)); // got to dest parent

        while (parent != null) {
            if (!shortestPath.contains(parent))
                shortestPath.add(parent);  //adds the parents to the list
            if (parents.get(parent.getKey()) != null)
                parent = currGraph.getNode(parents.get(parent.getKey()));//get the next parent
            else
                parent = null;
        }
        Collections.reverse(shortestPath); // list contains the path but in the reverse order
        return shortestPath;
    }

    /**
     *Saves this weighted (undirected) graph to the given file name.
     * In this function i used the StringBuilder class, that allow me to append to a String, without creating new one.
     * I chose a form that will storage all the information in a file, in a way that it will be easy to load this storage
     * and convert it back to a graph.
     * @param file - the file name (may include a relative path).
     * @return - true - iff the file was successfully saved. false if not.
     */
    @Override
    public boolean save(String file) {
        StringBuilder nodeString = new StringBuilder();
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            nodeString.append("Number of edges in this graph: ");nodeString.append(currGraph.edgeSize());
            nodeString.append("\nNumber of MC: ");nodeString.append(currGraph.getMC());
            nodeString.append("\n");

            for (node_info it : currGraph.getV()) {
                int key = it.getKey();
                nodeString.append("Key,");nodeString.append(it.getKey());
                nodeString.append(",Tag,");nodeString.append(it.getTag());
                nodeString.append(",Nodes_Info,");nodeString.append(it.getInfo());
                nodeString.append(",neighbors");
                for (node_info it2 : currGraph.getV(it.getKey())) {
                    nodeString.append(",");nodeString.append(it2.getKey());
                    nodeString.append(",edge_weight,");nodeString.append(currGraph.getEdge(key, it2.getKey()));
                }
                nodeString.append("\n");
                pw.write(nodeString.toString());//write the String into the file
                nodeString.setLength(0); // reset the String for the next node info
            }
            pw.close();
            return true;

        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * In this function i iterate through the file, pull put all the nodes and put it on the graph.
     * then - iterate again, this time pulling out to each node all its connected nodes from file, and implement this connections.
     * To each node, i update its Tag and Info as well.
     * the form is: key,2,Tag,6.5,info,"someInfo",neighbors,5,weight,7,4,weight,8.9........
     * @param file - file name
     * @return - if the graph was successfully loaded, false if not.
     */
    @Override
    public boolean load(String file) {
        String splitComma = ",";

        String line = "";
        String line2 = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); //first line
            br.readLine(); //second line

            //copy the graph
            while ((line = br.readLine()) != null) { //third line until last line - key node and his neighbors
                String[] node_inf = line.split(splitComma);
                currGraph.addNode(Integer.parseInt(node_inf[1]));
                node_info temp = currGraph.getNode(Integer.parseInt(node_inf[1]));
                temp.setTag(Double.parseDouble(node_inf[3]));
                temp.setInfo(node_inf[5]);
            }
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(file));
            br2.readLine();//first line
            br2.readLine();//second line
            //copy neighbors
            while ((line2 = br2.readLine()) != null) { //third line until last line - key node and his neighbors
                String[] node_inf2 = line2.split(splitComma);

                node_info temp2 = currGraph.getNode(Integer.parseInt(node_inf2[1]));
                for(int i=7; i<node_inf2.length-2; i= i+3){
                    currGraph.connect(temp2.getKey(), Integer.parseInt(node_inf2[i]), Double.parseDouble(node_inf2[i+2]));
                }
            }

            br2.close();
            return true;

        } catch (FileNotFoundException e) {
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
