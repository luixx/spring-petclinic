package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OwnerController {

    private final OwnerRepository owners;

    public OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }

    @GetMapping("/owners/find")
    public String initFindForm() {
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String processFindForm(@RequestParam(defaultValue = "1") int page, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "city", required = false) String city, Model model) {
        Pageable pageRequest = org.springframework.data.domain.PageRequest.of(page - 1, 10);

        List<Owner> ownerList = owners.findByLastNameContainingIgnoreCaseAndCityContainingIgnoreCase(lastName, city);

        model.addAttribute("ownerList", ownerList);
        return "owners/ownersList";
    }


}
