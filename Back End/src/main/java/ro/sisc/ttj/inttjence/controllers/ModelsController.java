package ro.sisc.ttj.inttjence.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sisc.ttj.inttjence.services.ModelsServices;

import java.util.List;

@RestController
@RequestMapping("/models")
@AllArgsConstructor
@CrossOrigin("*")
public class ModelsController {
    private ModelsServices modelsServices;

    @GetMapping
    public ResponseEntity<List<String>> getMessages() {
        List<String> models = modelsServices.getAllModels();
        return ResponseEntity.ok(models);
    }
}
