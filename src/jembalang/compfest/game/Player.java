package jembalang.compfest.game;

public class Player {
	private String name;
	private int levelOpened;
	private int[] scorePerLevel;
	private int[] weapons;
	private int[][] medals; //[level][medal]
	private int coin;
	
	public Player(String name,int levelOpened, int[] scorePerLevel, int[] weapons, int coin){
		this.name = name;
		this.levelOpened = levelOpened;
		this.scorePerLevel = scorePerLevel;
		this.weapons = weapons;
		this.coin = coin;
	}
	
	//Getter
	public String getName(){
		return name;
	}
	
	public int getLevelOpened(){
		return levelOpened;
	}
	
	public int[] getScorePerLevel(){
		return scorePerLevel;
	}
	
	public int[] getWeapons(){
		return weapons;
	}
	
	public int getCoin(){
		return coin;
	}
	
	//Setter
	public void setName(String name){
		this.name = name;
	}
	
	public void setLevelOpened(int levelOpened){
		this.levelOpened = levelOpened;
	}
	
	public void setScorePerLevel(int[] scorePerLevel){
		this.scorePerLevel = scorePerLevel;
	}
	
	public void setWeapon(int[] weapons){
		this.weapons = weapons;
	}
	
	public void setCoin(int coin){
		this.coin = coin;
	}
	
}
