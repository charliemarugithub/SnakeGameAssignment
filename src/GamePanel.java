import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{
	static final int SCREEN_WIDTH = 300;	// set up of screen width
	static final int SCREEN_HEIGHT = 300;	// set up of screen height
	static final int UNIT_SIZE = 15;				// 15 is a good size per each unit
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT ) / UNIT_SIZE;
	static int delay = 125;									// setting the speed of the game
	final int[] x = new int[GAME_UNITS]; 		// holds all units of the snake/head on the x-axis
	final int[] y = new int[GAME_UNITS]; 		// holds all units of the snake/head on the y-axis
	int snakeLength = 3;							// starting length of snake is 3 units
	int applesEaten;											// monitor how many apples eaten
	int appleX;													// position of apple on x-axis
	int appleY;													// position of apple on y-axis
	char direction = 'D';									// initial direction of snake when game starts
	private Image body;									// creating image variable for body
	private Image apple;									// creating image variable for apple
	private Image head;									// creating image variable for head
	boolean running = false;								// creating boolean running variable to check when game is running
	Timer timer;												// creating timer variable
	Random random;											// creating random variable for apple
	
	/*
	GamePanel is used to create a new instance of a Random object
	Setting the screen width and height
	Setting the background color to BLACK
	Adding in a Key listener called MyKeyAdapter to take in user input
	Calling startGame and loadImages
	 */
	GamePanel()
	{
		random = new Random ();
		this.setPreferredSize (new Dimension (SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground (Color.BLACK);
		this.setFocusable (true);
		this.addKeyListener (new MyKeyAdapter ());
		startGame ();
		loadImages ();
	}
	/*
	startGame is used to start the game, create a new apple and place
	randomly with the screen width/height
	Setting running to true
	Creating new timer object to set how fast the game runs
	 */
	
	public void startGame()
	{
		newApple ();
		running = true;
		timer = new Timer(delay, this);
		timer.start ();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent (g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if (running)
		{
			g.drawImage (apple, appleX, appleY, this);
			for (int i = 0; i < snakeLength; i++)
			{
				if (i == 0)
				{
					g.drawImage (head, x[i], y[i], this);
				} else
				{
					g.drawImage (body, x[i], y[i], this);
				}
			}
			g.setColor (Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 20));
			FontMetrics fMetrics = getFontMetrics (g.getFont ());
			g.drawString ("Score: " + applesEaten,  (SCREEN_WIDTH - fMetrics.stringWidth ("Score: " + applesEaten)) / 2, g.getFont ().getSize ());
			
		} else
		{
			gameOver (g);
		}
	}
	
	/*
	Loading images that are used for the snake head, snake body and
	the apple
	 */
	private void loadImages() {
		
		ImageIcon iid = new ImageIcon("src/resources/dot.png");
		body = iid.getImage();
		
		ImageIcon iia = new ImageIcon("src/resources/apple2.png");
		apple = iia.getImage();
		
		ImageIcon iih = new ImageIcon("src/resources/head2.png");
		head = iih.getImage();
	}
	
	/*
	Generate a new apple on the board randomly
	 */
	public void newApple()
	{
		appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
		appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
	}
	
	/*
	Setting up how the snake moves when user input is selected
	 */
	public void move()
	{
		for (int i = snakeLength; i > 0  ; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch (direction)
		{
			case 'U' -> y[0] = y[0] - UNIT_SIZE;
			case 'D' -> y[0] = y[0] + UNIT_SIZE;
			case 'L' -> x[0] = x[0] - UNIT_SIZE;
			case 'R' -> x[0] = x[0] + UNIT_SIZE;
		}
	}
	
	/*
	Check to see when the head is at the same spot as the apple using the
	x and y-axis. If so, increase length of snake by 1, increment score by 1
	and create a newly random apple location on the screen
	 */
	public void checkApple()
	{
		if ((x[0] == appleX) && (y[0] == appleY))
		{
			snakeLength++;
			applesEaten++;
			newApple ();
		}
	}
	
	/*
	Checking if the head comes into contact with the ends of the screen
	width and or height. Also checking if the snake runs into its own body.
	If so, change boolean running to false and timer is stopped
	 */
	public void checkCollisions()
	{
		// checking if head is colliding with body
		for (int i = snakeLength; i >0 ; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false;
				break;
			}
		}
		// check if head is colliding with left border
		if (x[0] < 0)
		{
			running = false;
		}
		// check if head is colliding with right board
		if (x[0] > SCREEN_WIDTH)
		{
			running = false;
		}
		// check if head is colliding with top of board
		if (y[0] < 0)
		{
			running = false;
		}
		// check if head is colliding with bottom of board
		if (y[0]  > SCREEN_HEIGHT)
		{
			running = false;
		}
		if (!running)
		{
			timer.stop();
		}
	}
	
	/*
	The gameOver method is used to display the end credits of the game.
	The top portion is used to display the number of apples eaten
	The bottom portion is used to display Game Over
	 */
	public void gameOver(Graphics g)
	{
		// display the number of apples eaten text
		g.setColor (Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 15));
		FontMetrics scoreMetrics = getFontMetrics (g.getFont ());
		g.drawString ("Number of apples eaten:  " + applesEaten,  (SCREEN_WIDTH - scoreMetrics.stringWidth ("Number of apples eaten: " + applesEaten)) / 2, g.getFont ().getSize ());
		
		// game over text
		g.setColor (Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 25));
		FontMetrics gameMetrics = getFontMetrics (g.getFont ());
		g.drawString ("GAME OVER!",  (SCREEN_WIDTH - gameMetrics.stringWidth ("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
	}
	
	/*
	method used to access other methods in order to run the game
	move(), checkApple() & checkCollisions().
	Then using repaint() method
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (running)
		{
			move();
			checkApple ();
			checkCollisions ();
		}
		repaint ();
	}
	
	/*
	User input is set up here to allow the user to direct the snake in
	order to eat an apple
	 */
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode ())
			{
				case KeyEvent.VK_LEFT :
					if (direction != 'R')
					{
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L')
					{
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'D')
					{
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U')
					{
						direction = 'D';
					}
					break;
			}
		}
	}
}
