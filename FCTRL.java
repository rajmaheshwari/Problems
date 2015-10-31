import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main{
	
	public static void main(String[] args) throws IOException{
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(in.readLine());
	    int sum=0,p,a,s;
	    
	    while(t-->0){
	    	int n = Integer.parseInt(in.readLine());
	    	p = 5;
	    	sum = 0;
	    	a = 1;
	    	
	    	while(a>=1){
	    		a = n/p;
	    		p *= 5;
	    		sum += a;
	    	}
	    	System.out.println(sum);
	    }
	}
}