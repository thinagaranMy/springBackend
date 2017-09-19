package com.bms.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A INCIDENT_REPORT.
 */
@Entity
@Table(name = "incident_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class INCIDENT_REPORT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        INCIDENT_REPORT iNCIDENT_REPORT = (INCIDENT_REPORT) o;
        if (iNCIDENT_REPORT.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iNCIDENT_REPORT.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "INCIDENT_REPORT{" +
            "id=" + getId() +
            "}";
    }
}
