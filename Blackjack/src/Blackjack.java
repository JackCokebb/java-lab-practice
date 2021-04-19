import java.util.Random;
import java.util.Scanner;


public class Blackjack {
	public Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int  NumofPlayer;
		Player[] players;
		Card card = new Card();
		
		
		int seed = Integer.parseInt(args[0]);
		NumofPlayer = Integer.parseInt(args[1]);
		
        do {                                                    //if error occur, Game ends
		if(NumofPlayer>5) {
        	System.out.println("Error-Too many players");
        	break;}
         
		House House = new House();
		players = new Player[NumofPlayer];
		Deck deck = new Deck();
		deck.shuffle(seed);
		
		for(int i = 0; i <NumofPlayer; i++) {
			players[i] = new Player();              //player 생성
			players[i].setNum(i);}                  //player 번호 부여
			
			for (int j = 0; j < 2; j++) {
				for (int k =0; k < NumofPlayer; k++) {
					players[k].addCard(deck.dealCard());      // distribute card to players
					}
				House.addCard(deck.dealCard());	
				}
		
		System.out.printf("House: HIDDEN, %d%c", House.CardinHand[1].Value, House.CardinHand[1].Suit);   //House의 두번째 카드만 오픈
		System.out.println("");
		
		for(int i = 0; i <NumofPlayer; i++) {                  //player의 카드 표시
			System.out.print("Player"+(i+1)+":" );
			for(int j =0; j<players[i].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[i].CardinHand[j].Value),players[i].CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.println("("+players[i].Total()+")");
			} 
		
		
		boolean out = true;       //while break을 위한 불
		while(out) {
			if(House.Total()==21) {     //House가 blackjack이면 바로 게임 끝
				break;
			}
		Player.turn(deck,card,players,0);//player1 turn
		
		for(int i =1;i<NumofPlayer;i++) { //computer turn
		Computer.turn(deck, card, players,i);
		}
		break;
		}
		if(House.Total()!=21) {
		House.turn(deck, card, House);} // house turn
		
		
		System.out.println("");
		System.out.println("--- Game Results ---");		
		System.out.print("House:" );
		for(int j =0; j<House.numOfCards;j++) {
		System.out.printf( "%s%c",card.readNum(House.CardinHand[j].Value),House.CardinHand[j].Suit );
		System.out.print(", ");
		}
		
		System.out.println("("+House.Total()+")");
		
		if(House.Total()>21)
			System.out.println("- Bust!");
		else 
			System.out.println("");
		
		for(int i = 0; i <NumofPlayer; i++) {
			if(players[i].Total()>House.Total()&& players[i].Total()<22) { 
				System.out.print("[Win] ");
			}
			else if((players[i].Total()<22)&&(House.houseBust == true))
					System.out.print("[Win] ");
			else if((players[i].Total()<House.Total()&& players[i].Total()<22)&&(House.houseBust != true)) {
				System.out.print("[Lose] ");
			}
			else if(players[i].Total()>21){
				System.out.print("[Lose] ");
			}
			else if(players[i].Total()==House.Total()&&(House.houseBust != true)) {
				System.out.print("[Draw] ");
			}
			System.out.print("Player"+(i+1)+":" );
			for(int j =0; j<players[i].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[i].CardinHand[j].Value),players[i].CardinHand[j].Suit );

			System.out.print(", ");
			}
			System.out.print("("+players[i].Total()+")");
			if(players[i].Total()>21)
				System.out.println("- Bust!");
			else 
				System.out.println("");
			} 
		break;
		}while(NumofPlayer<=5);        
	}
}
	
class Card{
	
	public int Value;
	public char Suit;
	public Card() {
		Suit = ' ';
		Value = 0;
	}
	 
	public Card( char theSuit, int theValue) {
		if(theValue >13 || theValue<1) 
			System.out.println("Error-Invalid Card num input");
		else {
			Value = theValue;
		}
		if(theSuit != 'h'&& theSuit != 'c'&& theSuit != 'd'&& theSuit != 's') {
			System.out.println("Error-Invalid Card suit input");
		}
		else {
		Suit = theSuit;
		}
		}
	
	public String readNum(int value) {
		String s_value = "null";
		
		if(value == 1) {
     		s_value = "A";
		}
		else if(value == 2) {
			s_value = "2";
		}
		else if(value == 3) {
			s_value = "3";
		}
		else if(value == 4) {
			s_value = "4";
		}
		else if(value == 5) {
			s_value = "5";
		}
		else if(value == 6) {
			s_value = "6";
		}
		else if(value == 7) {
			s_value = "7";
		}
		else if(value == 8) {
			s_value = "8";
		}
		else if(value == 9) {
			s_value = "9";
		}
		else if(value == 10) {
			s_value = "10";
		}
		else if(value == 11) {
			s_value = "J";
		}
		else if(value == 12) {
			s_value = "Q";
		}
		else if(value == 13) {
			s_value = "K";
		}
		return s_value;
		}
	
	public int readValue() {
		int cardValue=0;
		
		if(this.Value<11 ){
		cardValue = this.Value;
		}
		else if(this.Value >=11) {
			cardValue = 10;           //J, Q, K 는 0으로 set
			}
		return cardValue;
	}
}// End of class Card

class Deck{
	public Card[] deck = new Card[52];
	private int cardsUsed; 
	public Deck() {    
		int count = 0;
	
	for(int i = 1; i <= 13; i++) {
		deck[count++] = new Card('h',i);
	}
	
	for(int i = 1; i <= 13; i++) {
		deck[count++] = new Card('s',i);
	}
	
	for(int i = 1; i <= 13; i++) {
		deck[count++] = new Card('d',i);
	}
	
	for(int i = 1; i <= 13; i++) {
		deck[count++] = new Card('c',i);
	}                                                // deck 초기화
	}
	
	public void shuffle(int seed) {
		Random random = new Random(seed);
		for(int i = deck.length-1; i>0; i--) {
			int rand = (int)(random.nextInt(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}
	
	public Card dealCard() {
		if(cardsUsed== deck.length)
			throw new IllegalStateException("No cards are left in the deck");
		cardsUsed++;
		return deck[cardsUsed-1];
	}
}
	
class Hand{ 
	public Card[] CardinHand = new Card[12];
	public int numOfCards = 0;
	
	public int Total() {          // 카드 값 총합 계산
		int total = 0;
		boolean IsAce = false;             //Ace 유무판정 - 1과 11중 선택후 계산하기 위한 불
		for (int i = 0; i < numOfCards; i++) {
			int value = CardinHand[i].readValue();
			if(value ==1) {
				IsAce = true;
			}
			total += value;
		}
		if(IsAce && total +11<=21) {
			total += 11;
		}
		return total;
	}
	
	public void addCard(Card card) {             //player hand에 카드추가
		CardinHand[numOfCards++] = card;
	}
	}//end of Hand class

class Computer extends Hand{
	
	public static void turn(Deck deck,Card card,Player[] players,int playernum) {
		System.out.println("");
		System.out.println("---Player"+(playernum+1)+" turn---");
		System.out.print("Player"+(playernum+1)+":" );
		for(int j =0; j<players[playernum].numOfCards;j++) {
		System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
		System.out.print(", ");
		
	}
		System.out.println("("+players[playernum].Total()+")");
		do {
		
		if(players[playernum].Total()<14) {
			System.out.println("Hit!");
			players[playernum].addCard(deck.dealCard());}
		
		else if(players[playernum].Total()>17) {
			System.out.println("Stand");
			System.out.print("Player"+(playernum+1)+":" );
			
			for(int j =0; j<players[playernum].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.println("("+players[playernum].Total()+")");
			break; }
		
		else if(players[playernum].Total()>=14&&players[playernum].Total()<=17) {
			Random random = new Random();
			int is_hit =(int)(random.nextInt(2));
			if(is_hit ==1) {
				System.out.println("Hit!");
				players[playernum].addCard(deck.dealCard());}
			else if(is_hit == 0) {
				System.out.println("Stand");
				System.out.print("Player"+(playernum+1)+":" );
				for(int j =0; j<players[playernum].numOfCards;j++) {
				System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
				System.out.print(", ");
				
			}
				System.out.println("("+players[playernum].Total()+")");
				break; // stand시 출력후 break
			}
			
		}
			
			System.out.print("Player"+playernum+":" );
			for(int j =0; j<players[playernum].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.print("("+players[playernum].Total()+")");
			if(players[playernum].Total()>21) {
				System.out.println("-Bust!");
				break;
			}
			else
				System.out.println("");
		}while (players[playernum].Total() <= 21 );
		}
	}//end of computer class

class Player extends Hand{
	int PlayerNum;	
	public Player() {
	Hand hand = new Hand();
	}
	
	public void setNum(int Num){
		 PlayerNum = Num;
	}
	
	public static void turn(Deck deck,Card card,Player[] players,int playernum) {
		String command;
		char headcomm;
		Scanner sct = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("---Player"+(playernum+1)+" turn---");
		System.out.print("Player"+(playernum+1)+":" );
		for(int j =0; j<players[playernum].numOfCards;j++) {
		System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
		System.out.print(", ");
		}
		System.out.println("("+players[playernum].Total()+")");
		
		do {
		do {
			command = sct.next();
			headcomm =command.toUpperCase().charAt(0); //입력의 머리문자값 대문자로 저장
			if(!(headcomm == 'H'||headcomm == 'S'))
				System.out.println("Error-Invalid input");
			}while(!(headcomm == 'H'||headcomm == 'S'));
		if(headcomm=='H') {         //Hit
			players[playernum].addCard(deck.dealCard());
			System.out.print("Player"+(playernum+1)+":" );
			for(int j =0; j<players[playernum].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.print("("+players[playernum].Total()+")");
			if(players[playernum].Total()>21) {
				System.out.println("-Bust!");
				break;
			}
			else
				System.out.println("");
		}
	}while (headcomm != 'S' && players[playernum].Total() <= 21 );
		if(players[playernum].Total()>21)  // Bust일 경우 다시 결과 출력하는것 방지
			System.out.print("");
		else if(headcomm == 'S') {         // Stand일 경우 결과 출력
 			System.out.print("Player"+(playernum+1)+":" );
			for(int j =0; j<players[playernum].numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(players[playernum].CardinHand[j].Value),players[playernum].CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.print("("+players[playernum].Total()+")");
		}
		
	}

}//end of player class

class House extends Hand{
	boolean houseBust =false;   //House가 bust인지 판단하는 불
	
	public void turn(Deck deck,Card card,House house) {
		
		System.out.println("");
		System.out.println("---House turn---");
		System.out.print("House: " );
		for(int j =0; j<house.numOfCards;j++) {
		System.out.printf( "%s%c",card.readNum(house.CardinHand[j].Value),house.CardinHand[j].Suit );
		System.out.print(", ");		
	}
		System.out.println("("+house.Total()+")");
		do {
		if(house.Total()<=16) {
			System.out.println("Hit!");
			house.addCard(deck.dealCard());}
		
		else if(house.Total()>=17) {
			System.out.println("Stand");
			System.out.print("House:" );
			for(int j =0; j<house.numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(house.CardinHand[j].Value),house.CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.print("("+house.Total()+")");
			break; }
			
			System.out.print("House:" );
			for(int j =0; j<house.numOfCards;j++) {
			System.out.printf( "%s%c",card.readNum(house.CardinHand[j].Value),house.CardinHand[j].Suit );
			System.out.print(", ");
			}
			System.out.print("("+house.Total()+")");
			if(house.Total()>21) {
				System.out.println("-Bust!");
				houseBust = true;
				break;                 //Bust!
			}
			else
				System.out.println("");
		}while (house.Total() <= 21 );}
}//end of House


