import java.util.*;
import java.lang.*;

public class America extends Companies
{
	private int counts;
	private static final int max=25;
	private int stocksLeft;

	public America()
	{
		counts=0;
		stocksLeft=25;
	}
	public int getCounts()
	{
		return counts;
	}
	public void setCounts(int x)
	{
		counts = x;
	}
	public int getStocksLeft()
	{
		return stocksLeft;
	}
	public int getMajorityBonus()
	{
		if(counts>=41)
			return 11000;
		else if(counts>=31)
			return 10000;
		else if(counts>=21)
			return 9000;
		else if(counts>=11)
			return 8000;
		else if(counts>=6)
			return 7000;
		else if(counts==5)
			return 6000;
		else if(counts==4)
			return 5000;
		else if(counts==3)
			return 4000;
		else if(counts==2)
			return 3000;
		else
			return 0;
	}
	public int getMinorityBonus()
	{
		if(counts>=41)
			return 5500;
		else if(counts>=31)
			return 5000;
		else if(counts>=21)
			return 4500;
		else if(counts>=11)
			return 4000;
		else if(counts>=6)
			return 3500;
		else if(counts==5)
			return 3000;
		else if(counts==4)
			return 2500;
		else if(counts==3)
			return 2000;
		else if(counts==2)
			return 1500;
		else
			return 0;
	}
	public int getPrice()
	{
		if(counts>=41)
			return 1100;
		else if(counts>=31)
			return 1000;
		else if(counts>=21)
			return 900;
		else if(counts>=11)
			return 800;
		else if(counts>=6)
			return 700;
		else if(counts==5)
			return 600;
		else if(counts==4)
			return 500;
		else if(counts==3)
			return 400;
		else if(counts==2)
			return 300;
		else
			return 0;
	}
	public void changeCounts(int x)
	{
		counts+=x;
	}
	public void changeStocksLeft(int x)
	{
		stocksLeft+=x;
	}
}