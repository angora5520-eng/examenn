package com.example.examen.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore // Esto es clave para evitar un ciclo infinito al mostrar el JSON
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Constructor vacío
    public DetallePedido() {
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Integer getCantidad() { 
        return cantidad; 
    }
    
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
    }
    
    public Double getSubtotal() { 
        return subtotal; 
    }
    
    public void setSubtotal(Double subtotal) { 
        this.subtotal = subtotal; 
    }
    
    public Pedido getPedido() { 
        return pedido; 
    }
    
    public void setPedido(Pedido pedido) { 
        this.pedido = pedido; 
    }
    
    public Producto getProducto() { 
        return producto; 
    }
    
    public void setProducto(Producto producto) { 
        this.producto = producto; 
    }
}