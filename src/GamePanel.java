import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
	static final int SCREEN_WIDTH = 300;
	static final int SCREEN_HEIGHT = 300;
	static final int UNIT_SIZE = 10;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT ) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS]; 	// holds all units of the snake/head on the x axis
	final int y[] = new int[GAME_UNITS]; 	// holds all units of the snake/head on the y axis
	int startSnakeLength = 3;						// starting length of snake is 3 units
	int applesEaten;										// monitor how many apples eaten
	int appleX;													// position of apple on x axis
	int applyY;													// position of apply on y axis
	char direction = 'D';									// initial direction of snake when game starts
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel()
	{
		var random = new Random ();
		this.setPreferredSize (new Dimension (SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground (Color.BLACK);
		this.setFocusable (true);
		this.addKeyListener (new MyKeyAdapter ());
		startGame ();
	}
	
	public void startGame()
	{
		newApple ();
		running	 = true;
		timer = new Timer(DELAY, this);
		timer.start ();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent (g);
	}
	
	public void draw(Graphics g)
	{
	
	}
	
	public void newApple()
	{
	
	}
	
	public void move()
	{
	
	}
	
	public void checkCollisions()
	{
	
	}
	
	public void gameOver(Graphics g)
	{
	
	}
	
	
	@Override
	public void actionPerformed (ActionEvent e)
	{
	
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
		
		}
	}
}
