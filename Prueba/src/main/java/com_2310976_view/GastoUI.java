package com_2310976_view;

import com_2310976_controller.GastoController;
import com_2310976_model.Gasto;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.TitledBorder;

public class GastoUI extends JFrame {
    private JTextField txtDescripcion, txtCategoria, txtMonto, txtFecha, txtFiltroCategoria;
    private JTextArea txtResultados;
    private JButton btnAgregar, btnListar, btnEditar, btnEliminar, btnResumen, btnLimpiar;
    private GastoController controller;

    public GastoUI(GastoController controller) {
        this.controller = controller;

        // Configuración principal de la ventana
        setTitle("Gestión de Gastos Personales");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout());

        // Estilo general
        UIManager.put("Panel.background", new Color(230, 242, 255));

        // Crear y agregar los paneles
        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelResultados(), BorderLayout.CENTER);
        add(crearPanelAcciones(), BorderLayout.SOUTH);

        registrarEventos();
    }

    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                null,
                "Registrar / Filtrar Gastos",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));

        // Campos para ingresar datos
        panelFormulario.add(crearLabel("Descripcion:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(crearLabel("Categoria (Alimentacion/Transporte/Entretenimiento/Salud/Otros):"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(crearLabel("Monto:"));
        txtMonto = new JTextField();
        panelFormulario.add(txtMonto);

        panelFormulario.add(crearLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(crearLabel("Filtrar por Categoria:"));
        txtFiltroCategoria = new JTextField();
        panelFormulario.add(txtFiltroCategoria);

        btnAgregar = new JButton("Registrar Gasto");
        btnAgregar.setBackground(new Color(173, 216, 230));
        panelFormulario.add(btnAgregar);

        btnListar = new JButton("Filtrar Gastos");
        btnListar.setBackground(new Color(173, 216, 230));
        panelFormulario.add(btnListar);

        return panelFormulario;
    }

    private JScrollPane crearPanelResultados() {
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtResultados.setBackground(new Color(245, 245, 245));

        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder(
                null,
                "Resultados",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));
        return scrollResultados;
    }

    private JPanel crearPanelAcciones() {
        JPanel panelAcciones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelAcciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnEditar = new JButton("Editar Gasto");
        btnEditar.setBackground(new Color(255, 255, 224));

        btnEliminar = new JButton("Eliminar Gasto");
        btnEliminar.setBackground(new Color(255, 255, 224));

        btnResumen = new JButton("Resumen de Gastos");
        btnResumen.setBackground(new Color(255, 255, 224));

        btnLimpiar = new JButton("Limpiar Resultados");
        btnLimpiar.setBackground(new Color(255, 255, 224));

        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnResumen);
        panelAcciones.add(btnLimpiar);

        return panelAcciones;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 14)); 
        label.setForeground(Color.BLACK);
        return label;
    }

    private void registrarEventos() {
        btnAgregar.addActionListener(e -> agregarGasto());
        btnListar.addActionListener(e -> listarGastos());
        btnEditar.addActionListener(e -> editarGasto());
        btnEliminar.addActionListener(e -> eliminarGasto());
        btnResumen.addActionListener(e -> mostrarResumen());
        btnLimpiar.addActionListener(e -> limpiarResultados());
    }

    private void agregarGasto() {
        try {
            String descripcion = txtDescripcion.getText();
            String categoria = txtCategoria.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            String fecha = txtFecha.getText();

            String mensaje = controller.agregarGasto(descripcion, categoria, monto, fecha);
            mostrarMensaje(mensaje);
            limpiarCampos();
        } catch (NumberFormatException ex) {
            mostrarMensaje("El monto debe ser un numero valido");
        }
    }

    private void listarGastos() {
        String filtroCategoria = txtFiltroCategoria.getText();
        List<Gasto> gastos = controller.listarGastos(filtroCategoria);

        StringBuilder sb = new StringBuilder();
        for (Gasto gasto : gastos) {
            sb.append(String.format("ID: %d | Descripcion: %s | Categoria: %s | Monto: %.2f | Fecha: %s%n",
                    gasto.getId(), gasto.getDescripcion(), gasto.getCategoria(), gasto.getMonto(), gasto.getFechaGasto()));
        }
        txtResultados.setText(sb.toString());
    }

    private void editarGasto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del gasto a editar:"));
            String descripcion = JOptionPane.showInputDialog("Nueva descripcion:");
            String categoria = JOptionPane.showInputDialog("Nueva categoria:");
            double monto = Double.parseDouble(JOptionPane.showInputDialog("Nuevo monto:"));
            String fecha = JOptionPane.showInputDialog("Nueva fecha (YYYY-MM-DD):");

            String mensaje = controller.editarGasto(id, descripcion, categoria, monto, fecha);
            mostrarMensaje(mensaje);
        } catch (NumberFormatException ex) {
            mostrarMensaje("El ID o el monto ingresados no son validos");
        }
    }

    private void eliminarGasto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del gasto a eliminar:"));
            String mensaje = controller.eliminarGasto(id);
            mostrarMensaje(mensaje);
        } catch (NumberFormatException ex) {
            mostrarMensaje("El ID ingresado no es valido");
        }
    }

    private void mostrarResumen() {
        List<Object[]> resumen = controller.obtenerResumenPorCategoria();
        StringBuilder sb = new StringBuilder("Resumen de Gastos:\n");
        for (Object[] categoria : resumen) {
            sb.append(String.format("Categoria: %s | Cantidad: %d | Total: %.2f%n",
                    categoria[0], categoria[1], categoria[2]));
        }
        txtResultados.setText(sb.toString());
    }

    private void limpiarResultados() {
        txtResultados.setText(""); 
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtCategoria.setText("");
        txtMonto.setText("");
        txtFecha.setText("");
    }
}
