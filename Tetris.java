import javax.swing.JFrame;
	import javax.swing.JPanel;

	public class Tetris extends JPanel {

		private static final long serialVersionUID = 1L;

		private final Point[][][] Tetraminos = {
				// I-Piece
				{
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
				},
				
				// J-Piece
				{
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
				},
				
				// L-Piece
				{
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
				},
				
				// O-Piece
				{
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
				},
				
				// S-Piece
				{
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
				},
				
				// T-Piece
				{
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
				},
				
				// Z-Piece
				{
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
				}
		};
		
		private final Color[] tetraminoColors = {
			Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
		};
		
		private Point pieceOrigin;
		private int currentPiece;
		private int rotation;
		private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

		private long score;
		private Color[][] well;
		
		// Creates border around the well
		// Initializes the dropping piece
		private void init() {
			well = new Color[12][24];
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 23; j++) {
					if (i == 0 || i == 11 || j == 22) {
						well[i][j] = Color.GRAY;
					} else {
						well[i][j] = Color.BLACK;
					}
				}
			}
			newPiece();
		}
		
		// New random piece in the dropping location
		public void newPiece() {
			pieceOrigin = new Point(5, 2);
			rotation = 0;
			if (nextPieces.isEmpty()) {
				Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
				Collections.shuffle(nextPieces);
			}
			currentPiece = nextPieces.get(0);
			nextPieces.remove(0);
		}
		
		// Collision probe of current piece
		private boolean collidesAt(int x, int y, int rotation) {
			for (Point p : Tetraminos[currentPiece][rotation]) {
				if (well[p.x + x][p.y + y] != Color.BLACK) {
					return true;
				}
			}
			return false;
		}