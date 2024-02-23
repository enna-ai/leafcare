package com.enna_ai.firefly.model;

import com.enna_ai.firefly.enums.TicketCategory;
import com.enna_ai.firefly.enums.TicketPriority;
import com.enna_ai.firefly.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "ticket_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private TicketCategory category;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastUpdatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

    public Ticket() {
    }

    public Ticket(String subject, String description, TicketPriority priority, TicketCategory category, TicketStatus status) {
        this.subject = subject;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
