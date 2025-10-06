package tn.iit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.UpdateDepenseRequest;
import tn.iit.response.DepenseResponse;
import tn.iit.services.DepenseService;

@RestController
public class DepenseController {

    private static final Logger logger = LoggerFactory.getLogger(DepenseController.class);

    @Autowired
    private DepenseService depenseService;

   

	@PostMapping("/depense/create")
    public DepenseResponse createDepense(@RequestBody CreateDepenseRequest createDepenseRequest) {
        logger.info("Received request to create depense: {}", createDepenseRequest);
        DepenseResponse response = depenseService.createDepense(createDepenseRequest);
        logger.info("Created depense with ID: {}", response.getId());
        return response;
    }

    @GetMapping("/depense/getById/{id}")
    public DepenseResponse getById(@PathVariable Long id) {
        logger.info("Fetching depense with ID: {}", id);
        return depenseService.getDepenseById(id);
    }

    @GetMapping("/depense/getAll")
    public List<DepenseResponse> getAll() {
        logger.info("Fetching all depenses");
        return depenseService.getAllDepenses();
    }

    @PutMapping("/depense/update/{id}")
    public DepenseResponse updateDepense(@PathVariable Long id, @RequestBody UpdateDepenseRequest updateDepenseRequest) {
        logger.info("Updating depense with ID: {}", id);
        return depenseService.updateDepense(id, updateDepenseRequest);
    }

    @DeleteMapping("/depense/delete/{id}")
    public void deleteDepense(@PathVariable Long id) {
        logger.info("Deleting depense with ID: {}", id);
        depenseService.deleteDepense(id);
        logger.info("Depense with ID {} deleted successfully", id);
    }
}
