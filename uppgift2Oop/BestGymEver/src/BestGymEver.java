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
		System.out.println("-------------------" + "\nNågot gick fel, försök igen!");
	}

	public String concatenateTwoStrings(String one, String two) {
		System.out.println(one + "\n" + two);
		return null;
	}

	public void printToFile(String dateOfBirth, String name, LocalDate todaysDate, File file) {
		try (PrintWriter printToFile = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));) {
			printToFile.println(dateOfBirth.trim() + ", " + name.trim() + "." + "\n Tränade senast: " + todaysDate);
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
		int choice = 0; // används i switch-menyn

		System.out.println("1: Se fil-listan på gamla kunder. \n2: Kolla om kunden finns med. \n3: Lägg till ny kund");
		Scanner sc = new Scanner(System.in);

		while (bool) { // Meny val
			inputFromUser = sc.nextLine();
			try {
				choice = Integer.parseInt(inputFromUser); // gör om input till en int
				bool = false; // Avslutar while-loopen
			} catch (NumberFormatException e) {
				felMeddelande();
				bool = false;
			}
		}

		switch (choice) {

		case 1: // Skriver ut listan på de gamla kunderna
			System.out.println("Listan på gamla kunder: \n--------------------");
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
			System.out.println("Namn eller personnummer på kund ");
			inputFromUser = sc.nextLine();

			sc = new Scanner(new FileReader(oldCustomers));

			while (sc.hasNext()) {
				firstLine = sc.nextLine();
				dateOfBirthAndName = firstLine.split(", ");
				if (sc.hasNext()) {
					secondLine = sc.nextLine();
				}
				// Letar efter det användaren angett i en metod som använder equals i en array.
				if (comparingArrayFromInput(dateOfBirthAndName, inputFromUser)) {
					// formaterar datum
					LocalDate formatedDate = LocalDate.parse(secondLine, formatter);
					System.out.println(dateOfBirthAndName[1] + " är medlem.\n" + "Blev medlem: " + formatedDate);
					// om datumet är efter "för ett år sen"
					if (isDateAYearFromNow(formatedDate)) {
						System.out.println(dateOfBirthAndName[1] + " har betalat.\nLäggs nu till i har-tränat-listan.");
						printToFile(dateOfBirthAndName[0], dateOfBirthAndName[1], todaysDate, hasTrained);
						sc.close();
						System.exit(0);
					} else { // Annars är man en föredetta kund aka över ett år sen man betalade.
						System.out.printf("%s är en föredetta kund. \nBetalade senast: %s", dateOfBirthAndName[1],
								formatedDate);
						System.exit(0);
					}
				}
			}
			// Har inte hittat något som matchar det som finns i filen.
			System.out.println("Kunde ej hitta " + inputFromUser + "\nFanns ej med i filen.");
			break;

		case 3: // Lägger till ny kund, som sen läggs till i text-filen hasTrained
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