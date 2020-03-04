import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

interface AlgorytmSzyfrujacy{
	String execute(String tekst, int przesuniecie);
}

class EncryptUnicode implements AlgorytmSzyfrujacy{

	StringBuilder sb = new StringBuilder();
	@Override
	public String execute(String tekst, int przesuniecie) {
		for (Character symbol : tekst.toCharArray()) {
            sb.append((char) (symbol + przesuniecie));
        }
        return sb.toString();
	}
	
}

class DecryptUnicode implements AlgorytmSzyfrujacy{

	@Override
	public String execute(String tekst, int przesuniecie) {
		StringBuilder sb = new StringBuilder();
        for (Character symbol : tekst.toCharArray()) {
            sb.append((char) (symbol - przesuniecie));
        }
        return sb.toString();
	}
	
}

class EncryptShift implements AlgorytmSzyfrujacy{

	@Override
	public String execute(String tekst, int przesuniecie) {
		StringBuilder sb = new StringBuilder();
        char startLetter;
 
        for (Character symbol : tekst.toCharArray()) {
            if (Character.isAlphabetic(symbol)) {
                startLetter = (Character.isUpperCase(symbol)) ? 'A' : 'a';
                sb.append((char) (startLetter + (symbol - startLetter + przesuniecie) % 26));
            } else {
                sb.append((char) symbol);
            }
        }
        return sb.toString();
	}
	
}

class DecryptShift implements AlgorytmSzyfrujacy{

	@Override
	public String execute(String tekst, int przesuniecie) {
		StringBuilder sb = new StringBuilder();
        char endLetter;
 
        for (Character symbol : tekst.toCharArray()) {
            if (Character.isAlphabetic(symbol)) {
                endLetter = (Character.isUpperCase(symbol)) ? 'Z' : 'z';
                sb.append((char) (endLetter - (endLetter - symbol + przesuniecie) % 26));
            } else {
                sb.append((char) symbol);
            }
        }
        return sb.toString();
	}
	
}

class Szyfr{
	public int przesuniecie = 0;
	public String tryb = "enc";
	public String tekst = "";
	public String wejscie = "";
	public String wyjscie = "";
	public String algorytm = "shift";
	public AlgorytmSzyfrujacy algorytmszyfrujacy;
	public int[] ascii;
	public char[] wynik;
	
	public Szyfr(String[] args) {
		przypisz(args);
		if((tekst == null ) && wejscie != null) {
			Load();
		}
		wybierzAlgorytm();
	}
	
	public void Load(){
        File file = new File(wejscie);
        try (Scanner scanner = new Scanner(file)) {
            tekst = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
	
	public void Save(String wynik) {
		File file = new File(wyjscie);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(wynik); // prints a formatted string
        } catch (FileNotFoundException e) {
            System.out.printf("File not found");
        }
	}
	
	public void przypisz(String[] args) {
		for (int i = 0; i < args.length ; i++) {
            switch(args[i]) {
                case "-key":
                    przesuniecie = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    tekst = args[i+1];
                    break;
                case "-mode":
                    tryb = args[i+1];
                    break;
                case "-in":
                    wejscie = args[i+1];
                    break;
                case "-out":
                    wyjscie = args[i+1];
                    break;
                case "-alg":
                	algorytm = args[i+1];
                	break;
            }
        }
	}
	
	public void wybierzAlgorytm() {
		switch(algorytm) {
        case "unicode":
        	switch(tryb) {
            case "enc":
            	algorytmszyfrujacy = new EncryptUnicode();
                break;
            case "dec":
            	algorytmszyfrujacy = new DecryptUnicode();
                break;
        }
        	break;
        case "shift":
        	switch(tryb) {
            case "enc":
            	algorytmszyfrujacy = new EncryptShift();
                break;
            case "dec":
            	algorytmszyfrujacy = new DecryptShift();
                break;
        }
        break;	
        }
	}
	
	
	public void szyfruj() {
		String wynik = algorytmszyfrujacy.execute(tekst, przesuniecie);
		if(wyjscie == null) {
			System.out.println(wynik);
		}
		else {
			Save(wynik);
		}
	}
	
	
	
}


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Szyfr szyfr = new Szyfr(args);
		szyfr.szyfruj();
	}

}
