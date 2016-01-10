import java.io.*;
import java.math.BigInteger;
import java.util.*;

class Reader{
    private static BufferedReader reader;
    private static StringTokenizer tokenizer;

    static void init(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }

    static String next() throws IOException{
        while (!tokenizer.hasMoreTokens()){
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException{
        return Integer.parseInt(next());
    }
}

public class Main{
	public static int find(int A, int B, int C){
		int b=0,a=A;
		int step=1;
		int amt;
		
		while(a!=C && b!=C){
			amt = min(a,B-b);
			b += amt;
			a -= amt;
			step++;
			
			if(a==C || b==C){
				break;
			}
			if(a==0){
				a = A;
				step++;
			}
			if(b==B){
				b = 0;
				step++;
			}
		}
		return step;
	}
	
	public static int min(int a, int b){
		if(a>b){
			return b;
		}
		else{
			return a;
		}
	}
	
	public static int gcd(int a,int b){
	    if(b==0)
	        return a;
	    return gcd(b,a%b);
	}    
	
	public static void main(String[] args) throws IOException{ 
    	Reader.init(System.in);
    try{
		int t = Reader.nextInt();
		while(t-->0){
			int a = Reader.nextInt();
			int b = Reader.nextInt();
			int c = Reader.nextInt();
			
			if(c>a && c>b){
				System.out.println("-1");
			}
			else if( c % gcd(a,b) != 0){
				System.out.println("-1");
			}
			else if(c==a || c==b){
				System.out.println("1");
			}
			else{
				System.out.println( min( find(a,b,c),find(b,a,c)) );
			}
		}
		
	}
	catch(Exception e){return;}
    
    }
}