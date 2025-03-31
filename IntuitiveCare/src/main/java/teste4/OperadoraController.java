package teste4;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operadoras")
public class OperadoraController {

    private final OperadoraRepository repository;

    
    public OperadoraController(OperadoraRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Operadora>> buscar(
        @RequestParam("termo") String termoBusca) {
        return ResponseEntity.ok(repository.buscarPorTexto(termoBusca));
    }
}