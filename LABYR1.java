import java.io.*;
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

class pair{
	public int x;
	public int y;
	
	public pair(int a, int b){
		x = a;
		y = b;
	}
	
	public pair(){
		x = 0;
		y = 0;
	}
	
	public pair(pair random){
		x = random.x;
		y = random.y;
	}
}


public class Main{
	static pair end;
	static char[][] grid;
	static int max;

	
	public static void find(int x, int y, int R, int C){		
		pair s = new pair(x,y);	
		end = new pair();
		pair current = new pair();

		int[] r = {0,0,-1,1};
		int[] c = {1,-1,0,0};
		int[][] dist = new int[R+1][C+1];
		int[][] marked = new int[R+1][C+1];
		max = 0;		
		marked[x][y] = 1;
		
		Queue<pair> q = new LinkedList<pair>();
		
		q.add(s);
		
		while(!q.isEmpty()){
			current = q.poll();
			pair next = new pair();
			
			for(int i=0;i<4;i++){
				next.x = current.x + r[i];
				next.y = current.y + c[i];
				
				if( (next.x>0 && next.x<=R) && (next.y>0 && next.y<=C) ){
					if(grid[next.x][next.y]=='.' && (marked[next.x][next.y]!=1)){						
						marked[next.x][next.y] = 1;
						dist[next.x][next.y] = dist[current.x][current.y] + 1;
						
						if(dist[next.x][next.y] > max){
							max = dist[next.x][next.y];
							end = next;
						}
						
						pair random = new pair(next);
						q.add(random);
					}
				}
				
			}
			
		}
		
		end = new pair(current);

	}
	
    
	public static void main(String[] args) throws IOException{ 
    	Reader.init(System.in);
    try{
		int t = Reader.nextInt();
		
		while(t-->0){
			int C = Reader.nextInt();
			int R = Reader.nextInt();
			grid = new char[R+1][C+1];
			
			String str;
			for(int i=1;i<=R;i++){
				str = Reader.next();
				for(int j=1;j<=C;j++){
					grid[i][j] = str.charAt(j-1);
				}
			}
			
			int flag=0;
			int x=0,y=0;
			for(int i=1;i<=R;i++){
				for(int j=1;j<=C;j++){
					if(grid[i][j]=='.'){
						flag=1;
						x=i;y=j;
						break;
					}
				}
				if(flag==1){break;}
			}
						
			find(x,y,R,C);
			find(end.x,end.y,R,C);
			System.out.println("Maximum rope length is "+max+".");
		}
		
	}
	catch(Exception e){return;}
    
    }
}