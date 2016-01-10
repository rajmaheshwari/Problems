import java.util.*;
import java.io.*;

public class Main{
	public static int convert(int a){
		int b=0;
		while(a>0){
			b *= 10;
			b += a%10;
			a = a/10;
		}
		return b;
	}
	
    public static void main(String[] args) throws IOException{ 
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] s = new String[2];
		
		int t = Integer.parseInt(in.readLine());
		int a=0,b=0,c=0;
		
		while(t-->0){
			s = in.readLine().split(" ");
			a = Integer.parseInt(s[0]);
			b = Integer.parseInt(s[1]);
			a = convert(a);
			b = convert(b);
			c = a+b;
			c = convert(c);
			System.out.println(c);
		}
		
    }
}