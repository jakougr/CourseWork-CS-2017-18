
import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;

public class Grid {

	Node finPos;
	Node currPos;
	int idsMoves;
	HashSet visitedNodes = new HashSet();

	public Grid(int xCordA, int yCordA, int xCordB, int yCordB, int xCordC, int yCordC, int xCordAgent, int yCordAgent,
			int fxCordA, int fyCordA, int fxCordB, int fyCordB, int fxCordC, int fyCordC, int fxCordAgent,
			int fyCordAgent, int gridSize) throws Exception {
		finPos = new Node(
				new Setup(fxCordA, fyCordA, fxCordB, fyCordB, fxCordC, fyCordC, fxCordAgent, fyCordAgent, gridSize));
		currPos = new Node(new Setup(xCordA, yCordA, xCordB, yCordB, xCordC, yCordC, xCordAgent, yCordAgent, gridSize));
	}

	// BFS Method as described codeproject.com, wikipedia.com and lecture slides
	public void breadthFirst(Node start, Node finish) {
		visitedNodes.removeAll(visitedNodes);
		Queue nodes = new LinkedList();
		long startTime = System.currentTimeMillis();

		nodes.add(start);
		visitedNodes.add(start);

		while (!nodes.isEmpty()) {
			Node currNode = (Node) nodes.poll();

			if (currNode.getConf().compareTo(finish.getConf())) {
				double timeFinish = (System.currentTimeMillis() - startTime);
				printRes("BFS", timeFinish, visitedNodes);
				return;
			}

			ArrayList<Node> agentPosMoves = currNode.calcMoves();

			for (Node n : agentPosMoves) {
				if (!visitedNodes.contains(n)) {
					visitedNodes.add(n);
					nodes.add(n);
				}
			}
		}
		return;
	}

	// DFS Method as described codeproject.com, wikipedia.com and lecture slides
	public void depthFirst(Node start, Node end) throws Exception {
		long start_time = System.currentTimeMillis();

		Stack stack = new Stack();
		visitedNodes.removeAll(visitedNodes);

		stack.push(start);
		visitedNodes.add(start);

		while (!stack.isEmpty()) {
			Node currNode = (Node) stack.pop();
			if (currNode.getConf().compareTo(end.getConf())) {
				double time_end = (System.currentTimeMillis() - start_time);
				printRes("DFS", time_end, visitedNodes);
				return;
			}

			ArrayList<Node> possibleMoves = currNode.calcMoves();

			for (Node n : possibleMoves) {
				if (!visitedNodes.contains(n)) {
					visitedNodes.add(n);
					stack.push(n);
				}
			}
		}
		return;
	}

	// IDS Method as described codeproject.com, wikipedia.com, lecture slides
	// and youtube tutorials

	public Node idls(Node start, Node end, int depth, long time) {
		Stack stack = new Stack();
		Map<Node, Integer> nodesD = new HashMap<Node, Integer>();
		stack.push(start);
		nodesD.put(start, start.getDepth());
		while (!stack.isEmpty()) {
			Node node = (Node) stack.pop();
			if (node.getConf().compareTo(end.getConf())) {
				double end_time = (System.currentTimeMillis() - time);
				idsMethod(start, end, node, end_time);
				return node;
			}
			if (depth > node.getDepth()) {
				ArrayList<Node> possibleMoves = node.calcMoves();
				for (Node n : possibleMoves) {
					if (!nodesD.containsKey(n) || nodesD.get(n) >= n.getDepth()) {
						nodesD.put(n, n.getDepth());
						stack.push(n);
					}
				}
			}
		}
		idsMoves += nodesD.size();
		return null;
	}

	public Node idsMethod(Node start, Node finish, Node end, double time) {

		long start_time = System.currentTimeMillis();

		int depth = 1;
		Node n = null;

		if (end == null) {
			while (n == null) {
				n = idls(start, finish, depth, start_time);
				depth++;
			}
		} else {
			printRes("IDS", time, idsMoves);

		}

		return n;
	}

	// A* Method as described codeproject.com, wikipedia.com and lecture slides
	public void astar(Node start, Node end) {
		long start_time = System.currentTimeMillis();

		Queue queue = new PriorityQueue();
		visitedNodes.removeAll(visitedNodes);

		start.manhattanValue(end);
		queue.add(start);

		while (!queue.isEmpty()) {
			Node current = (Node) queue.poll();

			if (current.getConf().compareTo(end.getConf())) {
				double time_end = (System.currentTimeMillis() - start_time);
				printRes("A*", time_end, visitedNodes);
				return;
			}

			visitedNodes.add(current);
			ArrayList<Node> possibleMoves = current.calcMoves();

			for (Node n : possibleMoves) {
				if (!visitedNodes.contains(n) && !queue.contains(n)) {
					n.manhattanValue(end);
					queue.add(n);
				}
			}
		}
		return;
	}

	public void printRes(String searchName, double time, HashSet nodes) {
		System.out.println(searchName + " SEARCH ENDED:");
		System.out.println("Search completed in " + nodes.size() + " moves, time taken " + time + " ms");
	}
	
	public void printRes(String searchName, double time, int nodes) {
		System.out.println(searchName + " SEARCH ENDED:");
		System.out.println("Search completed in " + nodes + " moves, time taken " + time + " ms");
	}

}
