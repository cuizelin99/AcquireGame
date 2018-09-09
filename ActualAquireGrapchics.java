import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;

public class ActualAquireGrapchics
{
    public static void main(String[] args)
	{
		AquireWindow aq = new AquireWindow();
	}
}
class AquireDrawing extends java.applet.Applet
{
	public AquireDrawing()
	{
		setBackground(Color.BLACK);
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawRect(605,10,375,60);
		g.drawString("Player's Money: ",607,22);
		g.fillRect(590,460,50,50);
		g.setColor(Color.YELLOW);
		g.fillRect(590,520,50,50);
		g.setColor(new Color(255,125,0));
		g.fillRect(690,460,50,50);
		g.setColor(Color.GREEN);
		g.fillRect(690,520,50,50);
		g.setColor(Color.BLUE);
		g.fillRect(790,460,50,50);
		g.setColor(Color.CYAN);
		g.fillRect(790,520,50,50);
		g.setColor(Color.MAGENTA);
		g.fillRect(890,460,50,50);
		g.setColor(Color.WHITE);
		g.drawString("Buys left: ",605,445);
	}
}
class AquireWindow extends JFrame
{
	public void enableFrame(boolean b)
	{
		setEnabled(b);
	}
	//----------------------------------------------------------------Graphics--------------------
	JLabel money;
	JLabel sackson;
	JLabel zeta;
	JLabel hydra;
	JLabel america;
	JLabel fusion;
	JLabel phoenix;
	JLabel quantum;
	JLabel buysLeft;
	JButton buySackson;
	JButton buyZeta;
	JButton buyHydra;
	JButton buyAmerica;
	JButton buyFusion;
	JButton buyPhoenix;
	JButton buyQuantum;
	JButton buyStocks;
	JButton dontBuy;
	JButton endGame;
	JButton sCreate;
	JButton zCreate;
	JButton aCreate;
	JButton hCreate;
	JButton fCreate;
	JButton qCreate;
	JButton pCreate;
	JButton trade;
	JButton sell;
	JButton keep;
	JButton[] mActs;
	JButton[] tiles;
	JButton[] buyButtons;
	JButton[] createButtons;
	JTextArea eLog;
	JScrollPane eventLog;
	BoardPanel bp;
	//---------------------------------------------------------------Variables-------------------
	CorpWindow crate;
	Player human,comp1,comp2,comp3;
	Tile[] pTiles,c1Tiles,c2Tiles,c3Tiles;
	boolean tilePhase;
	boolean buyPhase;
	boolean endPhase;
	boolean AIPhase;
	Sackson s;
	Zeta z;
	America a;
	Fusion f;
	Hydra h;
	Phoenix p;
	Quantum q;
	int buys;
	//------------------------------------------------------------BOARDPANEL CLASS------------------------------------------------------------
	int activeCorp;
	class BoardPanel extends JPanel
	{
		int col;
		boolean contAI;
		Color[] rain;
		Tile[][] tilesPlaced;
		Tile[][] tilesNotPlaced;
		public void setAI(boolean b)
		{
			contAI = b;
		}
		public boolean isAICont()
		{
			return contAI;
		}
		public BoardPanel()
		{
			setBackground(Color.BLACK);
			setVisible(true);
			setBounds(0,0,600,450);
			contAI = false;
			col = 0;
			rain = new Color[8];
			rain[0] = Color.BLACK;
			rain[1] = Color.RED;
			rain[4] = new Color(255,125,0);
			rain[2] = Color.YELLOW;
			rain[5] = Color.GREEN;
			rain[6] = Color.CYAN;
			rain[3] = Color.BLUE;
			rain[7] = Color.MAGENTA;
			tilesPlaced = new Tile[12][9];
			tilesNotPlaced = new Tile[12][9];
			for(int x = 0;x<12;x++)
			{
				for(int y = 0;y<9;y++)
				{
					tilesNotPlaced[x][y] = new Tile(x,y);
				}
			}
		}
		public Player ownerOf(int a)
		{
			for(int x = 0;x<tilesPlaced.length;x++)
			{
				for(int y = 0;y<tilesPlaced[x].length;y++)
				{
					if(tilesPlaced[x][y].getCorp()==a)return tilesPlaced[x][y].getPlayer();
				}
			}
			return null;
		}
		public void givePlayerStock(Player p,int x)
		{
			if(x==1)p.changeSackson(1);

			else if(x==2)p.changeZeta(1);

			else if(x==3)p.changeAmerica(1);

			else if(x==4)p.changeHydra(1);

			else if(x==5)p.changeFusion(1);

			else if(x==6)p.changeQuantum(1);

			else if(x==7)p.changePhoenix(1);
		}
		public void paint(Graphics g)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,600,450);

			int a = 1;
			int b = 65;

			g.setColor(Color.GRAY);

			for(int x = 0;x<600;x+=50)
			{
				for(int y = 0;y<450;y+=50)
				{
					char c = (char)b;
					g.fillRect(x,y,49,49);
					g.setColor(Color.WHITE);
					g.drawString(a+""+c,x+7,y+30);
					g.setColor(Color.GRAY);
					b++;
				}
				a++;
				b=65;
			}
		}
		public void paintTile(Tile t, Graphics g)
		{
			g.setColor(rain[t.getCorp()]);
			g.fillRect(t.getX()*50,t.getY()*50,49,49);
			g.setColor(Color.WHITE);
			g.drawString((t.getX()+1)+""+((char)(t.getY()+65)),t.getX()*50+7,t.getY()*50+30);
			mergable(t);
		}
		public void repaintTileOnly(Tile t, Graphics g)
		{
			g.setColor(rain[t.getCorp()]);
			g.fillRect(t.getX()*50,t.getY()*50,49,49);
			g.setColor(Color.WHITE);
			g.drawString((t.getX()+1)+""+((char)(t.getY()+65)),t.getX()*50+7,t.getY()*50+30);
		}
		public void setColor(int x){col = x;}
		public void addTile(Tile t){tilesPlaced[t.getX()][t.getY()] = t;}
		public void mergable(Tile t)//----------------------------------------REWRITE------------------------------------------------------
		{
			ArrayList<Tile> neighs = getNeighbors(t);
			ArrayList<Tile> corpNeighs = new ArrayList<Tile>();
			for(int x = 0;x<neighs.size();x++)
			{
				if(neighs.get(x).getCorp()>0)
				{
					if(!corpNeighs.isEmpty())
					{
						boolean a = true;
						for(int y = 0;y<corpNeighs.size();y++)
						{
							if(neighs.get(x).hasSameCorp(corpNeighs.get(y)))
							{
								a = false;
								break;
							}
						}
						if(a)corpNeighs.add(neighs.get(x));
					}
				}
			}
			if(corpNeighs.size()>1)//At least merge once
			{
				TreeMap<Companies,Tile> compt = new TreeMap<Companies,Tile>();
				for(Tile d:corpNeighs)
				{
					compt.put(getCompany(d.getCorp()),d);
				}
				while(compt.size()>1)
				{
					Map.Entry<Companies,Tile> va = compt.pollLastEntry();
					Map.Entry<Companies,Tile> vb = compt.pollLastEntry();
					Tile ta = va.getValue();
					Tile tb = vb.getValue();
					Companies ca = vb.getKey();

					tb.getPlayer().change$(ca.getMinorityBonus());
					ta.getPlayer().change$(ca.getMajorityBonus());

					mergingActs ma = new mergingActs(ta,tb,t,compt.size()-1);
					updateThings();

					compt.put(getCompany(tb.getCorp()),tb);
					ta.setCorp(tb.getCorp());
					paintConnectedTiles(ta);
				}
			}
			else
			{
				addTile(t);
				checkForNeighbors(t);
			}
		}
		public Companies getCompany(int r)
		{
			if(r==1)return s;
			if(r==2)return z;
			if(r==3)return a;
			if(r==4)return h;
			if(r==5)return f;
			if(r==6)return q;
			if(r==7)return p;
			return null;
		}
		public boolean hasThisCorporation(int t)
		{
			for(int r=0;r<tilesPlaced.length;r++)
			{
				for(int c=0;c<tilesPlaced[r].length;c++)
				{
					if(tilesPlaced[r][c].getCorp()==t)
						return true;
				}
			}
			return false;
		}
		public boolean tileIsUsed(Tile t)
		{
			if(tilesNotPlaced[t.getX()][t.getY()]==null)return true;

			return false;
		}
		public boolean tilesAreLeft()
		{
			for(int x = 0;x<tilesNotPlaced.length;x++)
			{
				for(int y = 0;y<tilesNotPlaced[x].length;y++)
				{
					if(tilesNotPlaced[x][y]!=null)return true;
				}
			}
			return false;
		}
		public void useTile(Tile t)
		{
			tilesNotPlaced[t.getX()][t.getY()]=null;
		}
		public Tile getRandomTile()
		{
			int a = (int)(Math.random()*12);
			int b = (int)(Math.random()*9);
			Tile t = new Tile(a,b);

			while(tilesNotPlaced[a][b]==null)
			{
				a = (int)(Math.random()*12);
				b = (int)(Math.random()*9);
				t = new Tile(a,b);
				if(!isLegalTile(t))
				{
					useTile(t);
				}
			}
			useTile(t);
			return t;
		}
		public void checkForNeighbors(Tile t)
		{
			ArrayList<Tile> sed = getNeighbors(t);
			if(!sed.isEmpty())
			{
				for(int b = 0;b<sed.size();b++)
				{
					if(sed.get(b).getCorp()!=0)
					{
						paintConnectedTiles(sed.get(b));

						if(!contAI)
						{
							setButtonsOff(tiles);
							setButtonsOn(buyButtons);
						}
						else AIBuys(t.getPlayer());

						return;
					}
				}
				crate = new CorpWindow(t);
			}
			else if(!contAI)
			{
				setButtonsOff(tiles);
				setButtonsOn(buyButtons);
			}
		}
		public void paintConnectedTiles(Tile t)
		{
			repaintTileOnly(t,getGraphics());
			Tile x;

			if(t.getX()<tilesPlaced.length-1)
				if(tilesPlaced[t.getX()+1][t.getY()]!=null)
				{
					x = tilesPlaced[t.getX()+1][t.getY()];
					if(x.getCorp()!=t.getCorp())
					{
						x.setCorp(t.getCorp());
						x.setPlayer(t.getPlayer());
						repaintTileOnly(x,getGraphics());
						paintConnectedTiles(x);
					}
				}
			if(t.getX()>0)
				if(tilesPlaced[t.getX()-1][t.getY()]!=null)
				{
					x = tilesPlaced[t.getX()-1][t.getY()];
					if(x.getCorp()!=t.getCorp())
					{
						x.setCorp(t.getCorp());
						x.setPlayer(t.getPlayer());
						repaintTileOnly(x,getGraphics());
						paintConnectedTiles(x);
					}
				}
			if(t.getY()<tilesPlaced[0].length-1)
				if(tilesPlaced[t.getX()][t.getY()+1]!=null)
				{
					x = tilesPlaced[t.getX()][t.getY()+1];
					if(x.getCorp()!=t.getCorp())
					{
						x.setCorp(t.getCorp());
						x.setPlayer(t.getPlayer());
						repaintTileOnly(x,getGraphics());
						paintConnectedTiles(x);
					}
				}
			if(t.getY()>0)
				if(tilesPlaced[t.getX()][t.getY()-1]!=null)
				{
					x = tilesPlaced[t.getX()][t.getY()-1];
					if(x.getCorp()!=t.getCorp())
					{
						x.setCorp(t.getCorp());
						x.setPlayer(t.getPlayer());
						repaintTileOnly(x,getGraphics());
						paintConnectedTiles(x);
					}
				}
		}
		public boolean hasNeighbors(Tile t)
		{
			ArrayList<Tile> sed = getNeighbors(t);
			if(sed.isEmpty())
			{
				return false;
			}
			return true;
		}
		public int countStocks(int look)
		{
			int count = 0;
			for(int x = 0;x<tilesPlaced.length;x++)
			{
				for(int y = 0;y<tilesPlaced[x].length;y++)
				{
					if(tilesPlaced[x][y]!=null)
						if(tilesPlaced[x][y].getCorp()==look)count++;
				}
			}
			return count;
		}
		public boolean allStocksTaken()
		{
			if(bp.countStocks(1)>0&&bp.countStocks(2)>0&&bp.countStocks(3)>0&&bp.countStocks(4)>0&&bp.countStocks(5)>0&&bp.countStocks(6)>0&&bp.countStocks(6)>0)
				return true;

			return false;
		}
		public boolean isLegalTile(Tile t)
		{
			if(allStocksTaken())
				if(hasNeighbors(t))
				{
					ArrayList<Tile> h = getNeighbors(t);
					for(Tile r:h)
					{
						if(r.getCorp()>0)return true;
					}
					return false;
				}

			return true;
		}
		public ArrayList<Tile> getNeighbors(Tile t)
		{
			ArrayList<Tile> sed = new ArrayList<Tile>();
			Tile n;
			if(t.getX()<tilesPlaced.length-1)
			{
				if(tilesPlaced[t.getX()+1][t.getY()]!=null)
				{
					n = tilesPlaced[t.getX()+1][t.getY()];
					sed.add(n);
				}
			}
			if(t.getX()>0)
			{
				if(tilesPlaced[t.getX()-1][t.getY()]!=null)
				{
					n = tilesPlaced[t.getX()-1][t.getY()];
					sed.add(n);
				}
			}
			if(t.getY()<tilesPlaced[0].length-1)
			{
				if(tilesPlaced[t.getX()][t.getY()+1]!=null)
				{
					n = tilesPlaced[t.getX()][t.getY()+1];
					sed.add(n);
				}
			}
			if(t.getY()>0)
			{
				if(tilesPlaced[t.getX()][t.getY()-1]!=null)
				{
					n = tilesPlaced[t.getX()][t.getY()-1];
					sed.add(n);
				}
			}
			return sed;
		}
		public String corpName(int x)
		{
			if(x == 1)return "Sackson";
			if(x == 2)return "Zeta";
			if(x == 3)return "America";
			if(x == 4)return "Hydra";
			if(x == 5)return "Fusion";
			if(x == 6)return "Quantum";
			if(x == 7)return "Phoenix";
			return "";
		}
	}
	class CorpWindow implements ActionListener
	{
		Tile t;
		boolean b;
		public CorpWindow(Tile v)
		{
			setButtonsOn(createButtons);
			for(JButton g:createButtons)
			{
				if(g.getActionListeners().length!=0)for(int l = 0;l<g.getActionListeners().length;l++)g.removeActionListener(g.getActionListeners()[l]);

				g.addActionListener(this);
			}
			t = v;
			b = t.getPlayer().isAI();
			if(bp.countStocks(1)>0)sCreate.setEnabled(false);
			if(bp.countStocks(2)>0)zCreate.setEnabled(false);
			if(bp.countStocks(3)>0)aCreate.setEnabled(false);
			if(bp.countStocks(4)>0)hCreate.setEnabled(false);
			if(bp.countStocks(5)>0)fCreate.setEnabled(false);
			if(bp.countStocks(6)>0)qCreate.setEnabled(false);
			if(bp.countStocks(7)>0)pCreate.setEnabled(false);

			if(b)AIchoosing();
		}
		public void actionPerformed(ActionEvent e)
		{
			String s = ((JButton)e.getSource()).getText();

			if(s.charAt(0)=='S')
			{
				activeCorp = 1;
				t.getPlayer().changeSackson(1);
			}
			else if(s.charAt(0)=='Z')
			{
				activeCorp = 2;
				t.getPlayer().changeZeta(1);
			}
			else if(s.charAt(0)=='A')
			{
				activeCorp = 3;
				t.getPlayer().changeAmerica(1);
			}
			else if(s.charAt(0)=='H')
			{
				activeCorp = 4;
				t.getPlayer().changeHydra(1);
			}
			else if(s.charAt(0)=='F')
			{
				activeCorp = 5;
				t.getPlayer().changeFusion(1);
			}
			else if(s.charAt(0)=='Q')
			{
				activeCorp = 6;
				t.getPlayer().changeQuantum(1);
			}
			else if(s.charAt(0)=='P')
			{
				activeCorp = 7;
				t.getPlayer().changePhoenix(1);
			}
			t.setCorp(activeCorp);
			bp.paintConnectedTiles(t);
			updateThings();
			activeCorp = 0;
			setButtonsOff(createButtons);
			setButtonsOff(tiles);
			setButtonsOn(buyButtons);
		}
		public void AIchoosing()
		{
			ArrayList<Integer> stck = new ArrayList<Integer>();
			if(bp.countStocks(1)==0)stck.add(1);
			if(bp.countStocks(2)==0)stck.add(2);
			if(bp.countStocks(3)==0)stck.add(3);
			if(bp.countStocks(4)==0)stck.add(4);
			if(bp.countStocks(5)==0)stck.add(5);
			if(bp.countStocks(6)==0)stck.add(6);
			if(bp.countStocks(7)==0)stck.add(7);

			int rng = (int)(Math.random()*stck.size());

			activeCorp = stck.get(rng);

			if(activeCorp==1)
			{
				t.getPlayer().changeSackson(1);
			}
			else if(activeCorp==2)
			{
				t.getPlayer().changeZeta(1);
			}
			else if(activeCorp==3)
			{
				t.getPlayer().changeAmerica(1);
			}
			else if(activeCorp==4)
			{
				t.getPlayer().changeHydra(1);
			}
			else if(activeCorp==5)
			{
				t.getPlayer().changeFusion(1);
			}
			else if(activeCorp==6)
			{
				t.getPlayer().changeQuantum(1);
			}
			else if(activeCorp==7)
			{
				t.getPlayer().changePhoenix(1);
			}

			t.setCorp(activeCorp);
			bp.paintConnectedTiles(t);
			updateThings();
			activeCorp = 0;
			setButtonsOff(createButtons);
			AIBuys(t.getPlayer());
		}
	}
	//------------------------------------------------------------COLORBUTTON CLASS-------------------------------------------------------------
	class ColorButton implements ActionListener
	{
		Tile t;
		public ColorButton(Tile d)
		{
			t = d;
		}
		public void actionPerformed(ActionEvent e)
		{
			JButton b = (JButton)e.getSource();

			bp.paintTile(t,bp.getGraphics());
			eLog.append("\nTile placed at "+(t.getX()+1)+""+((char)(t.getY()+65)));
			setButtonsOff(tiles);

			if(bp.tilesAreLeft())
			{
				Tile temp = bp.getRandomTile();
				temp.setPlayer(t.getPlayer());
				t = temp;
				b.setText((t.getX()+1)+""+((char)(t.getY()+65)));
			}
			else
			{
				b.setText("");
				b.setEnabled(false);
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------------------------------
	class mergingActs implements ActionListener
	{
		Tile t1;//Company that tiles belong to that ate other comapny
		Tile t2;//Eaten Company
		Tile origin;
		int stockCount;
		int mergesLeft;
		Player merger;
		Player merged;
		Companies eater;
		Companies eaten;
		public mergingActs(Tile sadf,Tile md,Tile o,int ml)
		{
			setButtonsOn(mActs);
			for(JButton g:mActs)
			{
				if(g.getActionListeners().length!=0)for(int l = 0;l<g.getActionListeners().length;l++)g.removeActionListener(g.getActionListeners()[l]);

				g.addActionListener(this);
			}
			t1 = sadf;
			t2 = md;
			origin = o;
			merger = t1.getPlayer();
			merged = t2.getPlayer();//if merged is player, do action performed
			stockCount = getStockCount(t2.getCorp());
			eater = getCompany(t1.getCorp());
			eaten = getCompany(t2.getCorp());
			mergesLeft = ml;
			updateThings();
			if(t2.getPlayer().isAI())AIMerging();
		}
		public int getStockCount(int g)
		{
			if(g==1)return merged.getSackson();
			if(g==2)return merged.getZeta();
			if(g==3)return merged.getAmerica();
			if(g==4)return merged.getHydra();
			if(g==5)return merged.getFusion();
			if(g==6)return merged.getQuantum();
			if(g==7)return merged.getPhoenix();
			return 0;
		}
		public void actionPerformed(ActionEvent e)
		{
			char ex = ((JButton)e.getSource()).getText().charAt(0);
			if(stockCount>0)
			{
				if(ex=='T')
				{
					if(eater.getStocksLeft()>0&&stockCount>1)
					{
						stockCount-=2;
						eaten.changeStocksLeft(2);
						eater.changeStocksLeft(-1);
						merged.changeThisCompany(eaten,-2);
						merged.changeThisCompany(eater,1);
						updateThings();
					}
					else
					{
						eLog.append("\nInsufficient stocks");
					}
				}
				else if(ex=='S')
				{
					if(merged.getCompanyCount(eaten)>0)
					{
						stockCount--;
						merged.changeThisCompany(eaten,-1);
						merged.change$(eaten.getPrice());
						eaten.changeStocksLeft(1);
						updateThings();
					}
					else
					{
						eLog.append("\nInsufficient stocks");
					}
				}
				else if(ex=='K')
				{
					stockCount--;
				}
			}
			if(stockCount<=0)
			{
				if(mergesLeft==0)
				{
					bp.addTile(origin);
					bp.checkForNeighbors(origin);
				}
			}
		}
		public void AIMerging()
		{
			while(stockCount>0)
			{
				int v = (int)(Math.random()*3);

				if(v==0)
				{
					if(eater.getStocksLeft()>0&&stockCount>1)
					{
						stockCount-=2;
						eaten.changeStocksLeft(2);
						eater.changeStocksLeft(-1);
						merged.changeThisCompany(eaten,-2);
						merged.changeThisCompany(eater,1);
						updateThings();
					}
				}
				else if(v==1)
				{
					if(merged.getCompanyCount(eaten)>0)
					{
						stockCount--;
						merged.changeThisCompany(eaten,-1);
						merged.change$(eaten.getPrice());
						eaten.changeStocksLeft(1);
						updateThings();
					}
				}
				else if(v==2)
				{
					stockCount--;
				}
			}
			if(mergesLeft==0)
			{
				bp.addTile(origin);
				bp.checkForNeighbors(origin);
			}
		}
		public Companies getCompany(int r)
		{
			if(r==1)return s;
			if(r==2)return z;
			if(r==3)return a;
			if(r==4)return h;
			if(r==5)return f;
			if(r==6)return q;
			if(r==7)return p;
			return null;
		}
	}
	//--------------------------------------------------------------FRAME CONSTRUCTOR-----------------------------------------------------------
	public AquireWindow()
    {
		super("Aquire");
		s = new Sackson();
		z = new Zeta();
		a = new America();
		h = new Hydra();
		f = new Fusion();
		q = new Quantum();
		p = new Phoenix();
		setSize(1000,650);
		setVisible(true);
		setResizable(false);
    	//Variables------------------------------------------------------------------
		pTiles = new Tile[6];
		c1Tiles = new Tile[6];
		c2Tiles = new Tile[6];
		c3Tiles = new Tile[6];
		//Graphics-------------------------------------------------------------------
		eLog = new JTextArea();
		eLog.setEditable(false);
		eventLog = new JScrollPane(eLog);
		eventLog.setBounds(605,80,375,100);
		eventLog.setVerticalScrollBar(new JScrollBar());
		add(eventLog);
		bp = new BoardPanel();
		add(bp);
		//--------------------------------------------------------------------------------------------------------
		for(int x = 0;x<6;x++)
		{
			Tile check = bp.getRandomTile();
			bp.useTile(check);
			pTiles[x] = check;
		}
		for(int x = 0;x<6;x++)
		{
			Tile check = bp.getRandomTile();
			bp.useTile(check);
			c1Tiles[x] = check;
		}
		for(int x = 0;x<6;x++)
		{
			Tile check = bp.getRandomTile();
			bp.useTile(check);
			c2Tiles[x] = check;
		}
		for(int x = 0;x<6;x++)
		{
			Tile check = bp.getRandomTile();
			bp.useTile(check);
			c3Tiles[x] = check;
		}
		human = new Player(pTiles);
		human.setAI(false);
		comp1 = new Player(c1Tiles);
		comp1.setAI(true);
		comp2 = new Player(c2Tiles);
		comp2.setAI(true);
		comp3 = new Player(c3Tiles);
		comp3.setAI(true);
		for(int x = 0;x<6;x++)
		{
			human.getTiles()[x].setPlayer(human);
		}
		for(int x = 0;x<6;x++)
		{
			comp1.getTiles()[x].setPlayer(comp1);
		}
		for(int x = 0;x<6;x++)
		{
			comp2.getTiles()[x].setPlayer(comp2);
		}
		for(int x = 0;x<6;x++)
		{
			comp3.getTiles()[x].setPlayer(comp3);
		}
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(human);
		players.add(comp1);
		players.add(comp2);
		players.add(comp3);
		//---------------------------------------------------------------------------------
		tiles = new JButton[6];
		for(int x = 0;x<6;x++)
		{
			tiles[x] = new JButton();
			tiles[x].setBounds(x*60+50,500,60,60);
			tiles[x].setText((pTiles[x].getX()+1)+""+((char)(pTiles[x].getY()+65)));
			tiles[x].addActionListener(new ColorButton(pTiles[x]));
			add(tiles[x]);
		}
		//-------------------------------------------------------------------------------------
		for(int opo=1;opo<5;opo++)
		{
			int zz=opo;
			JButton info = new JButton("Player "+zz+" Info");
			info.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{

					JFrame infoFrame = new JFrame("Player "+zz+" Information");
					JLabel label = new JLabel("Info");
					label.setText(players.get(zz-1).getInfo(s.getPrice(),z.getPrice(),h.getPrice(),f.getPrice(),a.getPrice(),q.getPrice(),p.getPrice()));
					infoFrame.add(label);
					infoFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
					infoFrame.setBounds(700,200,675,300);
					infoFrame.setVisible(true);
					//infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				}
			});
			info.setBounds(825,190+zz*30,150,25);
        	info.setVisible(true);
        	info.setForeground(Color.white);
        	info.setBackground(Color.gray);
        	add(info);
		}
		//------------------------------------------------------------------------------------------------------
		sCreate = new JButton("Sackson");
		sCreate.setBounds(605,190,100,30);
		sCreate.setEnabled(false);
		add(sCreate);
		zCreate = new JButton("Zeta");
		zCreate.setBounds(605,225,100,30);
		zCreate.setEnabled(false);
		add(zCreate);
		aCreate = new JButton("America");
		aCreate.setBounds(605,260,100,30);
		aCreate.setEnabled(false);
		add(aCreate);
		hCreate = new JButton("Hydra");
		hCreate.setBounds(605,295,100,30);
		hCreate.setEnabled(false);
		add(hCreate);
		fCreate = new JButton("Fusion");
		fCreate.setBounds(605,330,100,30);
		fCreate.setEnabled(false);
		add(fCreate);
		qCreate = new JButton("Quantum");
		qCreate.setBounds(605,365,100,30);
		qCreate.setEnabled(false);
		add(qCreate);
		pCreate = new JButton("Phoenix");
		pCreate.setBounds(605,400,100,30);
		pCreate.setEnabled(false);
		add(pCreate);
		//------------------------------------------------------------------------------------------------------
		money = new JLabel();
		money.setBounds(692,13,40,10);
		money.setText("6000");
		add(money);
		sackson = new JLabel();
		sackson.setBounds(641,477,20,10);
		sackson.setText("0");
		add(sackson);
		zeta = new JLabel();
		zeta.setBounds(641,537,20,10);
		zeta.setText("0");
		add(zeta);
		hydra = new JLabel();
		hydra.setBounds(741,477,20,10);
		hydra.setText("0");
		add(hydra);
		fusion = new JLabel();
		fusion.setBounds(741,537,20,10);
		fusion.setText("0");
		add(fusion);
		america = new JLabel();
		america.setBounds(841,477,20,10);
		america.setText("0");
		add(america);
		quantum = new JLabel();
		quantum.setBounds(841,537,20,10);
		quantum.setText("0");
		add(quantum);
		phoenix = new JLabel();
		phoenix.setBounds(941,477,20,10);
		phoenix.setText("0");
		add(phoenix);
		buySackson = new JButton();
		buySackson.setBounds(641,491,25,20);
		buySackson.setText("S");
		BuyStocks bs = new BuyStocks();
		buySackson.addActionListener(bs);
		add(buySackson);
		buyZeta = new JButton();
		buyZeta.setBounds(641,551,25,20);
		buyZeta.setText("Z");
		buyZeta.addActionListener(bs);
		add(buyZeta);
		buyHydra = new JButton();
		buyHydra.setBounds(741,491,25,20);
		buyHydra.setText("H");
		buyHydra.addActionListener(bs);
		add(buyHydra);
		buyFusion = new JButton();
		buyFusion.setBounds(741,551,25,20);
		buyFusion.setText("F");
		buyFusion.addActionListener(bs);
		add(buyFusion);
		buyAmerica = new JButton();
		buyAmerica.setBounds(841,491,25,20);
		buyAmerica.setText("A");
		buyAmerica.addActionListener(bs);
		add(buyAmerica);
		buyQuantum = new JButton();
		buyQuantum.setBounds(841,551,25,20);
		buyQuantum.setText("Q");
		buyQuantum.addActionListener(bs);
		add(buyQuantum);
		buyPhoenix = new JButton();
		buyPhoenix.setBounds(941,491,25,20);
		buyPhoenix.setText("P");
		buyPhoenix.addActionListener(bs);
		add(buyPhoenix);
		dontBuy = new JButton();
		dontBuy.setBounds(890,520,75,50);
		dontBuy.setText("Dont");
		dontBuy.addActionListener(bs);
		add(dontBuy);
		buyButtons = getBuyButtons();
		for(JButton asd:buyButtons)asd.setEnabled(false);
		endGame = new JButton("End Game");
		endGame.setEnabled(false);
		endGame.setBounds(455,525,100,49);
		endGame.addActionListener(new endIt());
		buysLeft = new JLabel();
		buysLeft.setBounds(655,436,20,10);
		buysLeft.setText("3");
		add(buysLeft);
		add(endGame);
		createButtons = new JButton[7];
		createButtons[0] = sCreate;
		createButtons[1] = zCreate;
		createButtons[2] = aCreate;
		createButtons[3] = hCreate;
		createButtons[4] = fCreate;
		createButtons[5] = qCreate;
		createButtons[6] = pCreate;
		trade = new JButton("Trade");
		trade.setBounds(710,190,100,60);
		trade.setEnabled(false);
		add(trade);
		sell = new JButton("Sell");
		sell.setBounds(710,255,100,60);
		sell.setEnabled(false);
		add(sell);
		keep = new JButton("Keep");
		keep.setBounds(710,320,100,60);
		keep.setEnabled(false);
		add(keep);
		mActs = new JButton[3];
		mActs[0] = trade;
		mActs[1] = sell;
		mActs[2] = keep;
		//--------------------------------------------------------------------------------------------------------
		tilePhase = true;
		buyPhase = false;
		endPhase = false;
		AIPhase = false;
		//--------------------------------------------------------------------------------------------------------
		getContentPane().add(new AquireDrawing());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class endIt implements ActionListener
	{
		public endIt(){}
		public void actionPerformed(ActionEvent e)
		{
			char c = ((JButton)(e.getSource())).getText().charAt(0);
			if(c=='E')endGame();
			else if(c=='Q')disposeWindow();
		}
	}
	public void disposeWindow()
	{
		dispose();
	}
	public boolean endable()
	{
		if((bp.countStocks(1)>=41||bp.countStocks(2)>=41||bp.countStocks(3)>=41||bp.countStocks(4)>=41||bp.countStocks(5)>=41||bp.countStocks(6)>=41||bp.countStocks(7)>=41)||
			(bp.countStocks(1)>=11&&bp.countStocks(2)>=11&&bp.countStocks(3)>=11&&bp.countStocks(4)>=11&&bp.countStocks(5)>=11&&bp.countStocks(6)>=11&&bp.countStocks(7)>=11))
				return true;

		return false;
	}
	public void setButtonsOff(JButton[] jbs)
	{
		for(JButton x:jbs)x.setEnabled(false);
	}
	public void setButtonsOn(JButton[] jbs)
	{
		for(JButton x:jbs)x.setEnabled(true);
	}
	public void endGame()
	{
		ArrayList<Player> ppl = new ArrayList<Player>();
		ppl.add(human);
		ppl.add(comp1);
		ppl.add(comp2);
		ppl.add(comp3);

		Collections.sort(ppl);

		eLog.append("\nAND THE WINNER IS "+ppl.get(ppl.size()-1));
		setButtonsOff(tiles);
		setButtonsOff(buyButtons);
		endGame.setEnabled(true);
		endGame.setText("Quit");
	}
	public JButton[] getBuyButtons()
	{
		JButton[] butts = new JButton[8];
		butts[0] = buySackson;
		butts[1] = buyZeta;
		butts[2] = buyAmerica;
		butts[3] = buyFusion;
		butts[4] = buyHydra;
		butts[5] = buyQuantum;
		butts[6] = buyPhoenix;
		butts[7] = dontBuy;
		return butts;
	}
	public void updateThings()
	{
		s.setCounts(bp.countStocks(1));
		z.setCounts(bp.countStocks(2));
		a.setCounts(bp.countStocks(3));
		h.setCounts(bp.countStocks(4));
		f.setCounts(bp.countStocks(5));
		q.setCounts(bp.countStocks(6));
		p.setCounts(bp.countStocks(7));
		money.setText(human.get$()+"");
		sackson.setText(human.getSackson()+"");
		zeta.setText(human.getZeta()+"");
		america.setText(human.getAmerica()+"");
		hydra.setText(human.getHydra()+"");
		fusion.setText(human.getFusion()+"");
		quantum.setText(human.getQuantum()+"");
		phoenix.setText(human.getPhoenix()+"");
		if(endable())endGame.setEnabled(true);
	}
	class BuyStocks implements ActionListener
	{
		public BuyStocks()
		{
			buys = 3;
		}
		public void actionPerformed(ActionEvent e)
		{
			char c = ((JButton)e.getSource()).getText().charAt(0);
			if(buys>0)
			{
				if(c=='S')
				{
					if(s.getStocksLeft()>0&&bp.countStocks(1)>1&&human.get$()>s.getPrice())
					{
						human.changeSackson(1);
						human.change$(0-s.getPrice());
						//buys--;
					}
				}
				else if(c=='Z')
				{
					if(z.getStocksLeft()>0&&bp.countStocks(2)>1&&human.get$()>z.getPrice())
					{
						human.changeZeta(1);
						human.change$(0-z.getPrice());
						//buys--;
					}
				}
				else if(c=='A')
				{
					if(a.getStocksLeft()>0&&bp.countStocks(3)>1&&human.get$()>a.getPrice())
					{
						human.changeAmerica(1);
						human.change$(0-a.getPrice());
						//buys--;
					}
				}
				else if(c=='H')
				{
					if(h.getStocksLeft()>0&&bp.countStocks(4)>1&&human.get$()>h.getPrice())
					{
						human.changeHydra(1);
						human.change$(0-h.getPrice());
						//buys--;
					}
				}
				else if(c=='F')
				{
					if(f.getStocksLeft()>0&&bp.countStocks(5)>1&&human.get$()>f.getPrice())
					{
						human.changeFusion(1);
						human.change$(0-f.getPrice());
						//buys--;
					}
				}
				else if(c=='Q')
				{
					if(q.getStocksLeft()>0&&bp.countStocks(6)>1&&human.get$()>q.getPrice())
					{
						human.changeQuantum(1);
						human.change$(0-q.getPrice());
						//buys--;
					}
				}
				else if(c=='P')
				{
					if(p.getStocksLeft()>0&&bp.countStocks(7)>1&&human.get$()>p.getPrice())
					{
						human.changePhoenix(1);
						human.change$(0-p.getPrice());
						//buys--;
					}
				}
				updateThings();
				buys--;
				buysLeft.setText(buys+"");
			}
			if(buys==0)
			{
				for(JButton r:buyButtons)r.setEnabled(false);
				Player[] coos = {comp1,comp2,comp3};
				buys = 3;
				buysLeft.setText(buys+"");
				runAI(coos);
			}
		}
	}
	public void AIBuys(Player c)
	{
		for(int bys = 0;bys<3;bys++)
		{
			int rng2 = (int)(Math.random()*8);
			char[] stacks = {'S','Z','A','H','F','Q','P','N'};

			if(stacks[rng2]=='S')
			{
				if(s.getStocksLeft()>0&&bp.countStocks(1)>1&&c.get$()>s.getPrice())
				{
					s.changeStocksLeft(-1);
					c.change$(-s.getPrice());
					c.changeSackson(1);
				}
			}
			else if(stacks[rng2]=='Z')
			{
				if(z.getStocksLeft()>0&&bp.countStocks(2)>1&&c.get$()>z.getPrice())
				{
					z.changeStocksLeft(-1);
					c.change$(-z.getPrice());
					c.changeZeta(1);
				}
			}
			else if(stacks[rng2]=='A')
			{
				if(a.getStocksLeft()>0&&bp.countStocks(3)>1&&c.get$()>a.getPrice())
				{
					a.changeStocksLeft(-1);
					c.change$(-a.getPrice());
					c.changeAmerica(1);
				}
			}
			else if(stacks[rng2]=='H')
			{
				if(h.getStocksLeft()>0&&bp.countStocks(4)>1&&c.get$()>h.getPrice())
				{
					h.changeStocksLeft(-1);
					c.change$(-h.getPrice());
					c.changeHydra(1);
				}
			}
			else if(stacks[rng2]=='F')
			{
				if(f.getStocksLeft()>0&&bp.countStocks(5)>1&&c.get$()>f.getPrice())
				{
					f.changeStocksLeft(-1);
					c.change$(-f.getPrice());
					c.changeFusion(1);
				}
			}
			else if(stacks[rng2]=='Q')
			{
				if(q.getStocksLeft()>0&&bp.countStocks(6)>1&&c.get$()>q.getPrice())
				{
					q.changeStocksLeft(-1);
					c.change$(-q.getPrice());
					c.changeQuantum(1);
				}
			}
			else if(stacks[rng2]=='P')
			{
				if(p.getStocksLeft()>0&&bp.countStocks(7)>1&&c.get$()>p.getPrice())
				{
					p.changeStocksLeft(-1);
					c.change$(-p.getPrice());
					c.changePhoenix(1);
				}
			}
		}
		if(endable())
		{
			endGame();
			return;
		}
	}
	public void runAI(Player[] cpu)
	{
		for(Player c:cpu)
		{
			int rng = (int)(Math.random()*6);

			Tile rngTile = c.useTile(rng);

			bp.setAI(true);
			bp.paintTile(rngTile,bp.getGraphics());
			eLog.append("\nTile placed at "+(rngTile.getX()+1)+""+((char)(rngTile.getY()+65)));
			bp.setAI(false);

			if(bp.tilesAreLeft())
			{
				Tile temp = bp.getRandomTile();
				temp.setPlayer(c);
				c.setTile(rng,temp);
			}
		}
		for(JButton rel:tiles)rel.setEnabled(true);
	}
}