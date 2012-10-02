public class Main {
    public static void main(String[] args) {
		long number = 0;
		if (args.length > 0) {
			number=Long.parseLong(args[0]);
			System.out.println("-------------------------");
			System.out.println("Входное значение: " + number);
			System.out.println("Число Фибоначчи: " + fib(number));
			System.out.print("-------------------------");
		}
		else {
			System.out.println("Отсутствует параметр");
		}
    }
	public static long fib(long n){
		if (n <= 2) 
			return 1;
		else{
			return fib(n - 1) + fib(n - 2);
		}
	}
}