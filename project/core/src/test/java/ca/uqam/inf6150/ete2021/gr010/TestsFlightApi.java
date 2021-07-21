package ca.uqam.inf6150.ete2021.gr010;

import ca.uqam.inf6150.ete2021.gr010.flight.api.FlightAPI;
import ca.uqam.inf6150.ete2021.gr010.flight.model.Country;
import ca.uqam.inf6150.ete2021.gr010.flight.model.Flight;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestsFlightApi {
    //Test de la méthode FlightAPI.getall()
    @Test
    public void testDepart() throws SQLException {
        Timestamp TimeStampDepart = Collections.singletonList(FlightAPI.getAll().get(1).getDeparture()).get(0);
        Timestamp testTimeStampDepart = Timestamp.valueOf("2022-01-12 03:30:00.0");
        assertEquals(testTimeStampDepart,TimeStampDepart);
    }

    @Test
    public void testArrive() throws SQLException {
        Timestamp TimeStampArrive = Collections.singletonList(FlightAPI.getAll().get(5).getArrival()).get(0);
        Timestamp testTimeStampArrive = Timestamp.valueOf("2021-12-12 21:00:00.0");
        assertEquals(testTimeStampArrive,TimeStampArrive);
    }

    @Test
    public void testPaysDepart() throws SQLException {
        String pays = FlightAPI.getAll().get(8).getBeginAirport().getCity().getCountry().getName();
        assertEquals("Taîwan",pays);
    }

    @Test
    public void testManufacturier() throws SQLException {
        String manufacturier = FlightAPI.getAll().get(17).getAircraft().getManufacturer().getName();
        assertNotEquals("Airbus",manufacturier);
    }

    @Test
    public void testNombreVols() throws  SQLException {
        assertEquals(30,FlightAPI.getDao().countOf());
    }
    @Test
    public void testFlight() throws  SQLException {
       assertEquals("FLIGHT",FlightAPI.getDao().getTableName());
    }

    @Test
    public void testCoordonnesArriveeLat() throws SQLException {
        double testCoorArr = FlightAPI.getAll().get(27).getEndAirport().getCity().getLatitude();
        assertEquals(-4.2634,testCoorArr);
    }

    @Test
    public void testCoordonnesDepLong() throws SQLException {
        double testCoorDep= FlightAPI.getAll().get(14).getBeginAirport().getCity().getLongitude();
        assertEquals(-75.6972,testCoorDep);
    }


}