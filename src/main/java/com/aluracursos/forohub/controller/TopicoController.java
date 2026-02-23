package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }
        Topico topicoGuardado = topicoRepository.save(new Topico(datosRegistroTopico));
        return ResponseEntity.ok(topicoGuardado);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        }
        return ResponseEntity.notFound().build();
    }

    // EL MÉTODO PUT PARA ACTUALIZAR (El que nos faltaba para que no tire 405)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();

            // Verificamos regla de negocio de duplicados
            if (topicoRepository.existsByTituloAndMensajeAndIdNot(datosActualizarTopico.titulo(), datosActualizarTopico.mensaje(), id)) {
                return ResponseEntity.badRequest().body("Ya existe otro tópico con el mismo título y mensaje.");
            }

            topico.actualizarDatos(datosActualizarTopico);
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        }

        return ResponseEntity.notFound().build();
    }

    // NUEVO MÉTODO DELETE PARA ELIMINAR UN TÓPICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            // Si el tópico existe, lo eliminamos de la base de datos
            topicoRepository.deleteById(id);

            // El estándar REST para una eliminación exitosa es devolver 204 No Content
            return ResponseEntity.noContent().build();
        }

        // Si no existe el ID, devolvemos un 404 Not Found
        return ResponseEntity.notFound().build();
    }
}