package jembalang.compfest.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import android.util.Log;

public class Player implements Serializable{
	public static final long serialVersionUID = 0x100F;
	public static final String PREFS_NAME = "BUG_MASSACRE";
	private String name;
	private int levelOpened;
	private int[] scorePerLevel;
	private Vector<Integer> weapons;
	private int[][] medals; //[level][medal]
	private int coin;
	private static Player player;
	private Player(){
		scorePerLevel = new int[10];
		coin = 200;
		weapons = new Vector<Integer>();
		weapons.add(Weapon.GUN);
		weapons.add(Weapon.SLOWER);
		weapons.add(Weapon.BURNER);
		weapons.add(Weapon.RUDAL);
	}
	public static Player getInstance() {
		return player;
	}
	public static void init (){
		player = new Player();
	}
	public static void init(FileInputStream fis){
		try {
			ObjectInputStream ob = new ObjectInputStream(fis);
			player = (Player) ob.readObject();
			ob.close();
		} catch (IOException ex){
			Log.e(MainMenu.APPNAME, "error init player from input", ex);
		} catch (ClassNotFoundException ex){
			Log.e(MainMenu.APPNAME, "error init player from input", ex);
		}
	}
	public static void save(FileOutputStream fos){
		try {
			ObjectOutputStream ob = new ObjectOutputStream(fos);
			ob.writeObject(player);
			ob.close();
		}catch (IOException ex) {
			Log.e(MainMenu.APPNAME, "error writing player", ex);
		}
		
	}
	private Player(String name,int levelOpened, int[] scorePerLevel, Vector<Integer> weapons, int coin){
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
	
	public Integer[] getWeapons(){
		Integer[] tmp = new Integer[1];
		return weapons.toArray(tmp);
	}
	public Vector<Integer> getWeaponVector(){
		return weapons;
	}
	public int getCoin() {
		return coin;
	}
	public boolean buy(int c){
		if (coin>=c){
			coin-=c;
			return true;
		}
		return false;
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
	
	public void addCoin(int c){
		coin += c;
	}
	
}
