import java.util.Random;


public class Sudoku {

	private final static int width = 9;
	private final static int height = 9;
	
	private int [][] sudokuAnswer = new int [width][height];
	private int [][] sudokuPuzzle = new int [width][height];
	
	Random rand = new Random(System.currentTimeMillis());
	private int input; // 0~9까지 랜덤값, 스도쿠 칸에 들어갈 후보군.
	

	Sudoku() {
		initSudoku();
		generateAnswer();
		generatePuzzle();
	}
	
	void initSudoku() {
		for(int i = 0; i < height; i++) {
			for(int j =0; j< width; j++) {
				sudokuAnswer[i][j] = 0;
				sudokuPuzzle[i][j] = 0;
			}
		}
	}
	
	int islocked(boolean [] flag) {                   //다른 줄과 또는 같은 박스내에서 중복되는 숫자 flag표시
		for(int w=0; w<10; w++) {                   
			if(flag[w] ==false) {                     
				return 1;
				}
			}
	return 0;
	}
	
	void initLine(int x) {                           //가로줄을 0으로 초기화
		for(int i = 0; i<width;i++) {
			sudokuAnswer[x][i]=0;
		}
	}
	
	int ruleCheck(int x, int y, int val) {            //스도쿠 규칙에 위배되면 return 0, 위배되지 않으면 return 1
		int center_x=0;                               // 작은 3*3의 좌표가 될 변수
		int center_y=0;
		int test_x=0;
		int test_y=0;
		
	for(int i = 0; i< height; i++) {
			if(sudokuAnswer[i][y] == val)
				return 0;
		}
		for(int j = 0; j< width; j++) {
			if(sudokuAnswer[x][j] == val)
				return 0;
		}
		
		if(x % 3 == 0)                             //현 x,y좌표가 포함된 3*3박스의 중심좌표를 구하는 과정
			center_x = x+1;
		else if(x % 3 == 1)
			center_x = x;
		else if(x % 3 == 2)
			center_x = x-1;
		
		if(y % 3 == 0) 
			center_y = y+1;
		else if(y % 3 == 1)
			center_y = y;
		else if(y % 3 == 2)
			center_y = y-1;
		
		for(int i=0; i<3; i++) {               // 구한 중심 좌표 기준으로 그 박스내에 중복되는 숫자 체크
			for(int j = 0; j<3; j++) {
				if(sudokuAnswer[center_x -1 + i][center_y -1 + j] == val)
					return 0;
				
			}
		}
			
		
		
		return 1;
	}
	
	void generatePuzzle() {
		int bound=0;                     
		
		 while (bound <30) {
			 bound = rand.nextInt(41);        //오픈될 30~40개 숫자 개수 
		 }
		 if(bound%2 == 1)
			 sudokuPuzzle[4][4] =sudokuAnswer[4][4];       //대칭을 위해 정가운데는 오픈될 숫자가 홀수일로 결정됬을 경우에만 오픈.
		 
		 
		 for(int i =0; i<bound/2; i++) {                //대칭으로 숫자 오픈
			 while(true) {
			 int x = rand.nextInt(9);
			 int y = rand.nextInt(9);
			 
			 if(sudokuPuzzle[x][y]!=0)
				 continue;
			 else {
				 sudokuPuzzle[x][y] = sudokuAnswer[x][y];
				 sudokuPuzzle[8-x][8-y] = sudokuAnswer[8-x][8-y];
				 break;
				 }
				 
			 }
			 }
		 
		 
		 
		
	}
     void generateAnswer() {
    	//mycode here
    	 boolean [] numflag = new boolean [10];    //0~9까지 숫자 flag
    	 for(int w=0; w<10; w++) {
				numflag[w] = false;
			}
    	 
    	 initSudoku();
    	
    	
    	 for(int i =0; i < height; i++) {
    		 for(int j=0; j<width; j++) {
    			 while(true) {                     //현위치에 중복없이 숫자가 들어갈때 까지 반복
    			           
    			input= rand.nextInt(10);
    	    
    			
    			if(ruleCheck(i,j,input)==0) {      // 중복 확인, 0이면 중복  
    				numflag[input] = true;         // 다른 줄과 또는 같은 박스내에서 중복되는 숫자 flag표시
    				if(islocked(numflag)==0) 
    				 {                             // 모든 flag가 true이면 islock메소드는 0 리턴, 1~9까지 들어갈 수 있는 숫자가 없음을 의미
    					for(int w=0; w<10; w++) {  // flag 초기화
    						numflag[w] = false;
    					}
    					 initLine(i);              // 1~9까지 들어갈 수 있는 숫자가 없으므로 그 줄은 성립 불가, 그 줄만 초기화
    					 j=0;
    				 }
    				continue;                      //while 문 continue
    			}
    			else {
    				sudokuAnswer[i][j] = input;
    				break;
    			}
    			 }
    		 }
    	 }
	}
	
	void printSudoku (int[][] sudoku) {
		for (int i = 0; i <height; i++) {
			if(i % 3 == 0)
				System.out.print("+-----------------------+\n");
			for (int j =0; j<width; j++) {
				if(j %3==0)
					System.out.print("| ");
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.print("|\n");
		}
		System.out.print("+-----------------------+\n");
	}
	
	int[][] getAnswer(){
		return sudokuAnswer;
	}
	int[][]getPuzzle(){
		return sudokuPuzzle;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sudoku sudoku =new Sudoku();
		
		System.out.println(" # sudoku Puzzle #");
		sudoku.printSudoku(sudoku.getPuzzle());
		System.out.println("\n # Sudoku Answer #");
		sudoku.printSudoku(sudoku.getAnswer());

	}

}
