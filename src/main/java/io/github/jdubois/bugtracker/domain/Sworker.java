package io.github.jdubois.bugtracker.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Sworker.
 */
@Entity
@Table(name = "sworker")
public class Sworker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "height")
    private Integer height;

    @Column(name = "hair_color")
    private String hairColor;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "ethnicity")
    private String ethnicity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sworker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public Sworker nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Sworker birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getHeight() {
        return height;
    }

    public Sworker height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public Sworker hairColor(String hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public Sworker eyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        return this;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public Sworker ethnicity(String ethnicity) {
        this.ethnicity = ethnicity;
        return this;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sworker)) {
            return false;
        }
        return id != null && id.equals(((Sworker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sworker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", height=" + getHeight() +
            ", hairColor='" + getHairColor() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", ethnicity='" + getEthnicity() + "'" +
            "}";
    }
}
