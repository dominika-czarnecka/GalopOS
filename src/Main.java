import interpreter.Interpreter;

public class Main {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Interpreter());
		t1.start();

	}

}
