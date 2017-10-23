package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.CourseType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "courses")
public class Course extends MenuItem {

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseType type;

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }
}
