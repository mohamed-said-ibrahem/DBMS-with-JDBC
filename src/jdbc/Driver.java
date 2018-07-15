package jdbc;

import java.io.File;
import java.io.IOException;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Driver() {
        protocol = new String();
    }

    /**
     * @param url
     *            the URL of the database.
     * @return boolean to check whether the driver thinks that it can open a
     *         connection to the given URL
     */
    @Override
    public boolean acceptsURL(String url) throws SQLException {
        boolean typeSafe;
        String[] tmp = url.split(":");
        this.setProtocol(tmp[1]);
        typeSafe = (protocol.equals("xmldb")) || (protocol.equals("altdb"));
        if (tmp[0].equals("jdbc") && typeSafe && tmp[2].equals("//localhost")) {
            return true;
        }
        return false;
    }

    @Override
    public Connection connect(String url, Properties info)
            throws SQLException {
        Connection connection = null;
        if (!acceptsURL(url)) {
            throw new SQLException("database access error occurs");
        }
        checkExistence(info);
        connection = new Connection(url, info);
        return connection;
    }

    private void checkExistence(Properties info) {
        File file = (File) info.get("path");
        if (!file.exists()) {

            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
            throws SQLException {

        if (!acceptsURL(url)) {
            throw new SQLException("database access error occurs");
        }
        return new DriverPropertyInfo[] {
                new DriverPropertyInfo("path",
                        ((File) info.get("path")).getAbsolutePath()) };

    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException("Unsupported Method");
    }


}
