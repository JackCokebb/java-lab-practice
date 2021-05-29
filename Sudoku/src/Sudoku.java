import java.util.Random;


public class Sudoku {

	private final static int width = 9;
	private final static int height = 9;
	
	private int [][] sudokuAnswer = new int [width][height];
	private int [][] sudokuPuzzle = new int [width][height];
	
	Random rand = new Random(System.currentTimeMillis());
	private int input; // 0~9���� ������, ������ ĭ�� �� �ĺ���.
	

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
	
	int islocked(boolean [] flag) {                   //�ٸ� �ٰ� �Ǵ� ���� �ڽ������� �ߺ��Ǵ� ���� flagǥ��
		for(int w=0; w<10; w++) {                   
			if(flag[w] ==false) {                     
				return 1;
				}
			}
	return 0;
	}
	
	void initLine(int x) {                           //�������� 0���� �ʱ�ȭ
		for(int i = 0; i<width;i++) {
			sudokuAnswer[x][i]=0;
		}
	}
	
	int ruleCheck(int x, int y, int val) {            //������ ��Ģ�� ����Ǹ� return 0, ������� ������ return 1
		int center_x=0;                               // ���� 3*3�� ��ǥ�� �� ����
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
		
		if(x % 3 == 0)                             //�� x,y��ǥ�� ���Ե� 3*3�ڽ��� �߽���ǥ�� ���ϴ� ����
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
		
		for(int i=0; i<3; i++) {               // ���� �߽� ��ǥ �������� �� �ڽ����� �ߺ��Ǵ� ���� üũ
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
			 bound = rand.nextInt(41);        //���µ� 30~40�� ���� ���� 
		 }
		 if(bound%2 == 1)
			 sudokuPuzzle[4][4] =sudokuAnswer[4][4];       //��Ī�� ���� ������� ���µ� ���ڰ� Ȧ���Ϸ� �������� ��쿡�� ����.
		 
		 
		 for(int i =0; i<bound/2; i++) {                //��Ī���� ���� ����
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
    	 boolean [] numflag = new boolean [10];    //0~9���� ���� flag
    	 for(int w=0; w<10; w++) {
				numflag[w] = false;
			}
    	 
    	 initSudoku();
    	
    	
    	 for(int i =0; i < height; i++) {
    		 for(int j=0; j<width; j++) {
    			 while(true) {                     //����ġ�� �ߺ����� ���ڰ� ���� ���� �ݺ�
    			           
    			input= rand.nextInt(10);
    	    
    			
    			if(ruleCheck(i,j,input)==0) {      // �ߺ� Ȯ��, 0�̸� �ߺ�  
    				numflag[input] = true;         // �ٸ� �ٰ� �Ǵ� ���� �ڽ������� �ߺ��Ǵ� ���� flagǥ��
    				if(islocked(numflag)==0) 
    				 {                             // ��� flag�� true�̸� islock�޼ҵ�� 0 ����, 1~9���� �� �� �ִ� ���ڰ� ������ �ǹ�
    					for(int w=0; w<10; w++) {  // flag �ʱ�ȭ
    						numflag[w] = false;
    					}
    					 initLine(i);              // 1~9���� �� �� �ִ� ���ڰ� �����Ƿ� �� ���� ���� �Ұ�, �� �ٸ� �ʱ�ȭ
    					 j=0;
    				 }
    				continue;                      //while �� continue
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
