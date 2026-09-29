package com.example.SkillSwap.Service;
import com.example.SkillSwap.Entity.*;
import com.example.SkillSwap.Repository.*;
import com.example.SkillSwap.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SkillOfferService {
    private final SkillOfferRepository skills; private final MemberRepository members;
    public SkillOfferService(SkillOfferRepository skills, MemberRepository members) { this.skills=skills; this.members=members; }
    public List<SkillOffer> all() { return skills.findAll(); }
    public SkillOffer get(Long id) { return skills.findById(id).orElseThrow(() -> new ResourceNotFoundException("Skill offer not found")); }
    public SkillOffer create(Long memberId, SkillOffer input) { Member provider=members.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member not found")); if(input.getSkillName()==null || input.getSkillName().isBlank()) throw new IllegalArgumentException("Skill name is required"); if(input.getHoursPerSession()==null || input.getHoursPerSession()<=0) throw new IllegalArgumentException("Hours per session must be greater than zero"); input.setId(null); input.setProvider(provider); return skills.save(input); }
    public void delete(Long id) { skills.delete(get(id)); }
}
