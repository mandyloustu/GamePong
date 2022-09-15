import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;


public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final int TIMER_DELAY = 5;
	private static final int BALL_MOVEMENT_SPEED = 2;
	boolean gameInitialised = false;
	private static final int POINTS_TO_WIN = 3;
	int player1Score = 0, player2Score = 0;
	private static final int SCORE_TEXT_X = 100;
	private static final int SCORE_TEXT_Y = 100;
	private static final int SCORE_FONT_SIZE = 50;
	private static final String SCORE_FONT_FAMILY = "Serif";
	private static final int WINNER_TEXT_X = 200;
	private static final int WINNER_TEXT_Y = 200;
	private static final int WINNER_FONT_SIZE = 40;
	private static final String WINNER_FONT_FAMILY = "Serif";
	private static final String WINNER_TEXT = "WIN!";
	Player gameWinner;
	Ball ball;
	GameState gameState = GameState.Initialising;
	Paddle paddle1, paddle2;
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOR);
		Timer timer = new Timer (TIMER_DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	public void moveObject(Sprite obj) {
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(), getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(), getHeight());
	}
	
	public void checkWallBounce() {
		if(ball.getXPosition() <= 0) {
			//The ball hits the left side of the screen
			ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.Two);
			resetBall();
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) {
			//The ball hits the right side of the screen
			ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.One);
			resetBall();
		}
		if(ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) {
			//The ball hits the top or the bottom of the screen
			ball.setYVelocity(-ball.getYVelocity());
		}
	}
	
	private void checkPaddleBounce() {
		if (ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
		}
		if (ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setXVelocity(-BALL_MOVEMENT_SPEED);
		}
	}
	
	private void addScore(Player player) {
		if (player == Player.One) {
			player1Score ++;
		}
		else if (player == Player.Two) {
			player2Score ++;
		}
	}
	
	private void paintScores(Graphics g) {
		Font scoreFont = new Font (SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
		g.drawString(rightScore, getWidth() - SCORE_TEXT_X, SCORE_TEXT_Y);
	}
	
	private void checkWin() {
		if (player1Score >= POINTS_TO_WIN) {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		else if (player2Score >= POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
	
	private void paintWinner(Graphics g) {
		if (gameWinner != null) {
			Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD, WINNER_FONT_SIZE);
			g.setFont(winnerFont);
			int xPosition = getWidth() / 2;
			if (gameWinner == Player.One) {
				xPosition -= WINNER_TEXT_X;
			}
			else if (gameWinner == Player.Two) {
				xPosition += WINNER_TEXT_X;
			}
			g.drawString(WINNER_TEXT, xPosition, WINNER_TEXT_Y);
		}
	}
	
	
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	private void update() {
		switch(gameState) {
		case Initialising: {
			createObjects();
			gameState = GameState.Playing;
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
			ball.setYVelocity(BALL_MOVEMENT_SPEED);
			break;
		}
		case Playing: {
			moveObject(paddle1);
			moveObject(paddle2);
			moveObject(ball); //This method moves the ball
			checkWallBounce(); //The method checks whether or not the ball has hit the wall
			checkPaddleBounce();
			checkWin();
			break;
		}
		case GameOver: {
			break;
		}
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//Paddle 1 moves up and down via W and S
		if (event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setYVelocity(-1);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(1);
		}
		//Paddle 2 moves up and down via arrows
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			paddle2.setYVelocity(-1);
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//Stops the movement of Paddle 1
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(0);
		}
		//Stops the movement of Paddle 2
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(0);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.Initialising) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWinner(g);
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	public void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColor());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}

}
