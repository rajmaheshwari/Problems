import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main{
	static boolean find(int n, ArrayList a){
		Stack s = new Stack();
		int cur=0,p=0,q=0;
		
		while(cur<n || !s.empty()){			
			if(!s.empty()){p = (int)s.peek();}
			else{p=0;}
			
			if(a.size() != 0){q = (int)a.get(0);}
			else{q=0;}
			
			if(q==(cur+1)){
				a.remove(0);
				cur++;
			}
			else if(p==(cur+1)){
				s.pop();
				cur++;
			}
			else{   				
				while(a.size()>0 && (int)a.get(0) != (cur+1)){
					s.push((int)a.get(0));
					a.remove(0);
				}
				if(a.size()>0){
					if((int)a.get(0)==(cur+1)){
    					a.remove(0);
    					cur++;
    				}
				}
				else{
					return false;
				}
			}
		}
		return true;
	}
		
    public static void main(String[] args) throws IOException{ 
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	int n;
    	
    	while(true){
    		n = Integer.parseInt(in.readLine());
    		if(n==0){
    			break;
    		}
    		String[] str = new String[n];
    		ArrayList a = new ArrayList();
    		
    		str = in.readLine().split(" ");
    		for(int i=0;i<n;i++){
    			a.add(Integer.parseInt(str[i]));
    		}
    		
    		boolean x = find(n,a);
    		if(x)
    			System.out.println("yes");
    		else
    			System.out.println("no");
    	}
    }
}