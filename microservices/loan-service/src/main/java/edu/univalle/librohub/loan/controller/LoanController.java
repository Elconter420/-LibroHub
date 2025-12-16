package edu.univalle.librohub.loan.controller;

import edu.univalle.librohub.loan.entity.Loan;
import edu.univalle.librohub.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUser(@PathVariable Long userId) {
        return loanService.getLoansByUserId(userId);
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Long bookId = Long.valueOf(body.get("bookId").toString());
        Loan loan = loanService.createLoan(userId, bookId);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.returnLoan(id);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
