package com.codegym.controller;

import com.codegym.model.Smartphone;
import com.codegym.service.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;

@Controller
@RequestMapping(value = "/smartphones")
public class SmartphoneController {

    @Autowired
    private SmartphoneService smartphoneService;

    @GetMapping("")
    public ModelAndView allPhonesPage() {
        ModelAndView modelAndView = new ModelAndView("phones/all-phones");

        modelAndView.addObject("allphones", allPhones());
        return modelAndView;
    }

    @RequestMapping(value = "", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<Smartphone> allPhones() {
        return smartphoneService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createSmartphonePage() {
        ModelAndView mav = new ModelAndView("phones/new-phone");
        mav.addObject("sPhone", new Smartphone());
        return mav;
    }

    @RequestMapping(value = "/createnewPhone", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Smartphone createSmartphone(@RequestBody Smartphone smartphone) {
        return smartphoneService.save(smartphone);
    }



    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Smartphone deleteSmartphone(@PathVariable Integer id) {
        return smartphoneService.remove(id);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editSmartphonePage(@PathVariable Integer id){
        Smartphone smartphone = smartphoneService.findById(id);
        if(smartphone != null) {
            ModelAndView modelAndView = new ModelAndView("phones/edit-phone");
            modelAndView.addObject("sPhone",smartphone);
            return modelAndView;
        }
        return null;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Smartphone editSmartphone(@PathVariable Integer id, @RequestBody Smartphone smartphone){
        smartphone.setId(id);
        return smartphoneService.save(smartphone);
    }

}
