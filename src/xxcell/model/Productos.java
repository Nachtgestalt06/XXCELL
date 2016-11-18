/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xxcell.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pérez Garduño
 */
public class Productos {
    private StringProperty ID;
    private StringProperty Modelo;
    private StringProperty Nombre;
    private StringProperty Marca;
    private DoubleProperty PrecioPub;
    private DoubleProperty PrecioDist;
    private StringProperty Tipo;
    private StringProperty Descrip;
    private IntegerProperty Dispon;
    private IntegerProperty L58;
    private IntegerProperty L64;
    private IntegerProperty L127;
    
    

    public Productos(){
        this(null,null,null,null,0,0,null,null,0,0,0,0);
        /*this.ID = "";
        this.Marca = "";
        this.Modelo = "";*/
    }

    public Productos(String ID, String Marca, String Modelo, String Nombre, double PrecioPub, double PrecioDist,String Tipo, String Descrip, int Dispon, int l58, int l64, int l127){
        this.ID = new SimpleStringProperty(ID);
        this.Marca = new SimpleStringProperty(Marca);
        this.Modelo = new SimpleStringProperty(Modelo);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.PrecioPub = new SimpleDoubleProperty(PrecioPub);
        this.PrecioDist = new SimpleDoubleProperty(PrecioDist);
        this.Tipo = new SimpleStringProperty(Tipo);
        this.Descrip = new SimpleStringProperty(Descrip);
        this.Dispon = new SimpleIntegerProperty(Dispon);
        this.L58 = new SimpleIntegerProperty(l58);
        this.L64 = new SimpleIntegerProperty(l64);
        this.L127 = new SimpleIntegerProperty(l127);
    }

    public Productos(String ID, String Marca, String Modelo, String Nombre, double PrecioPub, double PrecioDist,String Tipo, String Descrip, int Dispon) {
        this.ID = new SimpleStringProperty(ID);
        this.Marca = new SimpleStringProperty(Marca);
        this.Modelo = new SimpleStringProperty(Modelo);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.PrecioPub = new SimpleDoubleProperty(PrecioPub);
        this.PrecioDist = new SimpleDoubleProperty(PrecioDist);
        this.Tipo = new SimpleStringProperty(Tipo);
        this.Descrip = new SimpleStringProperty(Descrip);
        this.Dispon = new SimpleIntegerProperty(Dispon);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String name) {
        this.ID.set(name);
    }
    
    public StringProperty idProperty() {
        return ID;
    }

    public String getMarca() {
        return Marca.get();
    }

    public void setMarca(String Marca) {
        this.Marca.set(Marca);
    }
    
    public StringProperty marcaProperty() {
        return Marca;
    }
    

    public String getModelo() {
        return Modelo.get();
    }
    
    public DoubleProperty preciopubProperty()
    {
        return PrecioPub;
    }
    
    public DoubleProperty preciodistProperty()
    {
        return PrecioDist;
    }
    
    public IntegerProperty disponProperty()
    {
        return Dispon;
    }
    
    public IntegerProperty l58Property()
    {
        return L58;
    }
    
    public IntegerProperty l64Property()
    {
        return L64;
    }
    
    public IntegerProperty l127Property()
    {
        return L127;
    }

    public void setModelo(String Modelo) {
        this.Modelo.set(Modelo);
    }
    
    public StringProperty modeloProperty() {
        return Modelo;
    }
    
    public StringProperty nombreProperty() {
        return Nombre;
    }
    
    public StringProperty tipoProperty() {
        return Tipo;
    }
    
    public StringProperty descripProperty() {
        return Descrip;
    }
    public String getNombre()
    {
        return Nombre.get();
    }
    public String getTipo()
    {
        return Tipo.get();
    }
    public String getDescrip()
    {
        return Descrip.get();
    }
    public double getPrecioPub()
    {
        return PrecioPub.get();
    }
    public double getPrecioDist()
    {
        return PrecioDist.get();
    }
    public int getDispon()
    {
        return Dispon.get();
    }
    public int getL58()
    {
        return L58.get();
    }
    public int getL64()
    {
        return L64.get();
    }
    public int getL127()
    {
        return L127.get();
    }
}