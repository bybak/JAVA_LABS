public class Main {
	public static void main(String[] args) {
		Element test = new Element(7);
		SortedVector<Integer> vector = new SortedVector<Integer>();
		vector.add(50);
		vector.add(30);
		vector.add(10);
		vector.add(150);
		vector.add(130);
		vector.add(15);
		vector.add(0);
		vector.add(1);
		vector.add(89);
		vector.add(54);
		vector.add(67);
		vector.add(7);
		vector.add(7);

		vector.printVector();
		
		System.out.println("-----Get(Int)----");
		System.out.println(vector.get(10));
		System.out.println("-----IndexOf(Comparable)----");
		System.out.println(vector.indexOf(133));
		System.out.println("----Remove(Int)-----");
		vector.remove(2);
		vector.remove(2);
		vector.remove(2);
		vector.remove(2);
		vector.printVector();
		
		for (Comparable test1 : vector) {
			System.out.println("Element: " + test1);
		}
		System.out.println("");
	}
}
