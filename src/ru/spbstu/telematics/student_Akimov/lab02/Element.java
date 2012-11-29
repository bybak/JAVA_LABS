public class Element implements Comparable<Element> {
	private int element;
	
	public Element(int element) {
		super();
		this.element = element;
	}

	public void setElement(int element) {
		this.element = element;
	}
	
	public void show(Element test){
		System.out.println("ELEMENT: " + test.element);
	}

	@Override
	public int compareTo(Element o) {
		if(this.element<0)
			return 1;
		return 0;
	}
	
}
