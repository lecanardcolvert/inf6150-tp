package ca.uqam.inf6150.ete2021.gr010.flight.db.connection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.slf4j.XSlf4j;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

@XSlf4j
@Getter()
@Setter(AccessLevel.PRIVATE)
public final class DBConnection
        implements Closeable {

    private static final String URL      = "jdbc:oracle:thin:@zeta2.labunix.uqam.ca:1521:baclab";
    private static final String USERNAME = "bj991090";
    private static final String PASSWORD = "iTfunnzz";

    private static DBConnection singleton;

    private ConnectionSource m_source;

    private DBConnection() {
        open();
    }

    @Synchronized
    public static DBConnection getOrCreate() {
        if (singleton == null) {
            singleton = new DBConnection();
        }

        return singleton;
    }

    public <TClass> Dao<TClass, Long> getDao(Class<TClass> p_entityClass) throws SQLException {
        return DaoManager.createDao(getSource(), p_entityClass);
    }

    public void open() {
        try {
            close();
        }
        catch (IOException p_thrown) {
            log.catching(p_thrown);
            releaseConnectionHandle();
        }

        try {
            var connectionPlaceholder = new JdbcPooledConnectionSource(
                    URL,
                    USERNAME,
                    PASSWORD,
                    new CustomOracleDatabaseType());

            setSource(connectionPlaceholder);

            log.info("DB successfully connected.");
        }
        catch (SQLException p_thrown) {
            log.catching(p_thrown);
            releaseConnectionHandle();
        }
    }

    @Override
    public void close() throws IOException {
        if (isOpen()) {
            getSource().close();
            releaseConnectionHandle();
        }
    }

    public boolean isOpen() {
        return getSource() != null;
    }

    private void releaseConnectionHandle() {
        setSource(null);
    }
}
