package com_2310976_controller;

import com_2310976_model.Gasto;
import com_2310976_service.GastoService;
import java.sql.SQLException;
import java.util.List;

public class GastoController {
    private final GastoService service;

    public GastoController() {
        this.service = new GastoService();
    }

        public String agregarGasto(String descripcion, String categoria, double monto, String fecha) {
            try {
                service.agregarGasto(descripcion, categoria, monto, fecha);
                return "El gasto se registro";
            } catch (Exception e) {
                return "Error al querer registrar gasto: " + e.getMessage();
            }
        }

        public List<Gasto> listarGastos(String filtroCategoria) {
            try {
                return service.listarGastos(filtroCategoria);
            } catch (SQLException e) {
                System.err.println("Error al querer listar gastos: " + e.getMessage());
                return null;
            }
        }

        public String editarGasto(int id, String descripcion, String categoria, double monto, String fecha) {
            try {
                service.editarGasto(id, descripcion, categoria, monto, fecha);
                return "Tu Gasto actualizado";
            } catch (Exception e) {
                return "Error al querer actualizar gasto: " + e.getMessage();
            }
        }

        public String eliminarGasto(int id) {
            try {
                service.eliminarGasto(id);
                return "Gasto eliminado";
            } catch (SQLException e) {
                return "Error al querer eliminar gasto: " + e.getMessage();
            }
        }

        public List<Object[]> obtenerResumenPorCategoria() {
            try {
                return service.obtenerResumenPorCategoria();
            } catch (SQLException e) {
                System.err.println("Error al querer obtener resumen: " + e.getMessage());
                return null;
            }
        }
}




