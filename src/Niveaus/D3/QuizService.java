/**
 * Enthält die Geschäftslogik des Länder-Quiz.
 */
public class QuizService {
    private final String[] fragen = {"Was ist die Hauptstadt von Frankreich?", "In welchem Land liegt die Stadt Tokio?", "Was ist die Hauptstadt der Schweiz?"};

    private final String[][] antworten = {{"Madrid", "Paris", "Rom", "Bern"}, {"China", "Südkorea", "Japan", "Thailand"}, {"Zürich", "Genf", "Bern", "Basel"}};

    // Die Zahlen beginnen bei 1, weil der Benutzer Antworten von 1 bis 4 eingibt.
    private final int[] richtigeAntworten = {2, 3, 3};

    private int aktuelleFrage;
    private int punkte;

    public void starteNeu() {
        aktuelleFrage = 0;
        punkte = 0;
    }

    public boolean hatNaechsteFrage() {
        return aktuelleFrage < fragen.length;
    }

    public String getAktuelleFrage() {
        if (!hatNaechsteFrage()) {
            throw new IllegalStateException("Es gibt keine weitere Frage.");
        }
        return fragen[aktuelleFrage];
    }

    public String[] getAntworten() {
        return antworten[aktuelleFrage];
    }

    public boolean pruefeAntwort(int antwortNummer) {
        if (antwortNummer < 1 || antwortNummer > antworten[aktuelleFrage].length) {
            throw new IllegalArgumentException("Antwortnummer liegt ausserhalb des gültigen Bereichs.");
        }

        boolean richtig = antwortNummer == richtigeAntworten[aktuelleFrage];
        if (richtig) {
            punkte++;
        }
        aktuelleFrage++;
        return richtig;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getAnzahlFragen() {
        return fragen.length;
    }
}
