package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OwnerController {

    private final OwnerRepository owners;

    public OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Owner owner, Model model) {
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@ModelAttribute Owner owner, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in creating the owner.");
            return "owners/createOrUpdateOwnerForm";
        }

        this.owners.save(owner);
        redirectAttributes.addFlashAttribute("message", "New Owner Created");
        return "redirect:/owners/{id}";
    }

    @GetMapping("/owners")
    public String processFindForm(@RequestParam(defaultValue = "1") int page, @ModelAttribute Owner owner, BindingResult result) {
        String lastName = owner.getLastName();
        if (lastName == null) {
            lastName = ""; // empty string signifies broadest possible search
        }

        Pageable pageRequest = org.springframework.data.domain.PageRequest.of(page - 1, 10);
        List<Owner> ownersList = this.owners.findByLastNameStartingWith(lastName, pageRequest).getContent();

        return "owners/ownersList";
    }

    @GetMapping("/owners/{ownerId}")
    public String showOwner(@PathVariable("ownerId") int ownerId, Model model) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId));
        model.addAttribute("owner", owner);
        return "owners/ownerDetails";
    }

    @GetMapping("/owners/find")
    public String initFindForm() {
        return "owners/findOwners";
    }

    @GetMapping("/owners/find/city")
    public String findByCity(@RequestParam("city") String city, @RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageRequest = org.springframework.data.domain.PageRequest.of(page - 1, 10);
        List<Owner> ownersList = this.owners.findByCityStartingWith(city, pageRequest).getContent();
        model.addAttribute("ownersList", ownersList);
        model.addAttribute("city", city);
        return "owners/ownersList";
    }

}
