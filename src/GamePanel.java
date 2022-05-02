import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
	static final int SCREEN_WIDTH = 300;
	static final int SCREEN_HEIGHT = 300;
	static final int UNIT_SIZE = 15;			// 10 is a good size per each unit
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT ) / UNIT_SIZE;
	static int delay = 125;
	final int x[] = new int[GAME_UNITS]; 	// holds all units of the snake/head on the x axis
	final int y[] = new int[GAME_UNITS]; 	// holds all units of the snake/head on the y axis
	int startSnakeLength = 3;						// starting length of snake is 3 units
	int applesEaten;										// monitor how many apples eaten
	int appleX;													// position of apple on x axis
	int appleY;													// position of apply on y axis
	char direction = 'D';									// initial direction of snake when game starts
	private Image body;
	private Image apple;
	private Image head;
	boolean running = false;
	Timer timer;
	Random random;
	
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
	
	public void startGame()
	{
		newApple ();
		running	 = true;
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
			for (int i = 0; i < startSnakeLength; i++)
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
	
	private void loadImages() {
		
		ImageIcon iid = new ImageIcon("src/resources/dot.png");
		body = iid.getImage();
		
		ImageIcon iia = new ImageIcon("src/resources/apple2.png");
		apple = iia.getImage();
		
		ImageIcon iih = new ImageIcon("src/resources/head2.png");
		head = iih.getImage();
	}
	
	
	public void newApple()		// generate a new apple on the board randomly
	{
		appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move()
	{
		for (int i = startSnakeLength; i > 0  ; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction)
		{
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
				case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}
	
	public void checkApple()
	{
		if ((x[0] == appleX) && (y[0] == appleY))
		{
			startSnakeLength++;
			applesEaten++;
			newApple ();
		}
	}
	
	public void checkCollisions()
	{
		// checking if head is colliding with body
		for (int i = startSnakeLength; i >0 ; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false;
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
		if (running == false)
		{
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g)
	{
		g.setColor (Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 15));
		FontMetrics scoreMetrics = getFontMetrics (g.getFont ());
		g.drawString ("Number of apples eaten:  " + applesEaten,  (SCREEN_WIDTH - scoreMetrics.stringWidth ("Number of apples eaten: " + applesEaten)) / 2, g.getFont ().getSize ());
		
		// game over
		g.setColor (Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 25));
		FontMetrics gameMetrics = getFontMetrics (g.getFont ());
		g.drawString ("GAME OVER!",  (SCREEN_WIDTH - gameMetrics.stringWidth ("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
	}
	
	
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
