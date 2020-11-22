package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class represents an undirectional weighted graph.It support a large number of nodes.
 * It contains an inner class that represent nodes.
 * It is construct with two HashMaps, one to contain the nodes, ans the second to represent the edges of each node.
 * The second HashMap called edgesWeight built in this way: (central node, inner HashMap(the neighbor, weight)).
 *It means that the inner HashMap contains the neighbors of the specific node that its key represented in the Key call of the outer HashMap.
 */

public class WGraph_DS implements weighted_graph {

    Map<Integer, node_info> myWGraph = new HashMap<>(); //all nodes in the graph
    Map<Integer, HashMap<node_info, Double>> edgesWeight = new HashMap<>(); //all node connect to a hashmap that contains<neighbor key, weight>

    int edgeSize;
    int MC;

    private static class NodeInfo implements node_info, Comparable<NodeInfo> {
        private final int key;
        private String nodeInfo;
        private double tag;

        public NodeInfo(int key) {
            this.nodeInfo = " ";
            this.tag = 0;
            this.key = key;
        }

        public NodeInfo(int key, double tag, String info) {
            this.nodeInfo = info;
            this.tag = tag;
            this.key = key;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.nodeInfo;
        }

        @Override
        public void setInfo(String s) {
            this.nodeInfo = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public int compareTo(NodeInfo o) {
            if (this.tag - ((node_info) o).getTag() > 0) return 1;
            else if (this.tag - ((node_info) o).getTag() < 0) return -1;
            return 0;
        }
    }

    /**
     * return the node_data by the node_id
     *
     * @param key - the node_id
     * @return the node_info that holds this key, null if such node does not exist.
     */
    @Override
    public node_info getNode(int key) {
        return myWGraph.get(key);//return null if node(key) does not exist - build in get dunc of hashmap
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     *
     * @param node1 first node (central node)
     * @param node2 second node
     * @return true if edge exist, false if not
     */
    @Override
    //checks id in the hashmap of node1 there is a hashmap with key node2
    //if they are neighbors so edgesWeight will contain node2 key in the second HashMap
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2) return false;
        if (myWGraph.containsKey(node1) && myWGraph.containsKey(node2))
            return edgesWeight.get(node1).containsKey(myWGraph.get(node2)); //edgesWeight.get(node1)= brings the inner hashmap.
        return false;
    }

    /**
     * return the weight if the edge node1--node1 exist.
     *
     * @param node1 - first node
     * @param node2 - second node
     * @return the weight of the edge that connects node1--node2, -1 if there is no edge.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (myWGraph.containsKey(node1) && myWGraph.containsKey(node2) && hasEdge(node1, node2))
            return edgesWeight.get(node1).get(myWGraph.get(node2));//edgesWeight.get(node1) = brings the inner hashmap.
        return -1;
    }

    /**
     * adding a new node with the given key. if there is node with this key - do nothing.
     *
     * @param key - the key of the new node.
     */
    @Override
    public void addNode(int key) {
        if (!myWGraph.containsKey(key)) {
            node_info newNode = new NodeInfo(key);
            myWGraph.put(key, newNode); // put the node in the graph
            edgesWeight.put(key, new HashMap<>());//delete hashmap for its neighbors
            MC++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with weight greater or equals to 0.
     *
     * @param node1 - first node.
     * @param node2 - second node.
     * @param w     - weight of the edge.
     */

    @Override
    public void connect(int node1, int node2, double w) {
        if (!myWGraph.containsKey(node1) || !myWGraph.containsKey(node2)) return;
        node_info node_1 = myWGraph.get(node1);
        node_info node_2 = myWGraph.get(node2);
        if (node1 != node2 && !(edgesWeight.get(node1).containsKey(node_2)) && !(edgesWeight.get(node2).containsKey(node_1)) && w >= 0) {
            edgesWeight.get(node1).put(node_2, w);
            edgesWeight.get(node2).put(node_1, w);
            edgeSize++;
            MC++;
        }
        else if(w>=0 && w != getEdge(node1, node2) && edgesWeight.get(node1).containsKey(node_2) && edgesWeight.get(node2).containsKey(node_1)) { // they neighbors but the weight is different
            edgesWeight.get(node1).put(node_2, w);    //override the existing weight with the new value, but edgeSize does not change
            edgesWeight.get(node2).put(node_1, w);
            MC++;
        }
    }

    /**
     * returns a pointer (shallow copy) for a Collection representing all the nodes in the graph.
     *
     * @return node_info Collection.
     */
    @Override
    public Collection<node_info> getV() {
        Collection<node_info> allNodes = myWGraph.values();
        return allNodes;
    }

    /**
     * returns a Collection containing all the nodes connected to node_id
     *
     * @param node_id node id
     * @return node_info Collection.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
//        Queue<node_info> newQueue = new PriorityQueue<>();
//        Collection<node_info> aa = edgesWeight.get(node_id).keySet();
//        Iterator<node_info> it = aa.iterator();
//        while(it.hasNext()){
//            newQueue.offer(it.next());
//        }
//        return newQueue;

        Collection<node_info> allNeigh = edgesWeight.get(node_id).keySet();
        return allNeigh;
    }

    /**
     * Delete the node (with the given ID) from the graph, and removes all edges which starts or ends at this node.
     *
     * @param key - given key of node to remove.
     * @return the deleted node_info.
     */
    @Override
    public node_info removeNode(int key) {
        if (myWGraph.containsKey(key)) {
            Iterator<node_info> it = edgesWeight.get(key).keySet().iterator();
            while (it.hasNext()) {
                edgesWeight.get(it.next().getKey()).remove(myWGraph.get(key));
                edgeSize--;
                MC++;
            }
            node_info removedNode = myWGraph.remove(key); //remove from graph
            edgesWeight.remove(key); //remove from edges hashmap

            return removedNode;
        }
        return null;
    }

    /**
     * Delete the edge node1--node2 from the graph.
     *
     * @param node1 - first node
     * @param node2 - second node
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (myWGraph.containsKey(node1) && myWGraph.containsKey(node2)) {
            if (hasEdge(node1, node2)) {
                edgesWeight.get(node1).remove(myWGraph.get(node2));
                edgesWeight.get(node2).remove(myWGraph.get(node1));
                edgeSize--;
                MC++;
            }
        }
    }

    /**
     * returns the number of nodes in the graph.
     *
     * @return - int (number of nodes).
     */
    @Override
    public int nodeSize() {
        return myWGraph.size();
    }

    /**
     * returns the number of edges in the graph.
     *
     * @return - int (number of edges).
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     *
     * @return int(mode count).
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * basic Constructor-init the graph with edgeSize = 0, MC = 0
     */
    //basic constructor
    public WGraph_DS() {
        this.edgeSize = 0;
        this.MC = 0;
    }

    /**
     * performs a deep copy from given graph to this graph.
     * doing this by iterate the nodes of the given graph and creates new nodes from its keys, and inserts this new nodes
     * to this graph. Iterate the new and the given graphs again and connect each node in the new graph to its neighbors nodes.
     * with the help of the information we received from the given graph.
     *
     * @param g - given graph to be copied.
     */
    //deep copy constructor
    public WGraph_DS(weighted_graph g) {
        Collection<node_info> gNodes = g.getV();
        Iterator<node_info> it = gNodes.iterator();
        while (it.hasNext()) { //iterates all nodes and creates new nodes, puts the new nodes into myWGraph
            int tempKey = it.next().getKey();
            node_info newNode = new NodeInfo(tempKey);
            myWGraph.put(newNode.getKey(), newNode);
            edgesWeight.put(newNode.getKey(), new HashMap<>());

        }

        Iterator<node_info> outerIT = myWGraph.values().iterator(); // after we creates new graph from g, iterate again to copy the neighbors
        while (outerIT.hasNext()) {
            int outerKey = outerIT.next().getKey();
            Collection<node_info> neigh = g.getV(outerKey); // takes all node neighbors
            Iterator<node_info> innerIT = neigh.iterator();
            while (innerIT.hasNext()) {
                int innerKey = innerIT.next().getKey();
                connect(outerKey, innerKey, g.getEdge(outerKey, innerKey)); //the connection is performed on the new! graph
                //that's mean that outerKey and innerKey is nodes from myWGraph.
            }
        }
    }

    @Override
    public boolean equals(Object g) {
        if(!(g instanceof WGraph_DS)) { return false; }

        weighted_graph newG = (WGraph_DS)g;
        //if number of nodes are not equals Or number of edges = automatically they are not equals.
        if (this.nodeSize() != newG.nodeSize() || this.edgeSize != newG.edgeSize()) { return false; }

        for (node_info it1 : newG.getV()) {
            if (!this.myWGraph.containsKey(it1.getKey())) { return false; } //if therese a node in newG that doesnt exist in this.graph = false;

            for (node_info it2 : newG.getV(it1.getKey())) {
                if (!this.hasEdge(it1.getKey(), it2.getKey())) {
                    return false;
                } //node1 has some neighbor in g, but node1 does not has that neighbor in this.graph
                if (this.getEdge(it1.getKey(), it2.getKey()) != newG.getEdge(it1.getKey(), it2.getKey())) {
                    return false; //there is an edge but the weight isnt equals.
                }
            }
        }
        return true;
    }
}


