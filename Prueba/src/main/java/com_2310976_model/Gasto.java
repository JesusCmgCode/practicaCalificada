package com_2310976_model;

public class Gasto {
    private int id;
    private String descripcion;
    private String categoria;
    private double monto;
    private String fechaGasto;

    private Gasto(Builder builder) {
        this.id = builder.id;
        this.descripcion = builder.descripcion;
        this.categoria = builder.categoria;
        this.monto = builder.monto;
        this.fechaGasto = builder.fechaGasto;
    }

    public static class Builder {
        private int id;
        private String descripcion;
        private String categoria;
        private double monto;
        private String fechaGasto;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder setCategoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder setMonto(double monto) {
            this.monto = monto;
            return this;
        }

        public Builder setFechaGasto(String fechaGasto) {
            this.fechaGasto = fechaGasto;
            return this;
        }

        public Gasto build() {
            return new Gasto(this);
        }
    }

    
    
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getMonto() {
        return monto;
    }

    public String getFechaGasto() {
        return fechaGasto;
    }
}

