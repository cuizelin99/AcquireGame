public class Tile
{
	int x;
	int y;
	int corp;
	Player p;
	public Tile(int a, int b)
	{
		x = a;
		y = b;
		corp = 0;//0 = no corp;1 = Sackson;2 = Zeta;3 = America;4 = Hydra;5 = Fusion;6 = Quantum;7 = Phoenix;
	}
	public void setPlayer(Player z){p = z;}
	public Player getPlayer(){return p;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getCorp(){return corp;}
	public boolean equals(Tile t)
	{
		if(t.getX()==x)
			if(t.getY()==y)return true;

		return false;
	}
	public boolean hasSameCorp(Tile t)
	{
		if(t.getCorp()==getCorp())return true;

		return false;
	}
	public void setNums(int a,int b)
	{
		x = a;
		y = b;
	}
	public void setCorp(int c)
	{
		corp = c;
	}
	public String toString()
	{
		return "Loc: "+x+","+y+" Corp: "+corp;
	}
}