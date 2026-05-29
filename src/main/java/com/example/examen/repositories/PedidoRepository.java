package com.example.examen.repositories;

import com.example.examen.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Funcionalidad adicional obligatoria: Búsqueda por estado
    List<Pedido> findByEstado(String estado);
    
}