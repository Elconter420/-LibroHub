package edu.univalle.librohub.loan.service;

import edu.univalle.librohub.loan.entity.Loan;
import edu.univalle.librohub.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public Loan createLoan(Long userId, Long bookId) {
        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setBookId(bookId);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14)); // 2 weeks loan
        loan.setStatus("ACTIVE");
        return loanRepository.save(loan);
    }

    public Loan returnLoan(Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setReturnDate(LocalDateTime.now());
            loan.setStatus("RETURNED");
            // Here you could add fine calculation logic if returnDate > dueDate
            return loanRepository.save(loan);
        }
        throw new RuntimeException("Loan not found");
    }
}
