/**
 * Driver.java
 * Create a board ranges in [-10, 10] for both X and Y Axis, randomly creates events, and returns a list of nearest top 5 events
 * @author johny
 *
 */
import java.util.*;
import java.io.*;
public class Driver {
	private static Map<Integer, List<Event>> locEventMap; // a hashmap with location index being key and list of events being value.
	private static int[][] board;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		// initialization
		int m = 2 * 10 + 1, n = 2 * 10 + 1;
		board = new int[m][n];
		locEventMap = new HashMap<>();
		
		// generate board and events
		int eventNum = generateRandomEvent(); 
		System.out.println("There are " + eventNum + " events happening now.");
		printBoard();
		
		// get input location
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input your location:");
		String location = scanner.next();
		scanner.close();
		String[] locs = location.split(",");
		if (locs.length != 2) {
			System.err.println("input valid x,y pair, x and y is between [-10, 10]");
			System.exit(0);
		}
		int x = Integer.parseInt(locs[0]);
		int y = Integer.parseInt(locs[1]);
		if (x < -10 || x > 10 || y < -10 || y > 10) {
			System.err.println("input valid x,y pair, x and y is between [-10, 10]");
			System.exit(1);
		}
		
		// find closest events
		System.out.println("Closest Events to" + "(" + x + "," + y + ")" + ":");
		
		// x' = j - (n-1)/2
		// y' = (m-1)/2 - i
		PriorityQueue<Map.Entry<Event, Integer>> result = getClosestEvent(x + 10, 10 - y, 5);
		while (result.size() > 0) {
			Map.Entry<Event, Integer> e = result.poll();
			System.out.println("Event " + String.format("%03d", e.getKey().getId()) + " - $" 
			+ String.format("%.2f", e.getKey().getCheapest()) + ", " + "Distance " + e.getValue());
		}	
	}
	/**
	 * Generate no more than 20 events. Distribute events evenly in the board
	 * @return Number of events
	 */
	private static int generateRandomEvent() {
		Random rand = new Random();
		int eventNum = rand.nextInt(20) + 1; // assume there are no more than 20 events
		int count = eventNum;
		int total = board.length * board[0].length;
		
		// randomly generate events in board
		// P(A ∩ B) = P(A | B) * P(B)
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (rand.nextInt(total) < count) {
					board[i][j] = 1; // There is an event here
					count--;
					if (!locEventMap.containsKey(i * board[0].length + j)) {
						locEventMap.put(i * board[0].length + j, new ArrayList<>()); // key of a 2d location is i * board[0].length + j
					}
					Event event = new Event(eventNum - count, i, j);
					locEventMap.get(i * board[0].length + j).add(event);
				}
				total--;
			}
		}
		return eventNum;
	}
	/**
	 * print board
	 */
	private static void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	/**
	 * Find top k nearest event and store in a min heap, using BFS algorithm
	 * @param x The x coordinate of input point
	 * @param y The y coordinate of input point
	 * @param count The top k nearest events to find. Default as 5
	 * @return A priorityqueue contains top 5 Map.Entry with key being event object and value being distance
	 */
	private static PriorityQueue<Map.Entry<Event, Integer>> getClosestEvent(int x, int y, int count){
		Map<Event, Integer> result = new HashMap<>();
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] {x, y});
		PriorityQueue<Map.Entry<Event, Integer>> pq = new PriorityQueue<>((a,b) -> (a.getValue() - b.getValue()));
		int[][] dirs = new int[][] {{0,1}, {0,-1}, {1,0}, {-1,0}};
		boolean[][] visited = new boolean[board.length][board[0].length];
		int dis = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				int[] cur = queue.poll();
				if (visited[cur[0]][cur[1]]) continue;
				if (board[cur[0]][cur[1]] == 1) { // have a event
					for (Event event : locEventMap.get(cur[0] * board[0].length + cur[1])) {
						result.put(event, dis);
						count--;
						if (count == 0) {
							for (Map.Entry<Event, Integer> entry : result.entrySet()) {
								pq.offer(entry);
							}
							return pq;
						}
					}
				}
				visited[cur[0]][cur[1]] = true;
				for (int[] dir : dirs) {
					int row = cur[0] + dir[0];
					int col = cur[1] + dir[1];
					if (row >= 0 && row < board.length && col >= 0 && col < board[0].length && !visited[row][col]) {
						queue.add(new int[] {row, col});
					}
				}
			}
			dis++;
		}
		for (Map.Entry<Event, Integer> entry : result.entrySet()) {
			pq.offer(entry);
		}
		return pq;
	}
}
