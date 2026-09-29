package com.example.SkillSwap.Repository;
import com.example.SkillSwap.Entity.CreditLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CreditLedgerRepository extends JpaRepository<CreditLedger, Long> {
    List<CreditLedger> findByMemberId(Long memberId);
}
