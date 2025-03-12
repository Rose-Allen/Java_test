package kz.dimash.crud.controller;

import jakarta.validation.Valid;
import kz.dimash.crud.dao.PersonDAO;
import kz.dimash.crud.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    PersonDAO personDAO;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("persons", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
        model.addAttribute("person", personDAO.getPersonById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person()); // Создаем пустой объект Person
        return "people/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("person")  @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("person", personDAO.getPersonById(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute @Valid Person person, BindingResult bindingResult, @PathVariable Integer id){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        personDAO.delete(id);
        return "redirect:/people";
    }


}
