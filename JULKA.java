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
	    
	public static void main(String[] args) throws IOException{ 
    	Reader.init(System.in);
    try{
		int t = 10;
		String sum, dif;
		while(t-->0){
			sum = Reader.next();
			dif = Reader.next();
			BigInteger a = new BigInteger(sum);
			BigInteger b = new BigInteger(dif);
			BigInteger b_ = new BigInteger("-"+dif);
			BigInteger c = new BigInteger("2");
			
			System.out.println( a.add(b).divide(c) );
			System.out.println( a.add(b_).divide(c) );
		}
		
	}
	catch(Exception e){return;}
    
    }
}