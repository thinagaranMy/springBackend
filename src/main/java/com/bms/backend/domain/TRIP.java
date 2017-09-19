package com.bms.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TRIP.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRIP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s_cheduledtime")
    private String sCheduledtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsCheduledtime() {
        return sCheduledtime;
    }

    public TRIP sCheduledtime(String sCheduledtime) {
        this.sCheduledtime = sCheduledtime;
        return this;
    }

    public void setsCheduledtime(String sCheduledtime) {
        this.sCheduledtime = sCheduledtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRIP tRIP = (TRIP) o;
        if (tRIP.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tRIP.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TRIP{" +
            "id=" + getId() +
            ", sCheduledtime='" + getsCheduledtime() + "'" +
            "}";
    }
}
