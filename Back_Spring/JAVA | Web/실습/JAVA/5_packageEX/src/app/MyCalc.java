package app;

import lib.Caculator;

public class MyCalc extends Caculator{
	public int add(int a, int b){
		return a + b;
	}
	
	public int subtract(int a, int b){
		return a - b;
	}
	
	public double average(int a[]){
		int sum = 0;
		for(int i=0; i<a.length; i++)
			sum += a[i];
		
		return sum/a.length;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyCalc gc = new MyCalc();
		
		System.out.println(gc.add(1, 2));
		System.out.println(gc.subtract(2, 1));
		System.out.println(gc.average(new int[] {3, 5, 6}));
	}

}
