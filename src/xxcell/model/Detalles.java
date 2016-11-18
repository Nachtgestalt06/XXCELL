package xxcell.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Detalles {
    private IntegerProperty Folio;
    private StringProperty Codigo;
    private StringProperty Producto;
    private IntegerProperty Cantidad;
    private FloatProperty Total;
    
    public Detalles(){
        
    }
    
    public Detalles(int Folio, String Codigo, String Producto, int Cantidad, float Total){
        this.Folio = new SimpleIntegerProperty(Folio);
        this.Codigo = new SimpleStringProperty(Codigo);
        this.Producto = new SimpleStringProperty(Producto);
        this.Cantidad = new SimpleIntegerProperty(Cantidad);
        this.Total = new SimpleFloatProperty(Total);
    }
    
    public IntegerProperty FolioProperty(){
        return Folio;
    }
    
    public StringProperty CodigoProperty(){
        return Codigo;
    }
    
    public StringProperty ProductoProperty(){
        return Producto;
    }
    
    public IntegerProperty CantidadProperty(){
        return Cantidad;
    }
    
    public FloatProperty TotalProperty(){
        return Total;
    }
    
}
