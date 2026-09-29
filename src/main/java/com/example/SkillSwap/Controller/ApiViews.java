package com.example.SkillSwap.Controller;
import com.example.SkillSwap.Entity.*;
import java.time.LocalDateTime;
final class ApiViews {
    private ApiViews() {}
    record MemberView(Long id, String name, String email, String phone, Integer creditBalance) { static MemberView of(Member m) { return new MemberView(m.getId(),m.getName(),m.getEmail(),m.getPhone(),m.getCreditBalance()); } }
    record SkillView(Long id, String skillName, String description, String category, Integer hoursPerSession, MemberView provider) { static SkillView of(SkillOffer s) { return new SkillView(s.getId(),s.getSkillName(),s.getDescription(),s.getCategory(),s.getHoursPerSession(),MemberView.of(s.getProvider())); } }
    record RequestView(Long id, MemberView requester, MemberView provider, SkillView skillOffer, Integer requestedHours, String status) { static RequestView of(SessionRequest r) { return new RequestView(r.getId(),MemberView.of(r.getRequester()),MemberView.of(r.getProvider()),SkillView.of(r.getSkillOffer()),r.getRequestedHours(),r.getStatus()); } }
    record LedgerView(Long id, MemberView member, Integer amount, LocalDateTime createdAt) { static LedgerView of(CreditLedger l) { return new LedgerView(l.getId(),MemberView.of(l.getMember()),l.getAmount(),l.getCreatedAt()); } }
}
