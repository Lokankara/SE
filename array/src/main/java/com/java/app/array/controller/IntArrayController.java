package com.java.app.array.controller;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.request.CreateRequest;
import com.java.app.array.entity.request.UpdateRequest;
import com.java.app.array.service.ArrayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/api/ints")
public class IntArrayController {

    private final ArrayService service;

    public IntArrayController(ArrayService service) {
        this.service = Objects.requireNonNull(service);
    }

    @PostMapping
    public ResponseEntity<IntArrayEntity> create(@RequestBody CreateRequest<Integer> request) {
        service.createArray(request.getName(), request.getArray());
        List<IntArrayEntity> found = service.searchArrays(e -> Objects.equals(e.getName(), request.getName()));
        return found.isEmpty() ? ResponseEntity.status(201).build() : ResponseEntity.status(201).body(found.get(0));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("name") String name) {
        List<IntArrayEntity> found = service.searchArrays(e -> Objects.equals(e.getName(), name));
        found.forEach(e -> service.deleteArray(e.getId()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IntArrayEntity>> findAll() {
        return ResponseEntity.ok(service.searchArrays(e -> true));
    }

    @GetMapping("/search")
    public ResponseEntity<List<IntArrayEntity>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.searchArrays(e -> Objects.equals(e.getName(), name)));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<IntArrayEntity>> sort(@RequestParam(value = "by", defaultValue = "ID") String by) {
        Comparator<IntArrayEntity> comparator = switch (by.toUpperCase(Locale.ROOT)) {
            case "NAME" -> Comparator.comparing(IntArrayEntity::getName, Comparator.nullsFirst(String::compareTo));
            case "FIRST" -> Comparator.comparingInt(e -> e.isEmpty() ? Integer.MIN_VALUE : e.getFirst());
            case "LENGTH" -> Comparator.comparingInt(IntArrayEntity::getLength);
            default -> Comparator.comparingInt(IntArrayEntity::getId);
        };
        return ResponseEntity.ok(service.sort(comparator));
    }

    @PutMapping("/elements")
    public ResponseEntity<Void> updateElement(@RequestParam("name") String name,
            @RequestParam("index") int index,
            @RequestBody UpdateRequest<IntArrayEntity> request) {
        List<IntArrayEntity> found = service.searchArrays(e -> Objects.equals(e.getName(), name));
        if (found.isEmpty())
            return ResponseEntity.notFound().build();
        IntArrayEntity entity = found.getFirst();
        entity.setArray(index, request.getValue().getFirst());
        return ResponseEntity.noContent().build();
    }
}
