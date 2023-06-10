package com.ajou_nice.with_pet.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class Party extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    private String name;
    private String partyIsbn;
    private Integer memberCount;
    private Integer dogCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party")
    private List<UserParty> userPartyList = new ArrayList<>();

    public Party(User user) {
        this.user = user;
    }

    public void updateParty(String name, String isbn) {
        this.name = name;
        this.partyIsbn = isbn;
    }

    public void updatePartyIsbn(String isbn) {
        this.partyIsbn = isbn;
    }

    public static Party of(User user, String partyName) {
        return Party.builder()
                .user(user)
                .name(partyName)
                .memberCount(1)
                .dogCount(1)
                .build();
    }

    public void updatePartyLeader(User user) {
        this.user = user;
    }

    public void updateMemberCount(Integer memberCount){
        this.memberCount = memberCount;
    }

    public void updateDogCount(Integer dogCount){
        this.dogCount = dogCount;
    }

    public void addUserPartyForTest(UserParty userParty){
        this.userPartyList.add(userParty);
    }
}
