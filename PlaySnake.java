import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.EventQueue;

public class PlaySnake extends JFrame {
	
	public PlaySnake() 
	{
		add(new Board());
		setResizable(false);
		pack();
		
		setTitle("Berke's Snake!");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main (String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				JFrame jf = new PlaySnake();
				jf.setVisible(true);
			}
		
		});
	}

}
