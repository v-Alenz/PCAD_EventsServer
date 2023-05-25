package com.eventi.gestione_eventi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class EventiMySql {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/event_database";
    private static final String dbUser = "root";
    private static final String dbPasswd = "threadementi";

    // QUERRYs
    private static final String querryList = "SELECT * FROM events";
    private static final String querryCreate = "INSERT INTO events(name, seats, max_seats) VALUES (?, ?, ?)";
    private static final String querryRemove = "DELETE FROM events WHERE name=?";
    private static final String querryBook = "UPDATE events SET seats=seats-? WHERE name=?";
    private static final String querryAdd = "UPDATE events SET max_seats=max_seats+?, seats=seats+? WHERE name=?";

    public EventiMySql() {
        try {
        } catch (Exception e) {

        }
    }

    private Connection connectDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionUrl, dbUser, dbPasswd);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    private void disconnectDatabase(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void eventCreate(String name, Integer max_seats) {
        Connection conn = connectDatabase();
        if (conn == null)
            return;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryCreate);
            stmt.setString(1, name);
            stmt.setInt(2, max_seats);
            stmt.setInt(3, max_seats);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getSQLState());
                }
            }
        }
    }

    public void eventeDelete(String name) {

        Connection conn = connectDatabase();
        if (conn == null)
            return;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryRemove);
            stmt.setString(1, name);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getSQLState());
                }
            }
        }
    }

    public void eventBook(String name, Integer seats) {
        Connection conn = connectDatabase();
        if (conn == null)
            return;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryBook);
            stmt.setInt(1, seats);
            stmt.setString(2, name);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getSQLState());
                }
            }
        }
    }

    public Map<String, Evento> eventGetList() {
        Map<String, Evento> list =  new TreeMap<>();
        Connection conn = connectDatabase();
        if (conn == null)
            return list;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryList);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())

                list.put(rs.getString("name"), new Evento(rs.getString("name"), rs.getInt("seats"), rs.getInt("max_seats")));

        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getSQLState());
                }
            }
        }
        for (Evento evento : list.values()) {
            System.out.println( evento.toString()); 
        }
        return list;
    }

    public void eventAdd(String name, Integer seats) {
        Connection conn = connectDatabase();
        if (conn == null)
            return;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryAdd);
            stmt.setInt(1, seats);
            stmt.setInt(2, seats);
            stmt.setString(3, name);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getSQLState());
                }
            }
        }
    }


    // public static void /* TEST */ main(String[] args) {
    //     var sql = new EventiMySql();
    //     var conn = sql.connectDatabase();
    //     // sql.eventCreate("che fine ha fatto mike bongiorno?", 1000);
    //     // sql.eventBook("cazzi", 10);
    //     // sql.eventAdd("cazzi", 100);
    //     // sql.eventeDelete("che fine ha fatto mike bongiorno?");
    //     sql.eventGetList();
    // }
}
