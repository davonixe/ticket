package com.ennov.tickets.model;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ennov.tickets.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Ticket extends Auditable<String> {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("user_id")
    private User user;
}
