package de.hdm.projectplan.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
import de.hdm.projectplan.server.db.*;


/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 */
public class DBConnection {

	 /**
     * Instantiieren der Connection und Festlegung der Einzigartigkeit durch <code>static</code>
     */
	
    private static Connection con = null;

    /**
     * Die URL fuer die Google Cloud-SQL Datenbank - angesprochen uebern die Projekt-, sowie Instanz-ID.
     * In deploytem Status soll die Datenbank von der Application nur ueber einen root-Zugang und den entsprechenden Google-Treibern angesprochen werden.
     */
  //  private static String googleUrl = "jdbc:google:mysql://prof-thies.de:thies-bankproject:thies-bankproject/bankproject?user=demo&password=demo";
    
    /**
     *   Die URL fuer die Google Cloud-SQL Datenbank zum Ansteuern der Datenbank von einer
     *   lokalen Entwicklungsumgebung (nicht <code>deployed</code>). Hier muss die Datenbank ueber die
     *   bereitgestellte IPv4-Adresse sowie einen eingerichteten Zugang angesprochen werden.
     */
    
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/projektplansystem?user=root&password=root";

    /**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>DBConnection.connection()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine
     * einzige Instanz von <code>DBConnection</code> existiert.
     * <p>
     * 
     * <b>Fazit:</b> DBConnection sollte nicht mittels <code>new</code>
     * instantiiert werden, sondern stets durch Aufruf dieser statischen
     * Methode.
     * <p>
     * 
     * <b>Nachteil:</b> Bei Zusammenbruch der Verbindung zur Datenbank - dies
     * kann z.B. durch ein unbeabsichtigtes Herunterfahren der Datenbank
     * ausgel�st werden - wird keine neue Verbindung aufgebaut, so dass die in
     * einem solchen Fall die gesamte Software neu zu starten ist. In einer
     * robusten L�sung w�rde man hier die Klasse dahingehend modifizieren, dass
     * bei einer nicht mehr funktionsf�higen Verbindung stets versucht w�rde,
     * eine neue Verbindung aufzubauen. Dies w�rde allerdings ebenfalls den
     * Rahmen dieses Projekts sprengen.
     * 
     * @return DAS <code>DBConncetion</code>-Objekt.
     * @see con
     */
    public static Connection connection() {
        // Wenn es bisher keine Conncetion zur DB gab, ...
        if (con == null) {
            String url = null;
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                  //  url = googleUrl;
                } else {
                    // Local MySQL instance to use during development.
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }
       // System.out.println("Hier geht niiiiiiiiiiichtssssssssssssss" + con);
        // Zur�ckgegeben der Verbindung
        return con;
    }

}
