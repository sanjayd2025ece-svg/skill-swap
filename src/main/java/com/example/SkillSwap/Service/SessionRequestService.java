package com.example.SkillSwap.Service;

import com.example.SkillSwap.Entity.*;
import com.example.SkillSwap.Exception.InsufficientCreditException;
import com.example.SkillSwap.Exception.ResourceNotFoundException;
import com.example.SkillSwap.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SessionRequestService {
    private final SessionRequestRepository requests; private final MemberRepository members; private final SkillOfferRepository skills; private final CreditLedgerRepository ledger;
    public SessionRequestService(SessionRequestRepository r, MemberRepository m, SkillOfferRepository s, CreditLedgerRepository l) { requests=r; members=m; skills=s; ledger=l; }
    public List<SessionRequest> all() { return requests.findAll(); }
    public SessionRequest get(Long id) { return requests.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session request not found")); }
    @Transactional public SessionRequest create(Long requesterId, Long providerId, Long skillOfferId, Integer hours) {
        if(hours==null || hours<=0) throw new IllegalArgumentException("Requested hours must be greater than zero");
        Member requester=members.findById(requesterId).orElseThrow(() -> new ResourceNotFoundException("Requester not found"));
        Member provider=members.findById(providerId).orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        SkillOffer skill=skills.findById(skillOfferId).orElseThrow(() -> new ResourceNotFoundException("Skill offer not found"));
        if(requester.getId().equals(provider.getId()) || !skill.getProvider().getId().equals(provider.getId())) throw new IllegalArgumentException("Choose another member's skill offer");
        if(requester.getCreditBalance()<hours) throw new InsufficientCreditException("Requester does not have enough credits");
        SessionRequest request=new SessionRequest(); request.setRequester(requester); request.setProvider(provider); request.setSkillOffer(skill); request.setRequestedHours(hours); request.setStatus("PENDING"); return requests.save(request);
    }
    @Transactional public SessionRequest confirm(Long id) { SessionRequest r=get(id); if(!r.getStatus().equals("PENDING")) throw new IllegalArgumentException("Only pending requests can be confirmed"); r.setStatus("CONFIRMED"); return requests.save(r); }
    @Transactional public SessionRequest reject(Long id) { SessionRequest r=get(id); if(!r.getStatus().equals("PENDING")) throw new IllegalArgumentException("Only pending requests can be rejected"); r.setStatus("REJECTED"); return requests.save(r); }
    @Transactional public SessionRequest complete(Long id) {
        SessionRequest r=get(id); if(!r.getStatus().equals("CONFIRMED")) throw new IllegalArgumentException("Only confirmed requests can be completed");
        Long requesterId=r.getRequester().getId(), providerId=r.getProvider().getId();
        Long firstId=requesterId.compareTo(providerId)<0?requesterId:providerId, secondId=requesterId.compareTo(providerId)<0?providerId:requesterId;
        Member first=members.findByIdForUpdate(firstId).orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Member second=members.findByIdForUpdate(secondId).orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Member from=requesterId.equals(firstId)?first:second, to=providerId.equals(firstId)?first:second; int hours=r.getRequestedHours();
        if(from.getCreditBalance()<hours) throw new InsufficientCreditException("Requester does not have enough credits");
        from.setCreditBalance(from.getCreditBalance()-hours); to.setCreditBalance(to.getCreditBalance()+hours); members.save(from); members.save(to);
        ledger.save(new CreditLedger(from,-hours)); ledger.save(new CreditLedger(to,hours)); r.setStatus("COMPLETED"); return requests.save(r);
    }
}
