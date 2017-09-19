package com.bms.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BUS.
 */
@Entity
@Table(name = "bus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BUS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_apacity")
    private String cApacity;

    @Column(name = "r_egistration_number")
    private String rEgistrationNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcApacity() {
        return cApacity;
    }

    public BUS cApacity(String cApacity) {
        this.cApacity = cApacity;
        return this;
    }

    public void setcApacity(String cApacity) {
        this.cApacity = cApacity;
    }

    public String getrEgistrationNumber() {
        return rEgistrationNumber;
    }

    public BUS rEgistrationNumber(String rEgistrationNumber) {
        this.rEgistrationNumber = rEgistrationNumber;
        return this;
    }

    public void setrEgistrationNumber(String rEgistrationNumber) {
        this.rEgistrationNumber = rEgistrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BUS bUS = (BUS) o;
        if (bUS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bUS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BUS{" +
            "id=" + getId() +
            ", cApacity='" + getcApacity() + "'" +
            ", rEgistrationNumber='" + getrEgistrationNumber() + "'" +
            "}";
    }
}
