package ciallo.glasssky.jdbc;

import java.sql.SQLException;

public interface Format {
    public void init() throws SQLException;
    public void end();
}
