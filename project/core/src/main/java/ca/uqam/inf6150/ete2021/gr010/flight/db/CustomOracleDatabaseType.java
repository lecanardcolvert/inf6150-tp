package ca.uqam.inf6150.ete2021.gr010.flight.db;

import com.j256.ormlite.jdbc.db.OracleDatabaseType;

public class CustomOracleDatabaseType
        extends OracleDatabaseType {

    @Override
    public boolean isLimitSqlSupported() {
        return false;
    }
}