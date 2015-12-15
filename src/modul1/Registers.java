package modul1;

public class Registers {
	
	public static Boolean Z, S;
	public int A,
	B, 
	C, 
	D,
	IP;
	
	public Registers()
	{
		ClearRegisters();
	}
	
	private void ClearRegisters()
	{
		A =
		B = 
		C = 
		D = 0;
		IP = 0;
		Z=
		S=false;		
	}
	
	public String toString() {
		return String.valueOf(A) + " " + String.valueOf(B) + " " + String.valueOf(C) + " " + String.valueOf(IP);
	}
}
