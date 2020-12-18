import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 600;

	private final int WIDTH = 600;
	private final int HEIGHT = 650;
	
	private final int DOT_SIZE = 20;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int SPEED = 100;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	
	private boolean gameOver = false;
	
	private Timer timer;
	private Image body;
	private Image apple;
	private Image head;
	
	private int score = 0;
	
	public Board() 
	{
		
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		loadImages();
		initGame();
	}
	
	private void loadImages()
	{
		ImageIcon iia = new ImageIcon(this.getClass().getResource("/res/red_apple.jpeg"));
		apple = iia.getImage(); // transform it 
		Image newimg = apple.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		iia = new ImageIcon(newimg);  // transform it back
		apple = iia.getImage();
		
		ImageIcon iib = new ImageIcon(this.getClass().getResource("/res/green_dot.jpg"));
		body = iib.getImage();
		newimg = body.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		iib = new ImageIcon(newimg);  // transform it back
		body = iib.getImage();
		
		ImageIcon iih = new ImageIcon(this.getClass().getResource("/res/right_head.jpg"));
		head = iih.getImage();
		newimg = head.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		iih = new ImageIcon(newimg);  // transform it back
		head = iih.getImage();
	}
	
	private void initGame()
	{
		dots = 3;
		
		for (int i = 0; i < dots; i++)
		{
			x[i] = 60 - (i * DOT_SIZE);
			y[i] = 60;
		}
		
		placeApple();
		
		timer = new Timer(SPEED, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		draw(g);
		
		g.setColor(Color.GRAY);
		g.fillRect(0, B_HEIGHT, WIDTH, HEIGHT - B_HEIGHT);
		
		String highscore = "SCORE: " + score;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metrics = getFontMetrics(small);
		
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(highscore, (WIDTH - metrics.stringWidth(highscore)) / 2, 
				HEIGHT - ((HEIGHT - B_HEIGHT) / 2));
	}

	private void draw(Graphics g) 
	{
		if (!gameOver) {
			
			g.drawImage(apple, apple_x, apple_y, this);
			
			for (int i = 0; i < dots; i++)
			{
				if (i == 0) {
					g.drawImage(head, x[i], y[i], this);
				} else {
					g.drawImage(body, x[i], y[i], this);
				}
			}
			
			Toolkit.getDefaultToolkit().sync();
		
		} else {
			
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) 
	{	
		String message = "GAME OVER";
		String highscore = "SCORE: " + score;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metrics = getFontMetrics(small);
		
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(message, (B_WIDTH - metrics.stringWidth(message)) / 2, 
				B_HEIGHT / 2);
		g.drawString(highscore, (B_WIDTH - metrics.stringWidth(highscore)) / 2, 
				(B_HEIGHT + 30) / 2);
	}
	
	private void checkApple()
	{
		if (x[0] == apple_x && y[0] == apple_y) 
		{
			dots++;
			score++;
			placeApple();
		}
	}
	
	private void move() 
	{
		for (int i = dots; i > 0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if (left)
		{
			x[0] -= DOT_SIZE;
			
			ImageIcon iih = new ImageIcon(this.getClass().getResource("/res/left_head.jpg"));
			head = iih.getImage();
			Image newimg = head.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iih = new ImageIcon(newimg);  // transform it back
			head = iih.getImage();
		
		}
				
		if (right) 
		{
			x[0] += DOT_SIZE;
			
			ImageIcon iih = new ImageIcon(this.getClass().getResource("/res/right_head.jpg"));
			head = iih.getImage();
			Image newimg = head.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iih = new ImageIcon(newimg);  // transform it back
			head = iih.getImage();
		}
		
		if (up) 
		{
			y[0] -= DOT_SIZE;
			
			ImageIcon iih = new ImageIcon(this.getClass().getResource("/res/up_head.jpg"));
			head = iih.getImage();
			Image newimg = head.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iih = new ImageIcon(newimg);  // transform it back
			head = iih.getImage();
		}
		
		if (down) 
		{
			y[0] += DOT_SIZE;
			
			ImageIcon iih = new ImageIcon(this.getClass().getResource("/res/down_head.jpg"));
			head = iih.getImage();
			Image newimg = head.getScaledInstance(DOT_SIZE, DOT_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iih = new ImageIcon(newimg);  // transform it back
			head = iih.getImage();
		}

	}
	
	private void checkCollision()
	{
		for (int i = dots; i > 0; i--)
		{
			if (i > 4 && x[0] == x[i] && y[0] == y[i])
				gameOver = true;
		}
		
		if (y[0] >= B_HEIGHT || y[0] < 0)
			gameOver = true;
		
		if (x[0] >= B_WIDTH || x[0] < 0)
			gameOver = true;
		
		if (gameOver)
			timer.stop();
			
	}
	
	private void placeApple()
	{
		int random = (int) (Math.random() * RAND_POS);
		apple_x = random * DOT_SIZE;
		
		random = (int) (Math.random() * RAND_POS);
		apple_y = random * DOT_SIZE;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (!gameOver)
		{
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_LEFT && !right) 
			{
				left = true;
				up = false;
				down = false;
			}
			
			if (key == KeyEvent.VK_RIGHT && !left) 
			{
				right = true;
				up = false;
				down = false;
			}
			
			if (key == KeyEvent.VK_DOWN && !up) 
			{
				down = true;
				right = false;
				left = false;
			}
			
			if (key == KeyEvent.VK_UP && !down) 
			{
				up = true;
				right = false;
				left = false;
			}
			
		}
		
	}
	
	
}
