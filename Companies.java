import java.util.*;
import java.lang.*;

public abstract class Companies implements Comparable<Companies>
{
	public abstract int getCounts();
	public abstract int getStocksLeft();
	public abstract int getMajorityBonus();
	public abstract int getMinorityBonus();
	public abstract int getPrice();
	public abstract void changeCounts(int x);
	public abstract void changeStocksLeft(int x);
	public int compareTo(Companies c)
	{
		if(getCounts()>c.getCounts())return 1;
		return -1;
	}
}