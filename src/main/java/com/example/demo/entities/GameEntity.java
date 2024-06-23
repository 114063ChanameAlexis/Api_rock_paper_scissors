package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;
    @Column
    private String name;

    //Todo: @Lob sirve para indicar a la base de datos que es un objecto grande
    @Lob
    @Column
    private String description;
    @Lob
    @Column
    private String rules;

}
