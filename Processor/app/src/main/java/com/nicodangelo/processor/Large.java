package com.nicodangelo.processor;

import java.util.ArrayList;
public class Large
{
	ArrayList<Integer> num;
	public Large(String s)
	{
		if(Character.toString(s.charAt(0)).equals("-"))
		{
			try 
			{
				throw new Exception("Negative numbers are not allowed");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			num = new ArrayList<Integer>();
			int spot = s.length() - 1;
			while(num.size() < s.length())
			{
				num.add(convertInt(s,spot));
				spot--;
			}
		}
	}
	public Large(int i)
	{
		if(i < 0)
		{
			try
			{
				throw new Exception("Negative numbers are not allowed");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			String s = Integer.toString(i);
			num = new ArrayList<Integer>();
			int spot = s.length() - 1;
			while(num.size() < s.length())
			{
				num.add(convertInt(s,spot));
				spot--;
			}
		}
		
	}
	public Large(long i)
	{
		if(i > 0)
		{
			try
			{
				throw new Exception("Negative numbers are not allowed");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			String s = Long.toString(i);
			num = new ArrayList<Integer>();
			int spot = s.length() - 1;
			while(num.size() < s.length())
			{
				num.add(convertInt(s,spot));
				spot--;
			}
		}
	}
	private Large(ArrayList<Integer> n)
	{
		num = new ArrayList<Integer>();
		for(int k = 0; k < n.size(); k++)
			num.add(n.get(k));
	}
	public void add(Large l)
	{
		if(l.getSize() > num.size())
		{
			while(num.size() < l.getSize())
			{
				num.add(new Integer(0));
			}
		}
		if(l.getSize() == num.size() || l.getSize() < num.size())
		{
			for(int k = 0; k < l.getSize(); k++)
			{
				int temp = num.get(k) + l.getDigit(k);
//				print("Add Temp: "+ temp);
				if(Integer.toString(temp).length() == 2)
				{
					String str = Integer.toString(temp);
					if(k + 2 > num.size())
					{
						num.add(new Integer(0));
					}
					int temp2 = convertInt(str, 0) + num.get(k + 1);
					num.set(k + 1, temp2);
					num.set(k, convertInt(str, 1));	
				}
				else
					num.set(k, temp);
			}
		}
		
	}
	public void sub(Large l)
	{
		if(num.size() > l.getSize())
		{
//			System.out.println("a.size > b.size");
			while(l.getSize() < num.size())
				l.addZero();
		}
		else if(num.size() < l.getSize())
		{
//			System.out.println("a.size < b.size");
			while(num.size() < l.getSize())
				num.add(new Integer(0));
		}
		if(!isLarger(num,l))
		{
//			System.out.println("a < b");
	/*		for(int k = 0; k < num.size(); k++)
			{
				print(num.get(k));
			} */
			for(int k = 0; k < l.getSize(); k++)
			{
				int a = num.get(k);
				int b = l.getDigit(k);
				num.set(k, b);
				l.setDigit(k, a);
			}
	/*		for(int k = 0; k < num.size(); k++)
			{
				print(num.get(k));
			}*/
		}
		if(num.size() == l.getSize())
		{
			for(int k = 0; k < l.getSize(); k++)
			{
//				print("Before: " + l.getDigit(k));
				l.setDigit(k, 9-l.getDigit(k));
//				print("After: " + l.getDigit(k));
			}
			
			for(int k = 0; k < l.getSize(); k++)
			{
				int temp;
				if(k == 0)
					temp = num.get(k) + l.getDigit(k) +1;
				else
					temp = num.get(k) + l.getDigit(k);
				
//				print("Temp: " + temp);
				if(Integer.toString(temp).length() == 2)
				{
					String str = Integer.toString(temp);
//					print("Str: " + str);
					if(k + 2 > num.size())
					{
						num.add(new Integer(0));
					}
					int temp2 = convertInt(str, 0) + num.get(k + 1);
//					print("Temp2: " + temp2);
					num.set(k + 1, temp2);
//					print("Num k + 1: " + num.get(k + 1));
					num.set(k, convertInt(str, 1));	
//					print("Num k: " + num.get(k));
				}
				else
					num.set(k, temp);
			}
			num.remove(num.size() - 1);
			removeZeros(num);
		}	
	}
	public void mult(Large l)
	{
//		print("Digit at 0: " + l.getDigit(0));
		if(l.getDigit(0) == 0 && l.getSize() == 1)
		{
//			print("First if");
			num.clear();
			num.add(0);
		}
		else if(l.getDigit(0) == 1 && l.getSize() == 1)
		{
//			print("Second if");
		}
		else
		{
			Large total = new Large("0");
			Large toAdd = new Large("0");
			for(int a = 0; a < l.getSize(); a++)
			{
				for(int k = 0; k < num.size(); k++)
				{
					int temp = num.get(k) * l.getDigit(a);
//					print("Temp: " + temp);
					String str = Integer.toString(temp);
//					print("Str: " + str);
					Large t = new Large(str);
					if(k != 0)
						t.moveDown(k);
					toAdd.add(t);
//					t.getAmount("T = ");
//					toAdd.getAmount("To Add");
				}
				if(a != 0)
				{
					toAdd.moveDown(a);
//					print("Added " + a + " zero(s). ");
				}
				total.add(toAdd);
//				total.getAmount("Total");
				toAdd.empty();
			}
//			toAdd.getAmount("To Add = ");
			num.clear();
//			print("Num.size() = " + num.size());
//			for(int k = 0; k < total.getSize();k++)
//				System.out.println("K = " + k + " Total.get(k) = " + total.getDigit(k));
			for(int k = 0; k < total.getSize(); k++)
				num.add(total.getDigit(k));
		}
			
	}
	public void div(Large l)
	{
		if(num.size() == 1 && num.get(0) == 0)
		{
			num.clear();
			num.add(0);
		}
		else if(l.getSize() == 1 && l.getDigit(0) == 0)
		{
			try
			{
				throw new Exception("You can't divide by zero");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Large temp = new Large(num);
			long total = 0;
			while(isLarger(num,l));
			{
				total++;
				temp.sub(l);
			}
			num.clear();
			for(int k = 0; k < l.getSize(); k++)
				num.add(l.getDigit(k));
			
		}
	}
	private void moveDown(int l)
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
// adds zeros to temp as many as l dictates		
		for(int k = 0; k < l; k++)
			temp.add(0);
// adds num(one) to temp which is already holding the zeros		
		for(int k = 0; k < num.size(); k++)
			temp.add(num.get(k));
// clears num and adds temp to num
		num.clear();
		for(int k = 0; k < temp.size(); k++)
			num.add(temp.get(k));
	}
	private void removeZeros(ArrayList<Integer> n)
	{
		int count = num.size() - 1;
		while(num.get(count) == 0)
		{
			num.remove(count);
			count--;
		}
	}
	public void setAmount(Large l)
	{
		num.clear();
		for(int k = 0; k < l.getSize(); k++)
			num.add(l.getDigit(k));
	}
	public String getAmount()
	{
		String number = "";
		if(num.size() > 16)
		{
			for(int k = num.size() - 1; k > num.size() - 4;k--)
			{
				if(k == num.size() - 2)
					number = number + ".";
				number = number + num.get(k);
			}
			number = number + " E " + Integer.toString(num.size() - 3);
		}
		else
		{
			for(int k = num.size() - 1;k >= 0 ; k--)
			{
				number = number + (num.get(k).toString());
			}
		}
		return number;
	}
	public String getAmount(String prefix)
	{
		String number = prefix + " ";
		if(num.size() > 16)
		{
			for(int k = num.size() - 1; k > num.size() - 4;k--)
			{
				if(k == num.size() - 2)
					number = number + ".";
				number = number + num.get(k);
			}
			number = number + " E " + Integer.toString(num.size() - 3);
		}
		else
		{
			for(int k = num.size() - 1;k >= 0 ; k--)
			{
				number = number + (num.get(k).toString());
			}
		}
		return number;
	}
	private void addZero()
	{
		num.add(new Integer(0));
	}
	public int getSize()
	{
		return num.size();
	}
	int getDigit(int k)
	{
		return num.get(k);
	}
	private void setDigit(int spot, int k)
	{
		num.set(spot, k);
	}
	public static Boolean isLarger(Large one, Large two)
	{
		if(one.getSize() < two.getSize())
			return false;
		else if(one.getSize() > two.getSize())
			return true;
		else
		{
			for(int a = one.getSize() - 1; a >= 0; a--)
			{
//				System.out.println("A = " + a + " one.get(a): " + one.getDigit(a) +" convertInt(str,a): " + convertInt(str,a));
				if(one.getDigit(a) < two.getDigit(a))
					return false;
				else if(one.getDigit(a) > two.getDigit(a))
					return true;
				
			}
			return false;
		}
	}
	public static Boolean isLarger(Large one, int k)
	{
		String str = Integer.toString(k);
		if(str.length() > 1)
		{
			String temp = "";
			for(int a = str.length() - 1; a >= 0; a--)
			{
				temp = temp + str.charAt(a);
			}
			str = temp;
//			System.out.println("Temp: " + temp);
			
		}		
		if(one.getSize() < str.length())
			return false;
		else if(one.getSize() > str.length())
			return true;
		else
		{
			for(int a = one.getSize() - 1; a >= 0; a--)
			{
//				System.out.println("A = " + a + " one.get(a): " + one.getDigit(a) +" convertInt(str,a): " + convertInt(str,a));
				if(one.getDigit(a) < convertInt(str,a))
					return false;
				else if(one.getDigit(a) > convertInt(str,a))
					return true;
				
			}
			return false;
		}
	}
	public static Boolean isLarger(ArrayList<Integer> one, Large two)
	{
		if(one.size() < two.getSize())
			return false;
		else if(one.size() > two.getSize())
			return true;
		else
		{
			for(int a = one.size() - 1; a >= 0; a--)
			{
//				System.out.println("A = " + a + " one.get(a): " + one.getDigit(a) +" convertInt(str,a): " + convertInt(str,a));
				if(one.get(a) < two.getDigit(a))
					return false;
				else if(one.get(a) > two.getDigit(a))
					return true;
				
			}
			return false;
		}
	}
	public static int convertInt(String s, int i)
	{
		int k = Integer.parseInt(Character.toString(s.charAt(i)));
		return k;
	}
	public void print(Object o)
	{
		System.out.println(o);
	}
	private void empty()
	{
		num.clear();
		num.add(0);
	}
}
