package com.eshoppers.domain;

import java.time.LocalDateTime;

public abstract class Domain {
    private Long id;
    private Long version;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateLastUpdated = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime created) {
        this.dateCreated = created;
    }

    public LocalDateTime getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(LocalDateTime lastUpdated) {
        this.dateLastUpdated = lastUpdated;
    }
}
