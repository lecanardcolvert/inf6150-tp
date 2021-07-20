package ca.uqam.inf6150.ete2021.gr010.flight.api;

import ca.uqam.inf6150.ete2021.gr010.flight.db.DBConnection;
import ca.uqam.inf6150.ete2021.gr010.flight.model.Flight;
import ca.uqam.inf6150.ete2021.gr010.flight.model.meta.FlightTable;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public interface FlightAPI {

    static Flight getLatest() throws SQLException {
        var dao = getDao();

        return dao.queryBuilder()
                  .orderByNullsLast(FlightTable.COL_NAME_DEPARTURE, false)
                  .limit(1L)
                  .queryForFirst();
    }

    static Dao<Flight, Long> getDao() throws SQLException {
        return DBConnection.getOrCreate().getDao(getEntityClass());
    }

    private static Class<Flight> getEntityClass() {
        return Flight.class;
    }
}
