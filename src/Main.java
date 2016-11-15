
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		TextProcessor tp=new TextProcessor();
		tp.processText();	
		tp.printStatistics();
	}

}