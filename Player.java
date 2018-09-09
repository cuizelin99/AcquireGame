import java.util.*;
import java.lang.*;

public class Player implements Comparable<Player>
{
	private int sackson,zeta,hydra,fusion,america,phoenix,quantum;
	private int money;
	private Tile[] tiles;
	private boolean isAI;

	public Player(Tile[] tiles)
	{
		this.tiles = tiles;
		money=6000;
		sackson=0;
		zeta=0;
		hydra=0;
		fusion=0;
		america=0;
		phoenix=0;
		quantum=0;
		isAI = false;
	}
	public void changeThisCompany(Companies c,int x)
	{
		if(c instanceof Sackson)sackson+=x;
		else if(c instanceof Zeta)zeta+=x;
		else if(c instanceof America)america+=x;
		else if(c instanceof Hydra)hydra+=x;
		else if(c instanceof Fusion)fusion+=x;
		else if(c instanceof Quantum)quantum+=x;
		else if(c instanceof Phoenix)phoenix+=x;
	}
	public int getCompanyCount(Companies c)
	{
		if(c instanceof Sackson)return sackson;
		else if(c instanceof Zeta)return zeta;
		else if(c instanceof America)return america;
		else if(c instanceof Hydra)return hydra;
		else if(c instanceof Fusion)return fusion;
		else if(c instanceof Quantum)return quantum;
		else if(c instanceof Phoenix)return phoenix;
		return 0;
	}
	public Tile[] getTiles()
	{
		return tiles;
	}
	public void set$(int x)
	{
		money=x;
	}
	public void change$(int x)
	{
		money+=x;
	}
	public int get$()
	{
		return money;
	}
	public int getSackson()
	{
		return sackson;
	}
	public int getZeta()
	{
		return zeta;
	}
	public int getHydra()
	{
		return hydra;
	}
	public int getFusion()
	{
		return fusion;
	}
	public int getAmerica()
	{
		return america;
	}
	public int getPhoenix()
	{
		return phoenix;
	}
	public int getQuantum()
	{
		return quantum;
	}
	public void changeSackson(int x)
	{
		sackson+=x;
	}
	public void changeZeta(int x)
	{
		zeta+=x;
	}
	public void changeHydra(int x)
	{
		hydra+=x;
	}
	public void changeFusion(int x)
	{
		fusion+=x;
	}
	public void changeAmerica(int x)
	{
		america+=x;
	}
	public void changePhoenix(int x)
	{
		phoenix+=x;
	}
	public void changeQuantum(int x)
	{
		quantum+=x;
	}
	public void setTile(int x,Tile t)
	{
		tiles[x] = t;
	}
	public Tile useTile(int x)
	{
		return tiles[x];
	}
	public void setAI(boolean b)
	{
		isAI = b;
	}
	public boolean isAI()
	{
		return isAI;
	}
	public int compareTo(Player x)
	{
		if(x.get$()>money)return -1;

		else if(x.get$()<money)return 1;

		return 0;
	}
	public String toString()
	{
		if(isAI)return "SOME AI";

		else return "THE PLAYER";
	}
	public int getNetWealth(int s1,int z1, int h1, int f1, int a1, int q1, int p1)
	{
		return s1*getSackson()+z1*getZeta()+h1*getHydra()+f1*getFusion()+a1*getAmerica()+q1*getQuantum()+p1*getPhoenix();
	}
	public String getInfo(int s1, int z1, int h1,int f1, int a1, int q1, int p1)
	{
		return "Money Left: "+get$()+"\n"+"Sackson: "+getSackson()+"\n"+"Zeta: "+getZeta()+"\n"+"Hydra: "+getHydra()+"\n"+"Fusion: "+getFusion()+"\n"+"America: "+getAmerica()+"\n"+"Quantum: "+getQuantum()+"\n"+"Phoenix: "+getPhoenix()+"\n"+"Net Wealth: "+getNetWealth(s1,z1,h1,f1,a1,q1,p1);
	}
}