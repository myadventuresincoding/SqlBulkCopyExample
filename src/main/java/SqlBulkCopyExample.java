import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class SqlBulkCopyExample {

    private static String JDBC_URL = "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;DatabaseName=Examples";
    private static String USERNAME = "client";
    private static String PASSWORD = "client";
    private static String TABLE_NAME = "dbo.SqlBulkInsertExample";

    public static void main(String[] args) {
        try {
            // Create some data to insert into our database table
            Map<Integer, String> data = new HashMap<>();
            data.put(1, "John Smith");
            data.put(2, "Steve Smith");
            data.put(3, "Molly Smith");

            // We are going to build a CSV document to use for the bulk insert
            StringBuilder stringBuilder = new StringBuilder();

            // Add table column names to CSV
            stringBuilder.append("id, name\n");

            // Copy data from map and append to CSV
            for (Map.Entry entry : data.entrySet()) {
                stringBuilder.append(
                        String.format("%s,%s\n", entry.getKey(), entry.getValue()));
            }

            byte[] bytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
            try (InputStream inputStream = new ByteArrayInputStream(bytes)) {

                // Pass in input stream and set column information
                SQLServerBulkCSVFileRecord fileRecord = new SQLServerBulkCSVFileRecord(
                        inputStream, StandardCharsets.UTF_8.name(), ",", true);

                fileRecord.addColumnMetadata(1, "id", Types.INTEGER, 0, 0);
                fileRecord.addColumnMetadata(2, "name", Types.VARCHAR, 0, 0);

                try (Connection connection = DriverManager.getConnection(
                        JDBC_URL, USERNAME, PASSWORD)) {

                    // Set bulk insert options, for example here I am setting a batch size
                    SQLServerBulkCopyOptions copyOptions = new SQLServerBulkCopyOptions();
                    copyOptions.setBatchSize(10000);

                    // Write the CSV document to the database table
                    try (SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(connection)) {
                        bulkCopy.setBulkCopyOptions(copyOptions);
                        bulkCopy.setDestinationTableName(TABLE_NAME);
                        bulkCopy.writeToServer(fileRecord);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
