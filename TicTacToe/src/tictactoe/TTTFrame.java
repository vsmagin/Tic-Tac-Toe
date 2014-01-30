/**
 * TicTacToe Game
 * 
 */

package tictactoe;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TTTFrame extends JFrame{
	private boolean gameOver = false;
	private final int N = 3;
	private tttButton[][] button = new tttButton[N][N];
	
	public TTTFrame(){
		this.setLayout(new GridLayout(3, 3));	
		for (int i = 0; i < N; i++)		//create 9 buttons
			for (int j = 0; j < N; j++){
				button[i][j] = new tttButton();
				this.add(button[i][j]);
				button[i][j].addActionListener(new buttonListener());
			}
	}
	
	//check if game is over - you won, lost or it's a draw
	public void isGameOver(){	
		for (int i = 0; i < N; i++){
			if (button[i][0].getState() == button[i][1].getState() &&
					button[i][0].getState() == button[i][2].getState() &&
					button[i][0].getStatusUsed())
				this.gameOver = true;
			if (button[0][i].getState() == button[1][i].getState() &&
					button[0][i].getState() == button[2][i].getState() &&
					button[0][i].getStatusUsed())
				this.gameOver = true;
		}
		if (button[0][0].getState() == button[1][1].getState() &&
				button[0][0].getState() == button[2][2].getState() &&
				button[1][1].getStatusUsed())
			this.gameOver = true;
		if (button[0][2].getState() == button[1][1].getState() &&
				button[0][2].getState() == button[2][0].getState() &&
				button[1][1].getStatusUsed())
			this.gameOver = true;
	}
	
	public boolean isTie(){
		int sumUsed = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				if (button[i][j].getStatusUsed()) sumUsed++;
			}
		if (sumUsed == N*N && !gameOver) return true;
		return false;
	}
	
	class buttonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (!gameOver) {
				boolean toSetO = false;
				for (int i = 0; i < N; i++)
					for (int j = 0; j < N; j++) {
						if (e.getSource() == button[i][j] && !button[i][j].getStatusUsed()) {
							button[i][j].setState(tttButton.X);
							button[i][j].setStatusUsed();
							isGameOver();
							if (gameOver) System.out.println("U win!");
							if (isTie()){
								gameOver = true;
								System.out.println("It's a DRAW!");
							}
							toSetO = true;
						}
					}
				while (toSetO && !gameOver) {
					Random rand = new Random();
					int i = rand.nextInt(N);
					int j = rand.nextInt(N);
					if (!button[i][j].getStatusUsed()) {
						button[i][j].setState(tttButton.O);
						button[i][j].setStatusUsed();
						toSetO = false;
						isGameOver();
						if (gameOver) System.out.println("U lose!");
					}
				}
			}
		}
	}
	
	//TicTacToe Button class
	class tttButton extends JButton {
		public static final char X = 'X';
		public static final char O = 'O';
		
		private boolean wasUsed = false;
		private char state;

		public tttButton(){
			this.setPreferredSize(new Dimension(100,100));
		}
		
		public void setStatusUsed(){
			this.wasUsed = true;
		}
		
		public boolean getStatusUsed(){
			return this.wasUsed;
		}
		
		public void setState(char state){
			this.state = state;
			repaint();
		}
		
		public char getState(){
			return this.state;
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			int width = getWidth();
			int height = getHeight();
			
			if (state == X){	//draw X
				g.setColor(Color.RED);
				for (int i=-1; i<=1; i++){	
					g.drawLine(10, 10+i, width-10, height-10+i);
					g.drawLine(width-10, 10+i, 10, height-10+i);
				}
			}
			else if (state == O){		//draw O
				g.setColor(Color.BLUE);
				for (int i=-1; i<=1; i++)
					g.drawOval(10+i, 10+i, width-(20+2*i), height-(20+2*i));
			}
		}
	}

	public static void main(String[] args) {
		TTTFrame frame = new TTTFrame();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
