package com.bluedot.infrastructure.repository.data_object;

import javax.persistence.*;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:24
 */
@Entity
@Table(name = "buffer_solution")
public class BufferSolution {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;

    public BufferSolution() {
    }

    public BufferSolution(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BufferSolution{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
