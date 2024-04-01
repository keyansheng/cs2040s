import java.util.ArrayDeque;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean[][] visited;
	private int[][] steps;
	private int[][] parents;
	private int[] reachable;

	private int endRow, endCol;
	private boolean solved;
	private Queue<Integer> queue;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		int rows = maze.getRows();
		int cols = maze.getColumns();

		this.maze = maze;
		this.visited = new boolean[rows][cols];
		this.steps = new int[rows][cols];
		this.parents = new int[rows][cols];
		this.reachable = new int[rows * cols];

		this.queue = null; // indicate that pathSearch has not been run since initialization
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		int rows = this.maze.getRows();
		int cols = this.maze.getColumns();

		if (startRow < 0 || startCol < 0 || startRow >= rows || startCol >= cols ||
				endRow < 0 || endCol < 0 || endRow >= rows || endCol >= cols) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				this.visited[i][j] = false;
				this.steps[i][j] = -1;
				this.parents[i][j] = -1;
				this.reachable[i * cols + j] = 0;
				maze.getRoom(i, j).onPath = false;
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;
		this.solved = false;
		this.queue = new ArrayDeque<>();

		this.queue.add(startRow);
		this.queue.add(startCol);
		this.queue.add(0);
		this.queue.add(-1);

		while (!this.solved && !this.queue.isEmpty()) {
			this.solve(this.queue.remove(), this.queue.remove(), this.queue.remove(), this.queue.remove());
		}

		if (this.solved) {
			int row = this.endRow;
			int col = this.endCol;

			while (row != startRow || col != startCol) {
				this.maze.getRoom(row, col).onPath = true;
				int direction = this.parents[row][col];
				row += DELTAS[direction][0];
				col += DELTAS[direction][1];
			}

			this.maze.getRoom(row, col).onPath = true;
			return this.steps[endRow][endCol];
		}

		return null;
	}

	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	private void solve(int row, int col, int rooms, int parent) {
		if (this.visited[row][col]) {
			return;
		}

		this.visited[row][col] = true;
		this.steps[row][col] = rooms;
		this.parents[row][col] = parent;
		this.reachable[rooms]++;

		if (row == endRow && col == endCol) {
			// YES! we found it!
			this.solved = true;
		}

		// for each of the 4 directions
		for (int direction = 0; direction < 4; ++direction) {
			if (canGo(row, col, direction)) { // can we go in that direction?
				// yes we can :)
				this.queue.add(row + DELTAS[direction][0]);
				this.queue.add(col + DELTAS[direction][1]);
				this.queue.add(rooms + 1);
				// reverses the direction
				this.queue.add((5 - direction) % 4);
			}
		}
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (this.queue == null) {
			throw new Exception("Oh no! You cannot call me without running pathSearch!");
		}

		if (k >= this.reachable.length) {
			return 0;
		}

		int rooms = -1;

		while (rooms <= k && !this.queue.isEmpty()) {
			this.solve(this.queue.remove(), this.queue.remove(), (rooms = this.queue.remove()), this.queue.remove());
		}

		return this.reachable[k];
	}

	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
