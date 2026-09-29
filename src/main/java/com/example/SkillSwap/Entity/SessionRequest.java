package com.example.SkillSwap.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "session_requests")
public class SessionRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "requester_id", nullable = false) private Member requester;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "provider_id", nullable = false) private Member provider;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "skill_offer_id", nullable = false) private SkillOffer skillOffer;
    @Column(name = "requested_hours", nullable = false) private Integer requestedHours;
    @Column(nullable = false) private String status = "PENDING";
    public SessionRequest() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Member getRequester() { return requester; } public void setRequester(Member requester) { this.requester = requester; }
    public Member getProvider() { return provider; } public void setProvider(Member provider) { this.provider = provider; }
    public SkillOffer getSkillOffer() { return skillOffer; } public void setSkillOffer(SkillOffer skillOffer) { this.skillOffer = skillOffer; }
    public Integer getRequestedHours() { return requestedHours; } public void setRequestedHours(Integer requestedHours) { this.requestedHours = requestedHours; }
    public Integer getHours() { return requestedHours; } public void setHours(Integer hours) { this.requestedHours = hours; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
}
