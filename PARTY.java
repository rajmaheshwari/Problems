import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{	
	static int[] calculate(int[] weight, int[] f,int W){
		int n = f.length,a,b;
		int[][] v = new int[n+1][W+1];
		int[][] cost = new int[n+1][W+1];
		
		for(int i=1;i<n;i++){
			for(int w=1;w<=W;w++){
				if(weight[i]<=w){
					if(v[i-1][w] > v[i-1][w-weight[i]]+f[i] ){
						v[i][w] = v[i-1][w];
						cost[i][w] = cost[i-1][w];
					}
					else if(v[i-1][w] == v[i-1][w-weight[i]]+f[i] ){
						a = cost[i-1][w];
						b = cost[i-1][w-weight[i]]+weight[i];
						if(a<b){
							cost[i][w] = a;
						}
						else{
							cost[i][w] = b;
						}
						v[i][w] = v[i-1][w-weight[i]]+f[i];
					}
					else{
						v[i][w] = v[i-1][w-weight[i]]+f[i];
						if(f[i]==0){
							cost[i][w] = cost[i-1][w-weight[i]]; 
						}
						else{
							cost[i][w] = cost[i-1][w-weight[i]] + weight[i];
						}
					}

				}
				else{
					v[i][w] = v[i-1][w];
					cost[i][w] = cost[i-1][w];
				}
			}
		}
		
		int[] x = new int[2];
		x[0] = v[n-1][W];
		x[1] = cost[n-1][W];
		return x;
	}

    public static void main(String[] args) throws IOException{ 
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	String[] s = new String[2];
    	int W,n;
    	
    	while(true){
    		s = in.readLine().split(" ");    		
    		W = Integer.parseInt(s[0]);
    		n = Integer.parseInt(s[1]);
    		
    		if(W==0 && n==0){
    			break;
    		}
    		
    		int[] w = new int[n+1];
    		int[] f = new int[n+1];
    		int[] v =  new int[2];
    		
    		for(int i=1;i<n+1;i++){
    			s = in.readLine().split(" ");
        		w[i] = Integer.parseInt(s[0]);
        		f[i] = Integer.parseInt(s[1]);
    		}
    		v = calculate(w,f,W);
    		System.out.println(v[1]+" "+v[0]);
    		in.readLine();
    	}
    	
    	
    }
}