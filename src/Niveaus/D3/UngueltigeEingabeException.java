/**
 * Eigene Exception für Eingaben, die im Quiz nicht erlaubt sind.
 */
public class UngueltigeEingabeException extends Exception {
    public UngueltigeEingabeException (String meldung) {
        super(meldung);
    }
}
