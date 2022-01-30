package com.opros.Site_oprosnik.models;

import javax.persistence.*;
import java.awt.*;

@Entity//сущность для работы с таблице базы данных
public class Opros
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//автоматически увеличивает id
    private Long id;

    private String question;
    private String question_text;
    private String answer;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id=id;
    }


    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question=question;
    }


    public String getQuestion_text()
    {
        return question_text;
    }

    public void setQuestion_text(String question_text)
    {
        this.question_text=question_text;
    }


    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer=answer;
    }


    public Opros(){};

    public Opros(String question, String question_text)
    {
        this.question=question;
        this.question_text=question_text;
    }
}

