package com.example.Enotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseModel {
    @Column(name = "isactive")
    private Boolean isActive;
    @Column(name = "isdeleted")
    private Boolean isDeleted;

    private Integer createdBy;
    @Column(name = "created_on" , insertable = false,updatable = false)
    private Date createdOn;
    private Integer updatedBy;
    @Column(name = "updated_on", insertable = false,updatable = false)
    private Date updatedOn;
}
