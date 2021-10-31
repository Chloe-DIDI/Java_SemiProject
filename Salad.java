import java.util.Vector;
import java.io.IOException;

public class Salad
{
	public static void main(String[] args) throws IOException
	{
		ISetup is = new ISetup();

		Change cg = new Change();

		Password pw = new Password();

		System.out.println();
		System.out.println();
		System.out.println("\t\t »Ï·Õ~ Power On ");
		System.out.println();
		
		pw.inputPass();

		pw.modePrint();
		pw.modeSelect();
		pw.modeRun();
	}
} 
