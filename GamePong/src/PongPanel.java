import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Color;
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
	boolean gameInitialised = false;
	Ball ball;
	GameState gameState = GameState.Initialising;
	Paddle paddle1, paddle2;
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOR);
		Timer timer = new Timer (TIMER_DELAY, this);
		timer.start();
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void update() {
		switch(gameState) {
		case Initialising: {
			createObjects();
			gameState = GameState.Playing;
			break;
		}
		case Playing: {
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
		
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
		
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
