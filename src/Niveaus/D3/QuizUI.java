import java.util.Scanner;

/**
 * Enthält nur Ein- und Ausgabe; keine Quiz-Logik.
 */
public class QuizUI {
    private final QuizService service;
    private final Scanner scanner;

    public QuizUI() {
        this.service = new QuizService();
        this.scanner = new Scanner(System.in);
    }

    public void starten() {
        System.out.println("=== Länder-Quiz ===");
        System.out.println("Wähle bei jeder Frage die Nummer der richtigen Antwort.");

        do {
            spieleEineRunde();
        } while (nochEinmalSpielen());

        System.out.println("Danke fürs Spielen. Auf Wiedersehen!");
    }

    private void spieleEineRunde() {
        service.starteNeu();
        while (service.hatNaechsteFrage()) {
            zeigeFrage();
            int antwort = liesGueltigeAntwort();

            // Delegation: Die UI prüft nicht selbst, sondern ruft die Logik auf.
            boolean richtig = service.pruefeAntwort(antwort);
            System.out.println(richtig ? "Richtig!" : "Leider falsch.");
            System.out.println();
        }

        System.out.printf("Quiz beendet: %d von %d Punkten.%n", service.getPunkte(), service.getAnzahlFragen());
    }

    private boolean nochEinmalSpielen() {
        while (true) {
            System.out.print("Möchtest du nochmals spielen? (j/n): ");
            String antwort = scanner.nextLine().trim().toLowerCase();

            if (antwort.equals("j") || antwort.equals("ja")) {
                return true;
            }
            if (antwort.equals("n") || antwort.equals("nein")) {
                return false;
            }
            System.out.println("Bitte j für Ja oder n für Nein eingeben.");
        }
    }

    private void zeigeFrage() {
        System.out.println(service.getAktuelleFrage());
        String[] antworten = service.getAntworten();
        for (int i = 0; i < antworten.length; i++) {
            System.out.printf("%d. %s%n", i + 1, antworten[i]);
        }
    }

    private int liesGueltigeAntwort() {
        while (true) {
            try {
                System.out.print("Deine Antwort: ");
                String eingabe = scanner.nextLine().trim();
                return validiereAntwort(eingabe, service.getAntworten().length);
            } catch (UngueltigeEingabeException exception) {
                System.out.println("Ungültige Eingabe: " + exception.getMessage());
            }
        }
    }

    /**
     * Wirft die eigene Exception, wenn die Benutzereingabe nicht gültig ist.
     */
    private int validiereAntwort(String eingabe, int anzahlAntworten) throws UngueltigeEingabeException {
        if (eingabe.isBlank()) {
            throw new UngueltigeEingabeException("Die Eingabe darf nicht leer sein.");
        }

        try {
            int antwortNummer = Integer.parseInt(eingabe);
            if (antwortNummer < 1 || antwortNummer > anzahlAntworten) {
                throw new UngueltigeEingabeException("Bitte eine Zahl von 1 bis " + anzahlAntworten + " eingeben.");
            }
            return antwortNummer;
        } catch (NumberFormatException exception) {
            throw new UngueltigeEingabeException("Bitte eine ganze Zahl eingeben.");
        }
    }
}
