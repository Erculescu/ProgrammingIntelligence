package ro.sisc.ttj.inttjence.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sisc.ttj.inttjence.repository.ModelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelsServices {
    private ModelRepository modelRepository;

    public List<String> getAllModels() {
        return modelRepository.findAll().stream()
                .map(m -> m.getName())
                .toList();
    }
}
