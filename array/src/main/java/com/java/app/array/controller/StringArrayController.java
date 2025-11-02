package com.java.app.array.controller;

import com.java.app.array.comparator.StringArrayComparator;
import com.java.app.array.entity.request.CreateRequest;
import com.java.app.array.entity.string.StringArrayEntity;
import com.java.app.array.entity.string.StringArrayStatistics;
import com.java.app.array.entity.string.StringWarehouse;
import com.java.app.array.service.StringArrayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/array/string")
public class StringArrayController {

    private final StringArrayService service;

    public StringArrayController(StringArrayService service) {
        this.service = Objects.requireNonNull(service);
    }

    @PostMapping
    public ResponseEntity<StringArrayStatistics> create(@RequestBody CreateRequest<String> request) {
        Integer id = service.createArray(request.getName(), request.getArray());
        StringArrayStatistics statistics = StringWarehouse.getInstance().getStatistics(id);
        return ResponseEntity.status(201).body(statistics);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("name") String name) {
        List<StringArrayEntity> found = service.findByName(name);
        found.forEach(e -> service.deleteArray(e.getId()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StringArrayEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<StringArrayEntity>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<StringArrayEntity>> sort(@RequestParam(value = "by", defaultValue = "NAME") String by) {
        StringArrayComparator comparator = StringArrayComparator.NAME;
        return ResponseEntity.ok(service.sortArrays(comparator));
    }
}
