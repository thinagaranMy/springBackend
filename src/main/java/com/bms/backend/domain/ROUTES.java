package com.bms.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ROUTES.
 */
@Entity
@Table(name = "routes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ROUTES implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_ode")
    private String cOde;

    @Column(name = "d_escription")
    private String dEscription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcOde() {
        return cOde;
    }

    public ROUTES cOde(String cOde) {
        this.cOde = cOde;
        return this;
    }

    public void setcOde(String cOde) {
        this.cOde = cOde;
    }

    public String getdEscription() {
        return dEscription;
    }

    public ROUTES dEscription(String dEscription) {
        this.dEscription = dEscription;
        return this;
    }

    public void setdEscription(String dEscription) {
        this.dEscription = dEscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ROUTES rOUTES = (ROUTES) o;
        if (rOUTES.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rOUTES.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ROUTES{" +
            "id=" + getId() +
            ", cOde='" + getcOde() + "'" +
            ", dEscription='" + getdEscription() + "'" +
            "}";
    }
}
