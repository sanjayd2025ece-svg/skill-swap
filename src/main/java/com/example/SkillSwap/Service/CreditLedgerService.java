package com.example.SkillSwap.Service;
import com.example.SkillSwap.Entity.CreditLedger;
import com.example.SkillSwap.Repository.CreditLedgerRepository;
import com.example.SkillSwap.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CreditLedgerService {
    private final CreditLedgerRepository repo;
    public CreditLedgerService(CreditLedgerRepository repo) { this.repo=repo; }
    public List<CreditLedger> all() { return repo.findAll(); }
    public CreditLedger get(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Credit transaction not found")); }
    public List<CreditLedger> byMember(Long id) { return repo.findByMemberId(id); }
}
