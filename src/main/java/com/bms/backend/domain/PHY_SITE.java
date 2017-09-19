package com.bms.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PHY_SITE.
 */
@Entity
@Table(name = "phy_site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PHY_SITE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "a_ddress_line_1")
    private String aDdressLine1;

    @Column(name = "a_ddress_line_2")
    private String aDdressLine2;

    @Column(name = "a_ddress_line_3")
    private String aDdressLine3;

    @Column(name = "p_ostcode")
    private String pOstcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getaDdressLine1() {
        return aDdressLine1;
    }

    public PHY_SITE aDdressLine1(String aDdressLine1) {
        this.aDdressLine1 = aDdressLine1;
        return this;
    }

    public void setaDdressLine1(String aDdressLine1) {
        this.aDdressLine1 = aDdressLine1;
    }

    public String getaDdressLine2() {
        return aDdressLine2;
    }

    public PHY_SITE aDdressLine2(String aDdressLine2) {
        this.aDdressLine2 = aDdressLine2;
        return this;
    }

    public void setaDdressLine2(String aDdressLine2) {
        this.aDdressLine2 = aDdressLine2;
    }

    public String getaDdressLine3() {
        return aDdressLine3;
    }

    public PHY_SITE aDdressLine3(String aDdressLine3) {
        this.aDdressLine3 = aDdressLine3;
        return this;
    }

    public void setaDdressLine3(String aDdressLine3) {
        this.aDdressLine3 = aDdressLine3;
    }

    public String getpOstcode() {
        return pOstcode;
    }

    public PHY_SITE pOstcode(String pOstcode) {
        this.pOstcode = pOstcode;
        return this;
    }

    public void setpOstcode(String pOstcode) {
        this.pOstcode = pOstcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PHY_SITE pHY_SITE = (PHY_SITE) o;
        if (pHY_SITE.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pHY_SITE.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PHY_SITE{" +
            "id=" + getId() +
            ", aDdressLine1='" + getaDdressLine1() + "'" +
            ", aDdressLine2='" + getaDdressLine2() + "'" +
            ", aDdressLine3='" + getaDdressLine3() + "'" +
            ", pOstcode='" + getpOstcode() + "'" +
            "}";
    }
}
