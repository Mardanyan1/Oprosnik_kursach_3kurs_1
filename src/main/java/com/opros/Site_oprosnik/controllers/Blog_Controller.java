package com.opros.Site_oprosnik.controllers;

import com.opros.Site_oprosnik.models.Opros;
import com.opros.Site_oprosnik.repo.Opros_reopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class Blog_Controller
{
    @Autowired
    private Opros_reopsitory oprosReopsitory;

    @GetMapping("/")
    public String home(Model model)
    {
        Iterable<Opros> opros= oprosReopsitory.findAll();
        model.addAttribute("oproses", opros);
        return "main";
    }




    @GetMapping("/blog")//раздел редактирования
    public String blogMain(Model model)
    {
        Iterable<Opros> opros= oprosReopsitory.findAll();
        model.addAttribute("oproses", opros);
        return "blog";
    }

    /*@PostMapping("/blog")//этот метод скорее всего не нужен
    public String blogAnswer(long id, @RequestParam String answer)
    {

        return "blog";
    }*/


//////////////////////////////////////////////добавление вопроса
    @GetMapping("/blog/add")//добавление вопроса
    public String oprosAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")//добавление вопроса и последующее сохранение
    public String oprosAddSave(@RequestParam String question, @RequestParam String question_text)
    {
        Opros opros = new Opros(question,question_text);
        oprosReopsitory.save(opros);
        return "blog-add";
    }


//////////////////////////////////////////////
    @GetMapping("/blog/{id}")//раздел редактирования, только зачем-то по отдельной ссылке(видимо можно убрать)
    public String oprosNum(@PathVariable(value = "id") long id, Model model)
    {
        if(!oprosReopsitory.existsById(id))
        {
            return "redirect:/blog";
        }
        Optional<Opros> opros = oprosReopsitory.findById(id);
        ArrayList<Opros> res = new ArrayList<>();
        opros.ifPresent(res::add);
        model.addAttribute("oproses",res);
        return "blog-details";
    }


//////////////////////////////////////////////редактирование вопросов

    @GetMapping("/blog/{id}/edit")//раздел редактирования
    public String blogEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!oprosReopsitory.existsById(id))
        {
            return "redirect:/blog";
        }
        Optional<Opros> opros = oprosReopsitory.findById(id);
        ArrayList<Opros> res = new ArrayList<>();
        opros.ifPresent(res::add);
        model.addAttribute("oproses",res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")//раздел редактирования, где происходит само редактирование
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String question, @RequestParam String question_text)
    {
        Opros opros = oprosReopsitory.findById(id).orElseThrow();
        opros.setQuestion(question);
        opros.setQuestion_text(question_text);
        oprosReopsitory.save(opros);
        return "redirect:/blog";
    }


    @PostMapping("/blog/{id}/remove")//раздел удаления, где происходит само удаление
    public String blogPostDelete(@PathVariable(value = "id") long id)
    {
        Opros opros = oprosReopsitory.findById(id).orElseThrow();
        oprosReopsitory.delete(opros);
        return "redirect:/blog";
    }

///////////////////////////////////////вывод ответов

    @GetMapping("/answers")//раздел ответов, где просто выводятся  ответы
    public String oprosAnswer(Model model)
    {
        Iterable<Opros> opros = oprosReopsitory.findAll();
        model.addAttribute("oproses",opros);
        return "answers";
    }



////////////////////////////////////////


    @GetMapping("/question/{id}")//раздел ответа
    public String questionAsk(@PathVariable(value = "id") long id, Model model)
    {
        if(!oprosReopsitory.existsById(id))
        {
            return "redirect:/main";
        }
        Optional<Opros> opros = oprosReopsitory.findById(id);
        ArrayList<Opros> res = new ArrayList<>();
        opros.ifPresent(res::add);
        model.addAttribute("oproses",res);
        return "question";
    }

    @PostMapping("/question/{id}")//раздел ответа, где пишешь ответ
    public String questionAskSave(@PathVariable(value = "id") long id, @RequestParam String answer)
    {
        Opros opros = oprosReopsitory.findById(id).orElseThrow();
        opros.setAnswer(answer);
        oprosReopsitory.save(opros);
        return "redirect:/";
    }
}
