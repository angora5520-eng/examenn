package com.example.examen.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaPedido;
    
    // Puede ser: "PENDIENTE", "EN_PREPARACION", "ENTREGADO"
    private String estado; 
    
    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    // Constructor vacío requerido por JPA
    public Pedido() {
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public LocalDateTime getFechaPedido() { 
        return fechaPedido; 
    }
    
    public void setFechaPedido(LocalDateTime fechaPedido) { 
        this.fechaPedido = fechaPedido; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
    
    public Double getTotal() { 
        return total; 
    }
    
    public void setTotal(Double total) { 
        this.total = total; 
    }
    
    public Cliente getCliente() { 
        return cliente; 
    }
    
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }
    
    public List<DetallePedido> getDetalles() { 
        return detalles; 
    }
    
    public void setDetalles(List<DetallePedido> detalles) { 
        this.detalles = detalles; 
    }
}