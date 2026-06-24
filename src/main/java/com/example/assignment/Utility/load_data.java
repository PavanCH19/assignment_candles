package com.example.assignment.Utility;

import com.datastax.oss.driver.api.core.CqlSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class load_data {
   public static void upload_data() throws Exception {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("stock_keyspace")
                .build();

       Files.lines(Paths.get("C:\\Users\\pavan\\Desktop\\assignment\\src\\main\\java\\com\\example\\assignment\\Utility\\stock_data.csv"))
               .skip(1)
               .forEach(line -> {

                   String[] p = line.split(",");

                   DateTimeFormatter formatter =
                           DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                   LocalDateTime dateTime =
                           LocalDateTime.parse(p[1], formatter);

                   String query = String.format(
                           """
                           INSERT INTO stock_data
                           (symbol, datetime, open, high, low, close, volume)
                           VALUES
                           ('%s', '%s', %s, %s, %s, %s, %s)
                           """,
                           p[0],
                           dateTime,
                           p[2],
                           p[3],
                           p[4],
                           p[5],
                           p[6]
                   );

                   session.execute(query);
               });

        session.close();
        System.out.println("Data Loaded");
    }
}
