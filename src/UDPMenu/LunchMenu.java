package UDPMenu;

import java.util.*;


public class LunchMenu
{
	private static final String[] Lunch = {"Kimbab", "Ramen", "Pasta", "Ice noodles", "Chicken", "Water"};
	
	public static String selectMenu()
	{
		return Lunch[new Random().nextInt(6)];
	}
	
	
}
