/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xxcell.model;

import java.text.DecimalFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author snak0
 */
public class ProductoVenta {
    private StringProperty Codigo;
    private StringProperty Modelo;
    private StringProperty Nombre;
    private StringProperty Precio;
    private StringProperty Cantidad;
    private StringProperty Importe;    
    private StringProperty Descuento;
    private StringProperty Preciobase;

    DecimalFormat formateador = new DecimalFormat("####.00");

    public ProductoVenta(String codigo, String nombre, String modelo, String cantidad, String preciobase, String precio, String descuento, String importe) {
        this.Codigo = new SimpleStringProperty(codigo);
        this.Nombre = new SimpleStringProperty(nombre);
        this.Modelo = new SimpleStringProperty(modelo);
        this.Cantidad = new SimpleStringProperty(cantidad);
        this.Precio = new SimpleStringProperty(precio);
        this.Importe = new SimpleStringProperty(importe);
        this.Descuento = new SimpleStringProperty(descuento);
        this.Preciobase = new SimpleStringProperty(preciobase);
    }

    public void setDescuento(StringProperty Descuento) {
        this.Descuento = Descuento;
    }

    public void setCodigo(StringProperty Codigo) {
        this.Codigo = Codigo;
    }

    public void setModelo(StringProperty Modelo) {
        this.Modelo = Modelo;
    }

    public void setNombre(StringProperty Nombre) {
        this.Nombre = Nombre;
    }

    public void setPrecio(StringProperty Costo) {
        this.Precio = Costo;
    }

    public void setCantidad(StringProperty Cantidad) {
        this.Cantidad = Cantidad;
    }

    public void setImporte(StringProperty Importe) {
        this.Importe = Importe;
    }
    
    public void setPreciobase(StringProperty Preciobase) {
        this.Preciobase = Preciobase;
    }

    public String getCodigo() {
        return Codigo.get();
    }

    public String getModelo() {
        return Modelo.get();
    }

    public String getNombre() {
        return Nombre.get();
    }

    public String getPrecio() {
        return Precio.get();
    }

    public String getCantidad() {
        return Cantidad.get();
    }

    public String getImporte() {
        return Importe.get();
    }
    
    public String getDescuento() {
        return Descuento.get();
    }
    
    public String getPreciobase() {
        return Preciobase.get();
    }
    
    public StringProperty codigoProperty() {
        return Codigo;
    }
    
    public StringProperty modeloProperty() {
        return Modelo;
    }
    
    public StringProperty nombreProperty() {
        return Nombre;
    }
    
    public StringProperty precioProperty() {
        return Precio;
    }
    
    public StringProperty cantidadProperty() {
        return Cantidad;
    }
    
    public StringProperty descuentoProperty() {
        return Descuento;
    }
    
    public StringProperty importeProperty() {
        return Importe;
    }
    
    public StringProperty preciobaseProperty() {
        return Preciobase;
    }

    public void setCantidadProperty(String cantidad) {
        this.Cantidad = new SimpleStringProperty(cantidad);
    }
    
    public void setImporteProperty(String importe) {
        this.Importe = new SimpleStringProperty(importe);
    }

    public void setPrecioProperty(String precio) {
        this.Precio = new SimpleStringProperty(precio);
    }
    
    public void setDescuentoProperty(String descuento) {
        this.Descuento = new SimpleStringProperty(descuento);
    }
}
