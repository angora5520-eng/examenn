package com.example.examen.controllers;

import com.example.examen.models.Pedido;
import com.example.examen.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    // 1. Listar registros [cite: 32]
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // 2. Buscar registros por ID [cite: 33]
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Crear registros [cite: 31] + Cálculo de totales 
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        // Funcionalidad adicional: Cálculo automático del total
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            double totalCalculado = 0.0;
            for (var detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido); // Relacionar bidireccionalmente
                totalCalculado += detalle.getSubtotal();
            }
            pedido.setTotal(totalCalculado);
        }
        
        // Forzar estado inicial si viene nulo
        if (pedido.getEstado() == null) {
            pedido.setEstado("PENDIENTE");
        }

        Pedido nuevoPedido = pedidoRepository.save(pedido);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    // 4. Actualizar registros [cite: 34]
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id);
        
        if (pedidoExistente.isPresent()) {
            Pedido pedido = pedidoExistente.get();
            // Actualizamos los campos permitidos (ej. estado)
            pedido.setEstado(pedidoActualizado.getEstado());
            
            Pedido pedidoGuardado = pedidoRepository.save(pedido);
            return new ResponseEntity<>(pedidoGuardado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar registros [cite: 35]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id);
        if (pedidoExistente.isPresent()) {
            pedidoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // 6. FUNCIONALIDAD ADICIONAL: Búsqueda por estado 
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}