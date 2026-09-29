package com.example.SkillSwap.Service;
import com.example.SkillSwap.Entity.Member;
import com.example.SkillSwap.Repository.MemberRepository;
import com.example.SkillSwap.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MemberService {
    private final MemberRepository repo;
    public MemberService(MemberRepository repo) { this.repo = repo; }
    public List<Member> all() { return repo.findAll(); }
    public Member get(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found")); }
    public Member save(Member m) { if (m.getName()==null || m.getName().isBlank() || m.getEmail()==null || m.getEmail().isBlank()) throw new IllegalArgumentException("Name and email are required"); if (m.getCreditBalance()==null) m.setCreditBalance(0); if (m.getCreditBalance()<0) throw new IllegalArgumentException("Credit balance cannot be negative"); return repo.save(m); }
    public Member update(Long id, Member input) { Member m=get(id); m.setName(input.getName()); m.setEmail(input.getEmail()); m.setPhone(input.getPhone()); if(input.getCreditBalance()!=null) { if(input.getCreditBalance()<0) throw new IllegalArgumentException("Credit balance cannot be negative"); m.setCreditBalance(input.getCreditBalance()); } return save(m); }
    public void delete(Long id) { repo.delete(get(id)); }
}
