package ca.uqam.inf6150.ete2021.gr010.flight.db.api;

import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@XSlf4j
class TestsFlightAPI {

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testDepart() throws SQLException {
        Timestamp TimeStampDepart     = Collections.singletonList(FlightAPI.fetchAll().get(1).getDeparture()).get(0);
        Timestamp testTimeStampDepart = Timestamp.valueOf("2021-12-05 03:15:00.0");
        assertEquals(testTimeStampDepart, TimeStampDepart);
        log.debug("Date et heure de depart de l'avant-dernier vol: " + TimeStampDepart);
    }

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testArrive() throws SQLException {
        Timestamp TimeStampArrive     = Collections.singletonList(FlightAPI.fetchAll().get(5).getArrival()).get(0);
        Timestamp testTimeStampArrive = Timestamp.valueOf("2021-11-20 03:50:00.0");
        assertEquals(testTimeStampArrive, TimeStampArrive);
        log.debug("Date et heure d'arrivee du 6eme vol: " + TimeStampArrive);
    }

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testPaysDepart() throws SQLException {
        String pays = FlightAPI.fetchAll().get(8).getBeginAirport().getCity().getCountry().getName();
        assertEquals("Canada", pays);
        log.debug("Pays de depart du vol 9: " + pays);
    }

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testManufacturier() throws SQLException {
        String manufacturier = FlightAPI.fetchAll().get(17).getAircraft().getManufacturer().getName();
        assertNotEquals("Airbus", manufacturier);
        log.debug("Manufacturier du vol 18: " + manufacturier);
    }

    //Test de la méthode FlightAPI.getDao()
    @Test
    public void testNombreVols() throws SQLException {
        assertEquals(30, FlightAPI.getDao().countOf());
        log.debug("Nombre de vols: " + FlightAPI.getDao().countOf());
    }

    //Test de la méthode FlightAPI.getDao()
    @Test
    public void testFlight() throws SQLException {
        assertEquals("FLIGHT", FlightAPI.getDao().getTableName());
        log.debug("Nom table de vol: " + FlightAPI.getDao().getTableName());
    }

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testCoordonnesArriveeLat() throws SQLException {
        double testCoorArr = FlightAPI.fetchAll().get(27).getEndAirport().getCity().getLatitude();
        assertEquals(48.2082, testCoorArr);
        log.debug("Latitude de ville d'arrivee du vol 28: " + testCoorArr);
    }

    //Test de la méthode FlightAPI.fetchAll()
    @Test
    public void testCoordonnesDepLong() throws SQLException {
        double testCoorDep = FlightAPI.fetchAll().get(14).getBeginAirport().getCity().getLongitude();
        assertEquals(71.4704, testCoorDep);
        log.debug("Longitude ville de depart du vol 15: " + testCoorDep);
    }

    //Test de la méthode FlightAPI.fetchForemostInTime()
    @Test
    public void testLatestDepart() throws SQLException {
        Timestamp TimestampDepart     = FlightAPI.fetchForemostInTime().getDeparture();
        Timestamp testTimeStampDepart = Timestamp.valueOf("2021-12-30 22:50:00.0");
        assertEquals(testTimeStampDepart, TimestampDepart);
        log.debug("Dernier vol prevu: " + TimestampDepart);

    }

    //Test de la méthode FlightAPI.fetchForemostInTime()
    @Test
    public void testLatestAirline() throws SQLException {
        String airline = FlightAPI.fetchForemostInTime().getAirline().getName();
        assertEquals("British Airways", airline);
        log.debug("Compagnie aerienne du dernier vol: " + airline);
    }

    //Test de la méthode FlightAPI.fetchLatest(Timestamp)
    @Test
    public void testLatestDepartAvecDate() throws SQLException {
        Timestamp TimestampLatest     = Timestamp.valueOf("2021-10-07 03:45:00.0");
        Timestamp testTimestampLatest = Timestamp.valueOf("2021-10-10 01:44:00.0");
        assertEquals(TimestampLatest, FlightAPI.fetchLatest(testTimestampLatest).getDeparture());
        log.debug("Dernier vol avant le 10 octobre 2020 a 1h44: " + TimestampLatest);
    }

    //Test de la méthode FlightAPI.fetchLatest(Timestamp)
    @Test
    public void testLatestDepartAvecDate2() throws SQLException {
        Timestamp TimestampLatest     = Timestamp.valueOf("2021-09-02 10:05:00.0");
        Timestamp testTimestampLatest = Timestamp.valueOf("2021-08-27 02:19:00.0");
        assertNotEquals(TimestampLatest, FlightAPI.fetchLatest(testTimestampLatest).getDeparture());
        log.debug("Dernier vol avant le 27 aout 2021 a 2h19: " +
                  FlightAPI.fetchLatest(testTimestampLatest).getDeparture());
    }

    //Test de la méthode FlightAPI.fetchLatestSequence(Timestamp,int)
    @Test
    public void testLatestSequenceTimestamp() throws SQLException {
        Timestamp TimestampLatest = Timestamp.valueOf("2021-09-02 10:05:00.0");
        String compagnieAerienne = FlightAPI.fetchLatestSequence(TimestampLatest, 3).
                                            get(0).getAirline().getName();
        assertEquals("Hong Kong Airlines Limited", compagnieAerienne);
        log.debug("Compagnie aerienne du vol le plus proche du 2 septembre 2021: " + compagnieAerienne);
    }

    //Test de la méthode FlightAPI.fetchLatestSequence(Timestamp,int)
    @Test
    public void testLatestSequenceTimestamp2() throws SQLException {
        Timestamp TimestampLatest = Timestamp.valueOf("2021-11-11 04:20:00.0");
        String model = FlightAPI.fetchLatestSequence(TimestampLatest, 4).
                                get(2).getAircraft().getModel();
        assertEquals("717", model);
        log.debug("Model d'avion du 3eme vol avant celui du 11 novembre 2021: " + model);
    }
}