package com.example.SkillSwap.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_ledger")
public class CreditLedger {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id", nullable = false) private Member member;
    @Column(nullable = false) private Integer amount;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @PrePersist void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public CreditLedger() {}
    public CreditLedger(Member member, Integer amount) { this.member = member; this.amount = amount; }
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; } public void setMember(Member member) { this.member = member; }
    public Integer getAmount() { return amount; } public void setAmount(Integer amount) { this.amount = amount; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
