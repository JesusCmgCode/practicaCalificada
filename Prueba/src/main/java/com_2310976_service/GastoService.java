package com_2310976_service;

import com_2310976_model.Gasto;
import com_2310976_repository.GastoRepository;
import java.sql.SQLException;
import java.util.List;

public class GastoService {
    private final GastoRepository repository;

    public GastoService() {
        this.repository = new GastoRepository();
    }

    public void agregarGasto(String descripcion, String categoria, double monto, String fecha) throws SQLException {
        validarMonto(monto);
        validarCategoria(categoria);
        validarLimiteMensual(monto);
        Gasto gasto = new Gasto.Builder()
                .setDescripcion(descripcion)
                .setCategoria(categoria)
                .setMonto(monto)
                .setFechaGasto(fecha)
                .build();
        repository.save(gasto);
    }

    public List<Gasto> listarGastos(String filtroCategoria) throws SQLException {
        return repository.findAll(filtroCategoria);
    }

    public void editarGasto(int id, String descripcion, String categoria, double monto, String fecha) throws SQLException {
        validarMonto(monto);
        validarCategoria(categoria);
        Gasto gasto = new Gasto.Builder()
                .setId(id)
                .setDescripcion(descripcion)
                .setCategoria(categoria)
                .setMonto(monto)
                .setFechaGasto(fecha)
                .build();
        repository.update(gasto);
    }

    public void eliminarGasto(int id) throws SQLException {
        repository.delete(id);
    }

    public List<Object[]> obtenerResumenPorCategoria() throws SQLException {
        return repository.resumenPorCategoria();
    }

    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    private void validarCategoria(String categoria) {
        List<String> categoriasPermitidas = List.of("Alimentacion", "Transporte", "Entretenimiento", "Salud", "Otros");
        if (!categoriasPermitidas.contains(categoria)) {
            throw new IllegalArgumentException("Categoria no permitida");
        }
    }

    private void validarLimiteMensual(double monto) throws SQLException {
        double totalMensual = repository.calcularTotalMensual();
        if (totalMensual + monto > 5000) {
            throw new IllegalArgumentException("El gasto excede el limite mensual de S/5000");
        }
    }
}

