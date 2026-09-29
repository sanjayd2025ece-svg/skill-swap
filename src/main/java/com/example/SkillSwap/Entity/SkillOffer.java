package com.example.SkillSwap.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_offers")
public class SkillOffer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String skillName;
    @Column(length = 2000) private String description;
    private String category;
    private Integer hoursPerSession;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "provider_id", nullable = false) private Member provider;
    public SkillOffer() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getSkillName() { return skillName; } public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; } public void setCategory(String category) { this.category = category; }
    public Integer getHoursPerSession() { return hoursPerSession; } public void setHoursPerSession(Integer hoursPerSession) { this.hoursPerSession = hoursPerSession; }
    public Member getProvider() { return provider; } public void setProvider(Member provider) { this.provider = provider; }
}
