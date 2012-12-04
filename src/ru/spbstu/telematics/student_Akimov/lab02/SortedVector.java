import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class SortedVector<T extends Comparable<T>> implements ISortedVector<T>, Iterable<Comparable>{
	private Comparable[] array;
	private int elementCount;
	
	public SortedVector() {
		//this.array = (T[]) new Object [10];
		//this.array = (T[]) Array.newInstance(SortedVector.class, 10);
		//this.array = (T[]) Array.newInstance(class, 10); 
		this.array = new Comparable[10];
		this.elementCount = 0;
		System.out.println("CONSTRUCTOR!");
	}
	
	public T getElement(){
		return this.getElement();
	}

	@Override
	public void add(T o) {
		if(elementCount%10==0 && elementCount!=0){
			//System.out.println("RESIZE");
			resizeArray(1);
		}
		if(elementCount==0){
			array[elementCount]=o;
			elementCount++;
		}
		else if(elementCount==1){
			int compare = array[0].compareTo(o);
			if(compare==-1)
				array[elementCount] = o;
			else{
				array[elementCount] = array[elementCount-1];
				array[elementCount-1] = o;
			}
			elementCount++;
		}
		else{
			Comparable arrayTemp[] = new Comparable[array.length];
			//T[] arrayTemp = (T[]) new Object[array.length];
			if(array[0].compareTo(o)==1){ 	// меньше первого
				//System.out.println("Меньше первого");
				for(int i=0;i<elementCount;i++){
					arrayTemp[i+1]=array[i];
				}
				arrayTemp[0]=o;
				elementCount++;
				for(int i=0;i<elementCount;i++){
					array[i]=arrayTemp[i];
				}
			}
			else if(array[elementCount-1].compareTo(o)==-1){ // больше последнего
				//System.out.println("Больше последнего");
				array[elementCount]=o;
				elementCount++;
			}
			else{
				//System.out.println("Посередине");
				for(int i=0;i<elementCount;i++){
					int compare1 = array[i].compareTo(o);
					if(compare1 == -1)
						arrayTemp[i]=array[i];
					else
						arrayTemp[i+1]=array[i];
				}
				for(int i=0;i<elementCount;i++){
					if(arrayTemp[i]==null)
						arrayTemp[i] = o;
				}
				for(int i=0;i<array.length;i++)
					array[i]=arrayTemp[i];
				elementCount++;
			}
		}
	}
	
	private void resizeArray(int pm){
		int znak;
		if(pm==1)
			znak = 1;
		else 
			znak = -1;
		int length = array.length+(10*znak);
		Comparable arrayTemp[] = new Comparable[array.length+(10*znak)];
		//T[] arrayTemp = (T[]) new Object[array.length+(10*znak)];
		if(pm!=1){
			for(int i=0;i<length;i++)
				arrayTemp[i]=array[i];
		}
		else{
			for(int i=0;i<array.length;i++)
				arrayTemp[i]=array[i];
		}
		array = new Comparable[array.length+(10*znak)];
		//array = (T[]) new Object[array.length+(10*znak)];
		for(int i=0;i<length;i++)
			array[i]=arrayTemp[i];
	}
	

	@Override
	public void remove(int index) {
		if(index<elementCount && index>=0){
			Comparable arrayTemp[] = new Comparable[array.length];
			//T[] arrayTemp = (T[]) new Object[array.length];
			for(int i=0;i<elementCount-1;i++){
				if(i<index)
					arrayTemp[i]=array[i];
				else
					arrayTemp[i]=array[i+1];
			}
			array[elementCount-1]=null;
			elementCount--;
			if(array.length-elementCount>10)
				resizeArray(0);
			for(int i=0;i<elementCount;i++)
				array[i]=arrayTemp[i];
		}
		else{
			System.out.println("Bad index!");
		}
	}

	@Override
	public T get(int index) {
		if(index<=array.length){
			return (T) array[index];
		}
		return null;
	}

	@Override
	public int indexOf(T o) {
		for(int i=0;i<elementCount;i++){
			if(array[i].compareTo(o)==0)
				return i;
		}
		return -1;
	}
	
	public void printVector(){
		for(int i=0;i<array.length;i++){
			if(array[i]!=null){
				System.out.println(i + ") " + array[i]);
			}
		}
	}

	@Override
	public Iterator <Comparable> iterator() {
		return new MyIterator();
	}
	
	class MyIterator implements Iterator <Comparable>{
		private int iterPos = 0;
		@Override
		public boolean hasNext() {
			if(iterPos<SortedVector.this.elementCount)
				return true;
			else
				return false;
		}

		@Override
		public Comparable next() {
			if(array[iterPos]!=null){
				Comparable nextVal = array[iterPos];
				iterPos++;
				return nextVal;
			}
			else
				iterPos++;
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
	}
}
