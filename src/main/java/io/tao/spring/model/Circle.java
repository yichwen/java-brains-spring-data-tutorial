package io.tao.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class Circle {

    @Id
    private int id;
    private String name;
}
