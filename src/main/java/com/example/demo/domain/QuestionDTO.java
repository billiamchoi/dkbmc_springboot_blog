package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="question")
public class QuestionDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    //What is GeneratedValue
    private Long id;

    private String content;

    private String subject;

    private Date create_date;

    private Date modify_date;
}
