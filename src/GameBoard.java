import javax.swing.*;

public class GameBoard extends JFrame
{
	GameBoard()
	{
		this.add(new GamePanel());
		this.setTitle("SNAKE by Charles Maru");
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setResizable (false);;
		this.pack();
		this.setVisible (true);
		this.setLocationRelativeTo (null);
		
		;
	}
}
