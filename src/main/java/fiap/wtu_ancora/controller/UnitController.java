package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.Unit;
import fiap.wtu_ancora.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping("/get-all")
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    @PostMapping("/create")
    public Unit createUnit(@RequestBody Unit unit) {
        return unitRepository.save(unit);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Unit> editUnit(@PathVariable Long id, @RequestBody Unit unitDatails) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if(unitOptional.isPresent()){
            Unit unit = unitOptional.get();
            unit.setName(unitDatails.getName());
            unit.setEndereco(unitDatails.getEndereco());
            unit.setFranchised(unitDatails.isFranchised());
            final Unit updatedUnit = unitRepository.save(unit);
            return ResponseEntity.ok(updatedUnit);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUnit(@PathVariable Long id) {
        try{
            unitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
