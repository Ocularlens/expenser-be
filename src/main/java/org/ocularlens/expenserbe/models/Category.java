package org.ocularlens.expenserbe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.ocularlens.expenserbe.common.TransactionType;

import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private boolean createdByAdmin;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Category() {}

    public Category(TransactionType type, String name, boolean createdByAdmin, User user) {
        this.type = type;
        this.name = name;
        this.createdByAdmin = createdByAdmin;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(boolean createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
