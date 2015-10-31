import java.util.*;
import java.lang.*;
import java.io.*;

class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int x;
		
		while(true){
			x = Integer.parseInt(in.readLine());
			if(x==42)
				break;
			else{
				System.out.println(x);
			}
		}
	}
}