import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BestGymEver {

	public boolean comparingArrayFromInput(String[] arrayName, String inputFromUser) {
		boolean check = false;
		for (int i = 0; i < arrayName.length; i++) {
			if (arrayName[0].equalsIgnoreCase(inputFromUser) 
					|| arrayName[1].equalsIgnoreCase(inputFromUser)) {
				check = true;
			} else {
				check = false;
			}
		}
		return check;
	}

	public void felMeddelande() {
		System.out.println("-------------------" + "\nN�got gick fel, f�rs�k igen!");
	}

	public String concatenateTwoStrings(String one, String two) {
		System.out.println(one + "\n" + two);
		return null;
	}

	public void printToFile(String dateOfBirth, String name, LocalDate todaysDate, File file) {
		try (PrintWriter printToFile = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));) {
			printToFile.println(dateOfBirth.trim() + ", " + name.trim() + "." + "\n Tr�nade senast: " + todaysDate);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException fel!");
			e.printStackTrace();
		} catch (IOException e1) {
			System.out.println("IOException fel!");
			e1.printStackTrace();
		}
	}

	public boolean isDateAYearFromNow(LocalDate formatedDate) {
		boolean checkDate = true;
		LocalDate todaysDate = LocalDate.now();
		LocalDate aYearFromNow = todaysDate.minusYears(1);
		if (formatedDate.isAfter(aYearFromNow)) {
			checkDate = true;
		} else {
			checkDate = false;
		}
		return checkDate;
	}

	BestGymEver() throws IOException {

		String[] dateOfBirthAndName;
		boolean bool = true;
		String inputFromUser;
		String firstLine = "";
		String secondLine = "";
		File oldCustomers = new File("customers.txt");
		File hasTrained = new File("hasTrained.txt");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate todaysDate = LocalDate.now();
		// LocalDate aYearFromNow = todaysDate.minusYears(1);
		int choice = 0; // anv�nds i switch-menyn

		System.out.println("1: Se fil-listan p� gamla kunder. \n2: Kolla om kunden finns med. \n3: L�gg till ny kund");
		Scanner sc = new Scanner(System.in);

		while (bool) { // Meny val
			inputFromUser = sc.nextLine();
			try {
				choice = Integer.parseInt(inputFromUser); // g�r om input till en int
				bool = false; // Avslutar while-loopen
			} catch (NumberFormatException e) {
				felMeddelande();
				bool = false;
			}
		}

		switch (choice) {

		case 1: // Skriver ut listan p� de gamla kunderna
			System.out.println("Listan p� gamla kunder: \n--------------------");
			sc = new Scanner(new FileReader(oldCustomers));
			while (sc.hasNext()) {
				firstLine = sc.nextLine();
				dateOfBirthAndName = firstLine.split(", ");
				if (sc.hasNext()) {
					secondLine = sc.nextLine();
					concatenateTwoStrings(firstLine, secondLine); // Skriver ut gamla-fil-listan
				}
			}
			sc.close();
			break;

		case 2: // Letar i gamla filen efter kund.
			System.out.println("Namn eller personnummer p� kund ");
			inputFromUser = sc.nextLine();

			sc = new Scanner(new FileReader(oldCustomers));

			while (sc.hasNext()) {
				firstLine = sc.nextLine();
				dateOfBirthAndName = firstLine.split(", ");
				if (sc.hasNext()) {
					secondLine = sc.nextLine();
				}
				// Letar efter det anv�ndaren angett i en metod som anv�nder equals i en array.
				if (comparingArrayFromInput(dateOfBirthAndName, inputFromUser)) {
					// formaterar datum
					LocalDate formatedDate = LocalDate.parse(secondLine, formatter);
					System.out.println(dateOfBirthAndName[1] + " �r medlem.\n" + "Blev medlem: " + formatedDate);
					// om datumet �r efter "f�r ett �r sen"
					if (isDateAYearFromNow(formatedDate)) {
						System.out.println(dateOfBirthAndName[1] + " har betalat.\nL�ggs nu till i har-tr�nat-listan.");
						printToFile(dateOfBirthAndName[0], dateOfBirthAndName[1], todaysDate, hasTrained);
						sc.close();
						System.exit(0);
					} else { // Annars �r man en f�redetta kund aka �ver ett �r sen man betalade.
						System.out.printf("%s �r en f�redetta kund. \nBetalade senast: %s", dateOfBirthAndName[1],
								formatedDate);
						System.exit(0);
					}
				}
			}
			// Har inte hittat n�got som matchar det som finns i filen.
			System.out.println("Kunde ej hitta " + inputFromUser + "\nFanns ej med i filen.");
			break;

		case 3: // L�gger till ny kund, som sen l�ggs till i text-filen hasTrained
			System.out.println("Ange Namn och personnummer: ");
			String name;
			String dateOfBirth;

			name = sc.nextLine();
			dateOfBirth = sc.nextLine();
			
			printToFile(dateOfBirth, name, todaysDate, hasTrained);
			break;
		default:
			System.out.println("Avslutar programmet");
			break;
		}
	}

	public static void main(String[] args) throws IOException {

		BestGymEver x = new BestGymEver();
	}
}