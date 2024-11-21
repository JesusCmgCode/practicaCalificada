package com_2310976_repository;

import com_2310976_model.Gasto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GastoRepository {
    private static final String URL = "jdbc:mysql://localhost:3310/gastos_personales";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public void save(Gasto gasto) throws SQLException {
        String query = "INSERT INTO gastos (descripcion, categoria, monto, fecha_gasto) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gasto.getDescripcion());
            stmt.setString(2, gasto.getCategoria());
            stmt.setDouble(3, gasto.getMonto());
            stmt.setString(4, gasto.getFechaGasto());
            stmt.executeUpdate();
        }
    }

    public List<Gasto> findAll(String filtroCategoria) throws SQLException {
        List<Gasto> gastos = new ArrayList<>();
        String query = "SELECT * FROM gastos";
        if (filtroCategoria != null && !filtroCategoria.isEmpty()) {
            query += " WHERE categoria = ?";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (filtroCategoria != null && !filtroCategoria.isEmpty()) {
                stmt.setString(1, filtroCategoria);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gastos.add(new Gasto.Builder()
                            .setId(rs.getInt("id"))
                            .setDescripcion(rs.getString("descripcion"))
                            .setCategoria(rs.getString("categoria"))
                            .setMonto(rs.getDouble("monto"))
                            .setFechaGasto(rs.getString("fecha_gasto"))
                            .build());
                }
            }
        }
        return gastos;
    }

    public double calcularTotalMensual() throws SQLException {
        String query = "SELECT SUM(monto) as total FROM gastos WHERE MONTH(fecha_gasto) = MONTH(CURRENT_DATE)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0;
    }

    public void update(Gasto gasto) throws SQLException {
        String query = "UPDATE gastos SET descripcion = ?, categoria = ?, monto = ?, fecha_gasto = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gasto.getDescripcion());
            stmt.setString(2, gasto.getCategoria());
            stmt.setDouble(3, gasto.getMonto());
            stmt.setString(4, gasto.getFechaGasto());
            stmt.setInt(5, gasto.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM gastos WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Object[]> resumenPorCategoria() throws SQLException {
        String query = "SELECT categoria, COUNT(*) as cantidad, SUM(monto) as total FROM gastos GROUP BY categoria";
        List<Object[]> resumen = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                resumen.add(new Object[]{rs.getString("categoria"), rs.getInt("cantidad"), rs.getDouble("total")});
            }
        }
        return resumen;
    }
}

