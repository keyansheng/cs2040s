import java.util.ArrayDeque;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean[][][] visited;
	private int[][][] steps;
	private int[][][][] parents;
	private int[] reachable;

	private int endRow, endCol, endSuperpowers;
	private boolean solved;
	private Queue<Integer> queue;

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		int rows = maze.getRows();
		int cols = maze.getColumns();

		this.maze = maze;
		this.visited = new boolean[rows][cols][];
		this.steps = new int[rows][cols][];
		this.parents = new int[rows][cols][][];
		this.reachable = new int[rows * cols];

		this.queue = null; // indicate that pathSearch has not been run since initialization
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		return this.pathSearch(startRow, startCol, endRow, endCol, 0);
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
			this.solve((rooms = this.queue.remove()),
					this.queue.remove(), this.queue.remove(), this.queue.remove(),
					this.queue.remove(), this.queue.remove(), this.queue.remove());
		}

		return this.reachable[k];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
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
				this.visited[i][j] = new boolean[superpowers + 2];
				this.steps[i][j] = new int[superpowers + 1];
				this.parents[i][j] = new int[superpowers + 1][3];
				this.reachable[i * cols + j] = 0;
				this.maze.getRoom(i, j).onPath = false;
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;
		this.endSuperpowers = -1;
		this.solved = false;
		this.queue = new ArrayDeque<>();

		this.queue.add(0);
		this.queue.add(startRow);
		this.queue.add(startCol);
		this.queue.add(superpowers);
		this.queue.add(-1);
		this.queue.add(-1);
		this.queue.add(superpowers);

		while (!this.solved && !this.queue.isEmpty()) {
			this.solve(this.queue.remove(),
					this.queue.remove(), this.queue.remove(), this.queue.remove(),
					this.queue.remove(), this.queue.remove(), this.queue.remove());
		}

		if (this.solved) {
			int row = this.endRow;
			int col = this.endCol;
			superpowers = this.endSuperpowers;

			while (row != startRow || col != startCol) {
				this.maze.getRoom(row, col).onPath = true;
				int[] parent = this.parents[row][col][superpowers];
				row = parent[0];
				col = parent[1];
				superpowers = parent[2];
			}

			this.maze.getRoom(row, col).onPath = true;
			return this.steps[endRow][endCol][this.endSuperpowers];
		}

		return null;
	}

	private void solve(int rooms, int row, int col, int superpowers,
					   int parentRow, int parentCol, int parentSuperpowers) {
		if (this.visited[row][col][superpowers]) {
			return;
		}

		this.visited[row][col][superpowers] = true;
		this.steps[row][col][superpowers] = rooms;
		this.parents[row][col][superpowers][0] = parentRow;
		this.parents[row][col][superpowers][1] = parentCol;
		this.parents[row][col][superpowers][2] = parentSuperpowers;
		if (!this.visited[row][col][this.visited[row][col].length - 1]) {
			this.visited[row][col][this.visited[row][col].length - 1] = true;
			this.reachable[rooms]++;
		}

		if (row == this.endRow && col == this.endCol) {
			// YES! we found it!
			this.endSuperpowers = superpowers;
			this.solved = true;
		}

		// for each of the 4 directions
		for (int direction = 0; direction < 4; ++direction) {
			int childSuperpowers = canGo(row, col, direction, superpowers);
			if (childSuperpowers >= 0) { // can we go in that direction?
				// yes we can :)
				this.queue.add(rooms + 1);
				this.queue.add(row + DELTAS[direction][0]);
				this.queue.add(col + DELTAS[direction][1]);
				this.queue.add(childSuperpowers);
				this.queue.add(row);
				this.queue.add(col);
				this.queue.add(superpowers);
			}
		}
	}

	private int canGo(int row, int col, int dir, int superpowers) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= this.maze.getRows()) return -1;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= this.maze.getColumns()) return -1;

		switch (dir) {
			case NORTH:
				return this.maze.getRoom(row, col).hasNorthWall() ? superpowers - 1 : superpowers;
			case SOUTH:
				return this.maze.getRoom(row, col).hasSouthWall() ? superpowers - 1 : superpowers;
			case EAST:
				return this.maze.getRoom(row, col).hasEastWall() ? superpowers - 1 : superpowers;
			case WEST:
				return this.maze.getRoom(row, col).hasWestWall() ? superpowers - 1 : superpowers;
		}

		return -1;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 1, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
