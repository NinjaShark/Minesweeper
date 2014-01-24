import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class runs and displays
 * the program and makes the menu
 * bar options etc.
 * 
 * @author Eileen Balci
 * @version 20.3.2007
 */
public class MineSweeperDriver {

		private static MineSweeperGUI game = new MineSweeperGUI();
		
		/**
		 * Runs the Mine Sweeper Game
		 */
		public static void main(String[] args) {
			
			JFrame window = new JFrame("MINE SWEEPER");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.getContentPane().add(game);
			makeMenuBar(window);//adds the menu bar
			
			//makes it so users can't resize window
			window.setResizable(false);
			
			window.pack();
			window.setVisible(true);
		}
		
		/**
	     * Create the main frame's menu bar.
	     * which will hold menu options such as
	     * Quit which will quit the program
	     * and
	     * New Game which will start a new game!
	     * 
	     * Taken from: 
	     * 	Objects First with Java a Pratical Intro Using BlueJ
	     *  Authors: Michael Kolling and David J Barnes 
	     *  Modified by: Eileen Balci
	     */
	    private static void makeMenuBar(JFrame frame)
	    {
	    	//used to make shortcut keys
	        final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	        JMenuBar menubar = new JMenuBar();
	        frame.setJMenuBar(menubar);
	        
	        JMenu menu;
	        JMenuItem item;
	        
	        // create the Game menu
	        menu = new JMenu("Game");
	        menubar.add(menu);
	        
	        //will make a new game when user selects it
	        //or if user uses the shortcut ctrl+N
	        item = new JMenuItem("New Game");
	        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                        	      game.addBackComponents(); 
	                               }
	                           });
	        menu.add(item);//adds new game to menu
	        
	        menu.addSeparator();//separates the new game from change color
	        
	        //will creates a beginner game
	        item = new JMenuItem("Change Color");
	        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, SHORTCUT_MASK));
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                        	     game.changeColor();
	                               }
	                           });
	        menu.add(item);//adds change color to menu
	        
	        menu.addSeparator(); //separates change color from difficulty settings
	        
	        //will creates a beginner game
	        item = new JMenuItem("Beginner");
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                        	     //haven't gotten this to work
	                               }
	                           });
	        menu.add(item);//adds Beginner to menu
	        
	        //will creates an intermediate game
	        item = new JMenuItem("Intermediate");
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                           		 //haven't gotten this to work
	                               }
	                           });
	        menu.add(item);//adds Intermediate to menu
	        
	        //will create an expert leveled game
	        item = new JMenuItem("Expert");	
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                           		 //haven't gotten this to work
	                               }
	                           });
	        menu.add(item);//adds Expert to menu
	        
	        menu.addSeparator();//separates the standard difficulties from the custom one
	        
	        
	        //will create a custom game board the user wants
	        item = new JMenuItem("Custom...");
	        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, SHORTCUT_MASK));
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                           		 //haven't gotten this to work
	                               }
	                           });
	        menu.add(item);//adds reset to menu
	        
	        menu.addSeparator();//separates the new game and reset options from quit
	        
	        //will allow user to quit but selecting it from the menu
	        //or by useing the shortcut ctr+Q
	        item = new JMenuItem("Quit");
	            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
	            item.addActionListener(new ActionListener() {
	                               public void actionPerformed(ActionEvent e) 
	                               { 
	                            	   quit(); 
	                               }
	                           });
	        menu.add(item);
	         
	        // put a spacer into the menubar, so the next menu appears to the right
	        menubar.add(Box.createHorizontalGlue());

	        // create the Help menu
	        menu = new JMenu("Help");
	        menubar.add(menu);
	        
	        item = new JMenuItem("How To Play...");
	            item.addActionListener(new ActionListener() {
	                               public void actionPerformed(ActionEvent e) 
	                               { 
	                            	   showAbout(); 
	                               }
	                           });
	        menu.add(item);
	    }
	    
	    /**
	     * Quit function: quit the application.
	     */
	    private static void quit()
	    {
	        System.exit(0);
	    }
	    
	    /**
	     * An explaination of the
	     * Connect Four game and how to play.
	     */
	    private static void showAbout()
	    {
	        JOptionPane.showMessageDialog(null, 
	                    "MINE SWEEPER\n" + 
	                    "A one player game that involves puzzle solving skills. " +
	                    "The grid holds a number of mines\n" + 
	                    "if you click on a square and a mine pops up it's game over!" +
	                    " The object of the game is to clear all the squares without\n" +
	                    "clicking on a mine. If you can do this, you win.\n\n" +
	                    "Sometimes when you click on a square a number (from 1-8) will appear." +
	                    " \nThis tells you how many mines are touching the square.\n" +
	                    "The mine can be touching a square" +
	                    " at the corners and sides \n(there are 8 places a mine can touch a\n" +
	                    "single square in)." +
	                    " use the settings to make the game harder" +
	                    " \nif you single Right click you can place a flag at a location you\n" +
	                    "think a mine might be located at, if you want to remove the flag, " +
	                    "double Right click.\n\n\nENJOY THE GAME!");
	        
	        
	    }
	    
		
	

}
