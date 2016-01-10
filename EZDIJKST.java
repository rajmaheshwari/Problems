import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int maxN;        // maximum number of elements on PQ
    private int N;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;      // keys[i] = priority of i

    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        keys = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        N++;
        qp[i] = N;
        pq[N] = i;
        keys[i] = key;
        swim(N);
    }

    public int delMin() { 
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];        
        exch(1, N--); 
        sink(1);
        qp[min] = -1;            // delete
        keys[pq[N+1]] = null;    // to help with garbage collection
        pq[N+1] = -1;            // not needed
        return min; 
    }

    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i] = key;
        swim(qp[i]);
    }

    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, N--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k)  {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}


class DirectedEdge{
	private final int v,w;
	private final int weight;
	
	public DirectedEdge(int v, int w, int weight){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public int from(){
		return this.v;
	}
	
	public int to(){ 
		return this.w;
	}
	
	public int weight(){
		return this.weight;
	}
}


class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;   
    private int N;              

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        first = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}


class EdgeWeightedDiGraph{
	public final int V;
	public final Bag<DirectedEdge>[] adj;
	
	public EdgeWeightedDiGraph(int V){
		this.V = V;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for(int i=0;i<V;i++){
			adj[i] = new Bag<DirectedEdge>();
		}
	}
	
	public void addEdge(DirectedEdge e){
		adj[e.from()].add(e);
	}
	
	public Iterable<DirectedEdge> adj(int v){
		return adj[v];
	}
}


public class djkstra {
	private DirectedEdge[] edgeTo;
	private int[] distTo;
	private IndexMinPQ<Integer> pq;
	
	public djkstra(EdgeWeightedDiGraph G, int s){
		edgeTo = new DirectedEdge[G.V];
		distTo = new int[G.V];

		pq = (IndexMinPQ<Integer>) new IndexMinPQ<Integer>(G.V);
		
		for(int i=0;i<G.V;i++){
			distTo[i] = Integer.MAX_VALUE;
		}
		distTo[s] = 0;
		
		((IndexMinPQ<Integer>) pq).insert(s,0);
		while(!pq.isEmpty()){

			int v = ((IndexMinPQ<Integer>) pq).delMin();
			for(DirectedEdge e: G.adj(v)){
				relax(e);
			}
		}
	}
	
	private void relax(DirectedEdge e){
		int v = e.from();
		int w = e.to();
		if(distTo[w] > distTo[v] + e.weight()){
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if(pq.contains(w)){

				((IndexMinPQ<Integer>) pq).decreaseKey(w,distTo[w]);
			}
			else{
				((IndexMinPQ<Integer>) pq).insert(w,distTo[w]);
			}
		}
	}	

	
	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] s = new String[2];
		String[] str = new String[3];
		
		int t = Integer.parseInt(in.readLine());
		int V, E;
		int v,w,weight;
		
		while(t-->0){
			s = in.readLine().split(" ");
			V = Integer.parseInt(s[0]);
			E = Integer.parseInt(s[1]);
			
			EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(V);
			for(int i=0;i<E;i++){
				str = in.readLine().split(" ");
				v = Integer.parseInt(str[0])-1;
				w = Integer.parseInt(str[1])-1;
				weight = Integer.parseInt(str[2]);
				DirectedEdge e = new DirectedEdge(v,w,weight);
				G.addEdge(e);
			}
			
			s = in.readLine().split(" ");
			v = Integer.parseInt(s[0])-1;
			w = Integer.parseInt(s[1])-1;
			djkstra x = new djkstra(G,v);
			if(x.distTo[w]!=Integer.MAX_VALUE){
				System.out.println(x.distTo[w]);
			}
			else{
				System.out.println("NO");
			}
		}
		
	}

}


