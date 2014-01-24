import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.BorderFactory;

/**
 * This class will hold all the Graphical
 * User Interface components and the Logic
 * for the game.
 * 
 * This program is suppose to mimic the Windows version
 * of minesweeper as closely as possible
 * 
 * CITATION:
 * Image Credits:
 * Many images are straight from the Windows version of MineSweeper (but 
 * slightly modified)
 * 
 * smile images came from: 
 * http://www.autotrucktoys.com/chevy_hhr/images/CG5728.jpg
 * 
 * yoshi images came from: 
 * http://www.smbhq.com/users/sprite/yisprite.gif
 * 
 * master chief images came from: 
 * http://nikon.bungie.org/misc/resolution/sprites/HaloMasterChiefSheet.gif
 * 
 * mario images came from: 
 * http://boomansion.bo.funpic.org/mkportal/modules/gallery/album/a_9.png
 * 
 * chomp images came from: 
 * http://www.johnsadowski.com/uploaded_images/yoshisisland-760257.gif
 * 
 * box face images were created by me using times new roman characters.
 * 
 * @author Eileen Balci
 * @version 20.3.2007
 */
public class MineSweeperGUI extends JPanel implements ActionListener {
	//all variables are static because this is the only
	//class that will use them and they are all part of this
	//class!
	
	/*------- GUI Variables: ------*/
	//PANELS:
	  //gameboard
	private static JPanel gameBoard;
	  //scoreboards
	private static JPanel scoreBoard;
	 	
	//LABELS:
	  //displays the number of mines in the grid
	private static JLabel numMinesDisplayer;
	  //displays the time in seconds
	private static JLabel timeDisplayer;
	  //gameboard images 
	private static final ImageIcon INIT = 
		ImagesLocator.getImage("images/GreenINIT.jpg");
	private static final ImageIcon CLICKED_MT = 
		ImagesLocator.getImage("images/GreenCLICKEDmt.jpg");
	private static final ImageIcon FLAG = 
		ImagesLocator.getImage("images/GreenFlag.jpg");
	private static final ImageIcon QMARK = 
		ImagesLocator.getImage("images/GreenQmark.jpg");
	private static final ImageIcon X_MINE = 
		ImagesLocator.getImage("images/GreenXmine.jpg");
	private static final ImageIcon MINE = 
		ImagesLocator.getImage("images/GreenMINEreg.jpg");
	private static final ImageIcon MINE_BOOM = 
		ImagesLocator.getImage("images/mineBOOM.jpg");
	private static ImageIcon MINE_BOOM2 =
		ImagesLocator.getImage("images/mineBOOMalt.jpg");
	private static final ImageIcon _1 = 
		ImagesLocator.getImage("images/Green1.jpg");
	private static final ImageIcon _2 = 
		ImagesLocator.getImage("images/Green2.jpg");
	private static final ImageIcon _3 = 
		ImagesLocator.getImage("images/Green3.jpg");
	private static final ImageIcon _4 = 
		ImagesLocator.getImage("images/Green4.jpg");
	private static final ImageIcon _5 = 
		ImagesLocator.getImage("images/Green5.jpg");
	private static final ImageIcon _6 = 
		ImagesLocator.getImage("images/Green6.jpg");
	private static final ImageIcon _7 = 
		ImagesLocator.getImage("images/Green7.jpg");
	private static final ImageIcon _8 = 
		ImagesLocator.getImage("images/Green8.jpg");
	
	//these images will be assigned a value in the 
	//constructor depending on user input. this is the default
	private static ImageIcon NORM = 
		ImagesLocator.getImage("images/Sminorm.jpg");
	private static ImageIcon LOSE = 
		ImagesLocator.getImage("images/Smilose.jpg");
	private static ImageIcon WIN = 
		ImagesLocator.getImage("images/Smiwin.jpg");
	private static ImageIcon UHOH = 
		ImagesLocator.getImage("images/Smiuhoh.jpg");
	private static ImageIcon CLICK = 
		ImagesLocator.getImage("images/Smiclicked.jpg");
	
	//The label that will hold the face/restart label
	private static JLabel face = new JLabel();
	

	/*-------- Logic Variables: --------*/
	
	//The timer
	private Timer time = new Timer(1000, this);
	
	//Final Variables
	//indicates there is nothing at this location
	private static final int NOTHING = 0;
	//indicates there is a mine at the location
	private static final int MINE_PRESENT = 1;
	//indicates there is a square holding a number of adj. mines
	private static final int ADJ_MINES_PRESENT = -1;
	//indicates the mine the user clicked on
	private static final int BOOM_HERE = 2;
	//beginner settings
	private static final int BEG_MINES = 15;
	private static final int BEG_ROWS = 9;
	private static final int BEG_COLS = 9;
	
	/* 
	//intermediate settings
	private static final int INT_MINES = 40;
	private static final int INT_ROWS = 16;
	private static final int INT_COLS = 16;
	//expert settings
	private static final int EX_MINES = 99;
	private static final int EX_ROWS = 16;
	private static final int EX_COLS = 30;
	*/

	//Game Component Variables
	//(initially they are set to beginner settings)
	//these aren't final variables because they could
	//be changed later.
	//The number of mines in the game (a constant)
	private static int num_mines = BEG_MINES;
	//number of rows (a constant).
	private static int num_rows = BEG_ROWS;
	//number of columns (a constant).
	private static int num_cols = BEG_COLS;
	
	//Counters
	//counts the mines
	private static int mineCounter = num_mines;
	//counts how many flags are actually on mines
	private static int flagCounter = num_mines;
	//keeps track of time
	private static int keepTime = 0;
	//increases depending on how many mines are ajacent to the location
	private static int adjacentMines = 0;
	
	//Strings
	//determines what face the user wants to use
	private static String faceChoice = "DEFAULT";
	//determines what color the user wants to use
	private static String colorChoice = "GRAY";
	//when lose is true the user lost the game
	private static boolean lose = false;

	//ARRAYS
	//creates a game array that holds JLabels which will fill 
	//the gameboard with the INIT ImageIcon at initilization
	private static JLabel[][] gameArray = new JLabel[num_rows][num_cols]; 
	
	//Creates a logic array which will hold the actual logic to figure out
	//if a user clicked on an empty square (0), a square adjecent to 
	//a mine (-1) or a mine (1).
	private static int[][] logicArray = new int[num_rows][num_cols]; 
	
	//This array will be filled with the boolean value false
	//when this value is true that means that the square at that location
	//was uncovered already and doesn't need to be uncovered again (this is
	//used to prevent the recursion from going into an infinit loop or into a
	//stack overflow error, which is the pretty much the same thing)
	private static boolean[][] booleanArray = new boolean [num_rows][num_cols];
	
	//creates a global variable for the mouseListener so that it can later
	//be removed from a lable after the user clicks on a square.
	private GridMouseListener listener = new GridMouseListener();
	
	
	/**
	 * constructs components, 
	 * assigns variables and adds them
	 * to their panels
	 * 
	 * sets up all the inital stuff
	 * 
	 */
	public MineSweeperGUI()
	{
		setLayout(new BorderLayout());
		
		//JPanels
		gameBoard = new JPanel();
		scoreBoard = new JPanel();
		
		
		//set layouts for all panels
		gameBoard.setLayout(new GridLayout(num_rows, num_cols));
		scoreBoard.setLayout(new GridLayout(1, 3));
		
		//set colors for panels 
		scoreBoard.setBackground(Color.GRAY);
		gameBoard.setBackground(Color.gray);

		gameBoard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(2, 
				Color.LIGHT_GRAY, Color.GRAY), 
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5)));
		
		scoreBoard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(2,
				Color.LIGHT_GRAY, Color.GRAY), 
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5)));
		
		timeDisplayer = new JLabel();
		numMinesDisplayer = new JLabel();
		
		numMinesDisplayer.setFont (new Font ("Sydnie", Font.BOLD, 18));
		
		timeDisplayer.setFont (new Font ("Sydnie", Font.BOLD, 18));
		
	    //fill the gameBoard with the initial ImageIcon
		this.fill();
		
		changeFace();//ask user if they'd like a diffrent character
		
		//set the face image
		face.setIcon(NORM);
		face.addMouseListener(new FaceMouseListener());
	
		
		//add components to p1Scores 
		scoreBoard.add(numMinesDisplayer);
		scoreBoard.add(face);
		scoreBoard.add(timeDisplayer);

		//set text to the number of mines in the grid
		numMinesDisplayer.setText("" + mineCounter);
		
		//adds the JPanels to the BorderLayout
		add(scoreBoard, BorderLayout.NORTH);
		add(gameBoard, BorderLayout.CENTER);
		
		changeColor();//ask user if they want to change BG color

	}
	
	/**
	 * Asks the user if they would like to change the icon
	 * on the reset button to one of the following in the 
	 * specified list.
	 */
	public void changeFace(){
		
		faceChoice = JOptionPane.showInputDialog(null, "Select a character!\n" +
				"Enter the name of the character you want to use:\n" +
				"Smiles, Yoshi, Mario, Master Chief, Chomp, Box Face", "Smiles").toUpperCase();
		
		if(faceChoice.equals("SMILES")){
			//use default face (nothing happens)
		}
		else if(faceChoice.equals("YOSHI")){
			LOSE = ImagesLocator.getImage("images/Yoshilose.jpg");
			WIN = ImagesLocator.getImage("images/Yoshiwin.jpg");
			UHOH = ImagesLocator.getImage("images/Yoshiuhoh.jpg");
			NORM = ImagesLocator.getImage("images/Yoshinorm.jpg");
			CLICK = ImagesLocator.getImage("images/YoshiClicked.jpg");
		}
		else if(faceChoice.equals("MASTER CHIEF")){
			LOSE = ImagesLocator.getImage("images/MasterClose.jpg");
			WIN = ImagesLocator.getImage("images/MasterCwin.jpg");
			UHOH = ImagesLocator.getImage("images/MasterCuhO.jpg");
			NORM = ImagesLocator.getImage("images/MasterCnorm.jpg");	
			CLICK = ImagesLocator.getImage("images/MasterCclicked.jpg");
		}
		else if(faceChoice.equals("MARIO")){
			LOSE = ImagesLocator.getImage("images/Mariolose.jpg");
			WIN = ImagesLocator.getImage("images/Mariowin.jpg");
			UHOH = ImagesLocator.getImage("images/Mariouhoh.jpg");
			NORM = ImagesLocator.getImage("images/Marionorm.jpg");	
			CLICK = ImagesLocator.getImage("images/MarioClicked.jpg");
		}
		else if(faceChoice.equals("CHOMP")){
			LOSE = ImagesLocator.getImage("images/Marlose.jpg");
			WIN = ImagesLocator.getImage("images/Marwin.jpg");
			UHOH = ImagesLocator.getImage("images/Maruhoh.jpg");
			NORM = ImagesLocator.getImage("images/Marnorm.jpg");	
			CLICK = ImagesLocator.getImage("images/marClicked.jpg");
		}
		else if(faceChoice.equals("BOX FACE")){
			LOSE = ImagesLocator.getImage("images/boxfacelose.jpg");
			WIN = ImagesLocator.getImage("images/boxfacewin.jpg");
			UHOH = ImagesLocator.getImage("images/boxfaceuhoh.jpg");
			NORM = ImagesLocator.getImage("images/boxfacenorm.jpg");	
			CLICK = ImagesLocator.getImage("images/boxfaceClicked.jpg");
		}
		else{
			JOptionPane.showMessageDialog(null, "\"" + faceChoice + "\"" + 
					" is an invalid input. Your character" +
					" has been set to the Default character Smiles.");
		}
	}
	
	/**
	 * allows user to change the background
	 * color of the scoreboard panel
	 */
	public void changeColor()
	{
		colorChoice = JOptionPane.showInputDialog(null, "Select a Background color!\n" +
				"Enter the name of the color you want to use:\n" +
				"Gray, Orange, Yellow, Pink, Cyan, Red", "Gray").toUpperCase();
		
		if(colorChoice.equals("GRAY")){
			scoreBoard.setBackground(Color.GRAY);
		}
		else if(colorChoice.equals("ORANGE")){
			scoreBoard.setBackground(Color.ORANGE.darker());
		}
		else if(colorChoice.equals("YELLOW")){
			scoreBoard.setBackground(Color.YELLOW.darker());
		}
		else if(colorChoice.equals("PINK")){
			scoreBoard.setBackground(Color.PINK.darker());
		}
		else if(colorChoice.equals("CYAN")){
			scoreBoard.setBackground(Color.CYAN.darker());
		}
		else if(colorChoice.equals("RED")){
			scoreBoard.setBackground(Color.RED.darker());
		}
	}
	
	/**
	 * This method fills the game with the correct
	 * initial images and mines in randomized locations
	 */
	public void fill()
	{
        //fill the board with labels and set the icon
		//to the empty board peice
		for(int rows=0; rows < num_rows; rows++){
			for(int cols=0; cols < num_cols; cols++){
				//fill the board with the initial image
				gameArray[rows][cols] = new JLabel();
				gameArray[rows][cols].setIcon(INIT);
				gameArray[rows][cols].addMouseListener(listener);
				gameBoard.add(gameArray[rows][cols]);
				
				//means that all the squares are initially 
				//covered (not clicked on)
				booleanArray[rows][cols] = false;
			}
		}
		
		//time to add the mines in random locations
		Random randGen = new Random();
		
		//loop through placing mines in random locations
		for(int i = 0; i < num_mines; i++){
			
			//generate two random numbers
			int randomIndex = randGen.nextInt(logicArray.length);
			int randomIndex2 = randGen.nextInt(logicArray.length);
			
			//loops until all mines have been placed in empty locations
			//and not on top of each other.
			while(logicArray[randomIndex][randomIndex2] == MINE_PRESENT){
				//System.out.println("there is a mine here already");
				randomIndex = randGen.nextInt(logicArray.length);
				randomIndex2 = randGen.nextInt(logicArray.length); 
			}
			
			//when the mine finds an empty random location, set that
			//location equal to 1 (MINE_PRESENT) to represent there is a mine.
	    	logicArray[randomIndex][randomIndex2] = MINE_PRESENT;
	    	
	    	//this was used to check to make sure the program was running right by
	    	//initially displaying the mines at their locations
	    	//if you would like to see the mines initially just uncomment the
	    	//line right below this one =)
	    	//gameArray[randomIndex][randomIndex2].setIcon(MINE);
	    	
		}
	}
	
	/**
	 * This method shows where all the mines where located
	 * after a user clicked on a mine (aka lost the game)
	 */
	public void showAllMines()
	{
		if(lose == true){
			for(int row=0; row < num_rows; row++){
				for(int col=0; col < num_rows; col++){
					//if there is a mine at the location and it is NOT the 
					//mine the user clicked on show all other mines.
					if(logicArray[row][col] == MINE_PRESENT && 
					   (logicArray[row][col] != BOOM_HERE || logicArray[row][col] != NOTHING)){
						//if there was a flag on the mine, to show the user was right
						//make the image the X_MINE image
						if(gameArray[row][col].getIcon() == FLAG){
							gameArray[row][col].setIcon(X_MINE);
						}
						//otherwise there wasn't a flag on the mine so show 
						//the regular mine image.
						else{
							gameArray[row][col].setIcon(MINE);
						}
					}
					
					if(logicArray[row][col] < 0 || logicArray[row][col] >= 0){
						//make EVERYTHING in the gameArray unclickable (because the
						//game is over). (only the reset button is clickable)
						gameArray[row][col].removeMouseListener(listener);
					}
				}	
			}
			
			lose = false; // set lose back to false
		}
	}
	
	/**
	 * This method adds back the components for 
	 * the game after the user hits the 
	 * reset or new game button
	 */
	public void addBackComponents()
	{
		gameBoard.removeAll();//removes all gameBoard components
		
		//sets all arrays to zero or inital state
	    for(int row = 0; row < num_rows; row++){
	    	for(int col = 0; col < num_cols; col++){
	    		logicArray[row][col] = NOTHING;
	    		gameArray[row][col].setIcon(INIT);
	    	}
	    }
	    fill();//refills gameBoard back up to its initial state
	    keepTime = 0;//resets the time lable
	    adjacentMines = NOTHING; 
	    mineCounter = num_mines;
	    flagCounter = num_mines;
		numMinesDisplayer.setText("" + mineCounter);
	    time.restart();//resets the time
	}
	
	/**
	 * checks to see what squares around the square that was
	 * clicked on is empty and adjecent to mines and opens up
	 * the region until there is basically a wall of numbers
	 * (squares with numbers of adjcent mines in them)
	 * @param row is the row number of the location
	 * @param col is the column number of the location
	 */
	public void openRegion(int row, int col)
	{
		int rowNum, colNum;	
		
		//automatically try to open all 8 adjecent squares if possible
		//if there is a flag, don't open that square.
		rowNum = row;
		colNum = col;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
				
				if(logicArray[rowNum][colNum] == ADJ_MINES_PRESENT){
					//you landed directly on a square that is adjecent to a mine
					//so there is no need to continue with opening up squares.
					return; //terminates method
				}
			}
		}
		rowNum = row-1;
		colNum = col-1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row-1;
		colNum = col;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row-1;
		colNum = col+1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row;
		colNum = col-1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row;
		colNum = col+1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row+1;
		colNum = col-1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row+1;
		colNum = col;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		rowNum = row+1;
		colNum = col+1;
		//check if row number is in bounds
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT 
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				findAdjecentMines(rowNum, colNum);
				setNumMinesImage(rowNum, colNum);
			}
		}
		
		//now for the recursive part, try to open the next set of 8 squares if 
		//possible and repeate until the base cases are reached
		rowNum = row-1;
		colNum = col;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}

		}
		rowNum = row+1;
		colNum = col;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row;
		colNum = col-1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row;
		colNum = col+1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row-1;
		colNum = col-1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row+1;
		colNum = col-1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row+1;
		colNum = col-1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
		rowNum = row-1;
		colNum = col+1;
		if(rowNum >= 0 && rowNum < num_rows && colNum >= 0 && colNum < num_cols){
			if(logicArray[rowNum][colNum] != MINE_PRESENT && booleanArray[rowNum][colNum] == false
					&& gameArray[rowNum][colNum].getIcon() != FLAG){
				booleanArray[rowNum][colNum] = true;
				openRegion(rowNum, colNum);
			}
		}
	}
	
	/**
	 * Check the adjecent sides of the square the user clicked and
	 * if there is a mine adjacent increment the adjacentMines variable
	 * 
	 * @param rows is the row (x) coordinate in the grid
	 * @param cols is the column (y) corrdinate in the grid
	 */
	public void findAdjecentMines(int rows, int cols)
	{
		if(logicArray[rows][cols] != MINE_PRESENT){
			//if you are at top left Corner of the grid
			//there are only 3 possible places a mine can be
			if(rows == 0 && cols == 0){
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom right
				if(logicArray[rows+1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are at the bottom left corner of the grid
			//there are only 3 possible places a mine can be
			else if(rows == num_rows && cols == 0){
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top right
				if(logicArray[rows-1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are the top right corner of the grid
			//there are only 3 possible places a mine can be
			else if(rows == 0 && cols == num_cols){
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom left
				if(logicArray[rows+1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			
			//if you are the bottom right corner of the grid
			//there are only 3 possible places a mine can be
			else if(rows == num_rows && cols == num_cols){
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top left
				if(logicArray[rows-1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are in the middle area of the grid check all 8 places
			else if(rows > 0 && rows < num_rows-1 && cols > 0 && cols < num_cols-1){
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom right
				if(logicArray[rows+1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top left
				if(logicArray[rows-1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top right
				if(logicArray[rows-1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom left
				if(logicArray[rows+1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are at the left edge of the grid
			//there are only 5 possible places a mine can be
			//(can't go anywhere to the left)
			else if(cols == 0 && rows > 0 && rows < num_rows-1){
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top right
				if(logicArray[rows-1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom right
				if(logicArray[rows+1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are the top edge of the grid
			//there are only 5 possible places a mine can be
			else if(rows == 0 && cols > 0 && cols < num_cols-1){
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom left
				if(logicArray[rows+1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom right
				if(logicArray[rows+1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			
			//if you are the bottom edge of the grid
			//there are only 5 possible places a mine can be
			else if(rows == num_rows-1 && cols > 0 && cols < num_cols-1){
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top right
				if(logicArray[rows-1][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check right
				if(logicArray[rows][cols+1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top left
				if(logicArray[rows-1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
			}
			//if you are the right edge of the grid
			//there are only 5 possible places a mine can be
			else if(cols == num_cols-1 && rows > 0 && rows < num_rows-1){
				//check up
				if(logicArray[rows-1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
				//check top left
				if(logicArray[rows-1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check left
				if(logicArray[rows][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check bottom left
				if(logicArray[rows+1][cols-1] == MINE_PRESENT){
					adjacentMines++;
				}
				//check down
				if(logicArray[rows+1][cols] == MINE_PRESENT){
					adjacentMines++;
				}
			}
		}
	}
	               
	/**
	 * Put the appropriate number image in the
	 * clicked square's place (using the adjacentMines variable). and give 
	 * that location the value of ADJ_MINES_PRESENT (-1) to 
	 * represent a square that has mines adjecent to it
	 * and also make that location unclickable since it has already
	 * been uncovered.
	 * @param rows is the row (x) coordinate in the grid
	 * @param cols is the column (y) corrdinate in the grid
	 */
	public void setNumMinesImage(int rows, int cols)
	{
		//put the right image at the location
		if(adjacentMines == NOTHING){
			//(no adjacentMines located)
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(CLICKED_MT);
		}
		else if(adjacentMines == 1){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_1);
		}
		else if(adjacentMines == 2){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_2);
		}
		else if(adjacentMines == 3){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_3);
		}
		else if(adjacentMines == 4){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_4);
		}
		else if(adjacentMines == 5){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_5);
		}
		else if(adjacentMines == 6){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_6);
		}
		else if(adjacentMines == 7){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_7);
		}
		else if(adjacentMines == 8){
			logicArray[rows][cols] = ADJ_MINES_PRESENT;
			gameArray[rows][cols].removeMouseListener(listener);
			gameArray[rows][cols].setIcon(_8);
		}

		//image has been set so now
		//set adjacentMines back to zero so that
		//the next time the user clicks on a square
		//it doesn't add the old value to the new.
		adjacentMines = 0;	
		
	}
	
	/**
	 * This method is used when a user right clicks on a square.
	 * if the user single right clicks a square a flag image is 
	 * placed at that location and that location will not be left clickable
	 * it can only be right clicked. To remove a flag the user must
	 * double right click. Adding flags will decrement the number of mines
	 * in the numMineDisplayer and removing a flag will increment the number
	 * of mines back to whatever the previous value was. If all the mines are
	 * covered in flags the user wins. The flags MUST be on the mines to win.
	 * @param rows is the row location (x) coordinate in the grid
	 * @param cols is the column location (y) coordinate in the grid
	 * @param clicks will determine if the user single or double clicked
	 */
	public void flagging(int rows, int cols, int clicks){
		
		int numClicks = clicks;//will be equal to event.getClickCount()
		
		//if it was a single click
		if(numClicks == 1){
			//if there isn't a flag in this location already
			if(gameArray[rows][cols].getIcon() != FLAG){
				//add flag
				gameArray[rows][cols].setIcon(FLAG);
				mineCounter--;//decrease number of mines
				
				//display new number
				numMinesDisplayer.setText("" + mineCounter);
				
				//if the flag is actually on a mine increase flag counter
				if(logicArray[rows][cols] == MINE_PRESENT){
					flagCounter--; //decrement
					//System.out.println(flagCounter);
				}
			}
		}
		//other wise if it was a double click
		else if(numClicks == 2){
			//if there is a flag at this location already
			if(gameArray[rows][cols].getIcon() == FLAG){
				//remove flag
				gameArray[rows][cols].setIcon(INIT);
				mineCounter++;//increases number of mines
				
				//display new number
				numMinesDisplayer.setText("" + mineCounter);
				
				//if the flag was removed from being on a mine
				if(logicArray[rows][cols] == MINE_PRESENT){
					flagCounter++; //increment
					//System.out.println(flagCounter);
				}
			}
		}
		//setting numClicks back to zero prevents 
		//unwanted increments or decrements in the number of mines
		numClicks = 0;
		winner(); //check if the user won yet
	}
	
	/**
	 * This method informs the user if they won
	 */
	public void winner()
	{
		//if mine counter isn't in the negatives
		//meaning the user isn't filling the whole grid
		//with flags to try to win, then check for winner
		//(the mine counter must be in the positive numbers
		//to win).
		if(mineCounter >= 0){
			//if all the flags are on the mines
			if(flagCounter == 0){
				time.stop(); //stop the time
				face.setIcon(WIN); //set a winning face
				JOptionPane.showMessageDialog(null, "YOU WON in " + 
						keepTime + " seconds!"); //tell the user they won
				addBackComponents(); //reset the game
			}
		}
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//ActionListener Method that starts the clock
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public void actionPerformed(ActionEvent event){
		
		if(event.getSource() == time){
			keepTime++;
			timeDisplayer.setText("" + keepTime);
		}
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//  Preforms actions on lables that are added to this Mouse
	//  Listener.
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	private class GridMouseListener implements MouseListener{
	//MouseListener Methods	
		public void mouseClicked(MouseEvent event){
		
		}
	    
		public void mousePressed(MouseEvent event){
			
			face.setIcon(UHOH);
			
			for(int rows=0; rows < num_rows; rows++){
				for(int cols=0; cols < num_cols; cols++){
					if(event.getSource() == gameArray[rows][cols]){
						if(gameArray[rows][cols].getIcon() != FLAG){
							gameArray[rows][cols].setIcon(CLICKED_MT);
						}
						else{
							return;
						}
					}
				}
			}
		}
	
		public void mouseReleased(MouseEvent event){
			
			face.setIcon(NORM);

			time.start();//start the time after mouse release
			
			for(int rows=0; rows < num_rows; rows++){
				for(int cols=0; cols < num_cols; cols++){
					if(event.getSource() == gameArray[rows][cols]){
						//if right clicked
						if(event.getButton() == MouseEvent.BUTTON3){ 
							if(gameArray[rows][cols].getIcon() != FLAG ||
							   gameArray[rows][cols].getIcon() == FLAG){
								flagging(rows, cols, event.getClickCount());
								return;
							}
						}
						//if left clicked and there is a flag don't do anything
						//if there isn't a flag do what it is supposed to do
						if(gameArray[rows][cols].getIcon() != FLAG){
							if(logicArray[rows][cols] == MINE_PRESENT){
								lose = true;
								time.stop();
								face.setIcon(LOSE);
								gameArray[rows][cols].setIcon(MINE_BOOM2);
								//represents the mine the user clicked on
								logicArray[rows][cols] = BOOM_HERE; 
								//disable clickabillity
								gameArray[rows][cols].removeMouseListener(listener);
							}
							else{
								openRegion(rows, cols);
								
							}
						}
						
					}
				}
			}			
			//if the user lost show the mines
			showAllMines();
			
		}
	
		public void mouseEntered(MouseEvent event){
			
		}
	
		public void mouseExited(MouseEvent event){
		
		}
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// preforms actions to lables that are added to this MouseListener 
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	private class FaceMouseListener implements MouseListener{
		//MouseListener Methods	
			public void mouseClicked(MouseEvent event){
			
			}
		
			public void mousePressed(MouseEvent event){
				
				face.setIcon(CLICK);
				
			}
		
			public void mouseReleased(MouseEvent event){
				
				face.setIcon(NORM);
				addBackComponents();

			}
		
			public void mouseEntered(MouseEvent event){
			
			}
		
			public void mouseExited(MouseEvent event){
			
			}
		}
}
