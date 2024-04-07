import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private int[][] dists;
	private PriorityQueue<Coord> pq;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		dists = new int[maze.getRows()][maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		for (int[] row : dists) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		pq = new PriorityQueue<>();

		Coord start = new Coord(startRow, startCol);
		Coord end = new Coord(endRow, endCol);
		start.setDist(0);
		pq.add(start);

		while (!pq.isEmpty()) {
			Coord curr = pq.remove();
			if (curr.equals(end)) {
				return curr.dist();
			}
			for (int dir = 0; dir < 4; dir++) {
				Coord next = curr.move(dir);
				if (next != null) {
					int weight = curr.weight(dir);
					int newDist;
					try {
						newDist = Math.addExact(curr.dist(), weight);
					} catch (ArithmeticException e) {
						newDist = Integer.MAX_VALUE;
					}
					if (next.dist() > newDist) {
						next.setDist(newDist);
						pq.add(next);
					}
				}
			}
		}
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		return null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 0, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Coord implements Comparable<Coord> {
		private int row;
		private int col;

		private Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}

		private Coord move(int dir) {
			int newRow = row + DELTAS[dir][0];
			int newCol = col + DELTAS[dir][1];
			return 0 <= newRow && newRow < maze.getRows() &&
					0 <= newCol && newCol < maze.getColumns()
					? new Coord(newRow, newCol)
					: null;
		}

		private int dist() {
			return dists[row][col];
		}

		private void setDist(int dist) {
			dists[row][col] = dist;
		}

		private Room room() {
			return maze.getRoom(row, col);
		}

		private int weight(int dir) {
			return Math.max(1, WALL_FUNCTIONS.get(dir).apply(room()));
		}

		@Override
		public int compareTo(Coord c) {
			return Integer.compare(dist(), c.dist());
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Coord) {
				Coord c = (Coord) o;
				return row == c.row && col == c.col;
			}
			return false;
		}
	}
}
