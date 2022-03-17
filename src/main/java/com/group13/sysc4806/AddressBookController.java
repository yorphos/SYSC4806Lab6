package com.group13.sysc4806;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Service
@RequestMapping("/")
public class AddressBookController {

    private static final String FEATURE_IS_BETA = "isBeta";

    private final AddressBookRepository repo;

    @Autowired
    public AddressBookController(AddressBookRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public FF4j ff4j;

    @PostConstruct
    public void populateFeatures() {
        if (!ff4j.exist(FEATURE_IS_BETA)) {
            ff4j.createFeature(new Feature(FEATURE_IS_BETA, false));
        }
    }

    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("root");
        modelAndView.addObject("isBeta", ff4j.check(FEATURE_IS_BETA));

        return modelAndView;
    }

    @GetMapping("/traditional")
    public ModelAndView traditional() {
        List<AddressBook> addressBooks = this.repo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional");
        modelAndView.addObject("addressBooks", addressBooks);
        return modelAndView;
    }

    @GetMapping("/spa")
    public ModelAndView spa() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("spa");
        return modelAndView;
    }

    @GetMapping("/createbook")
    public ModelAndView traditionalCreateBook() {
        createBook();
        List<AddressBook> addressBooks = this.repo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional");
        modelAndView.addObject("addressBooks", addressBooks);
        return modelAndView;
    }

    @PostMapping("/createbook")
    public AddressBook createBook() {
        return this.repo.save(new AddressBook());
    }

    @GetMapping("/viewbook")
    public ModelAndView traditionalView(@RequestParam(name = "bookId") long bookId) {
        List<BuddyInfo> buddies = this.repo.getById(bookId).getBuddies();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional-viewbook");
        modelAndView.addObject("buddies", buddies);
        modelAndView.addObject("bookId", bookId);
        return modelAndView;
    }

    @GetMapping("/addbuddy")
    public ModelAndView traditionalAddBuddy(@RequestParam(name = "bookId") long bookId,
                                            @RequestParam(name = "name") String name,
                                            @RequestParam(name = "phoneNumber") String phoneNumber) {
        AddressBook book = this.repo.findById(bookId);
        book.addBuddy(new BuddyInfo(name, phoneNumber));
        this.repo.save(book);

        List<BuddyInfo> buddies = book.getBuddies();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional-viewbook");
        modelAndView.addObject("buddies", buddies);
        modelAndView.addObject("bookId", bookId);
        return modelAndView;
    }

    @PostMapping("/addbuddy")
    public AddressBook addBuddy(@RequestParam(name = "bookId") long bookId, @RequestBody BuddyInfo buddy) {
        AddressBook book = this.repo.findById(bookId);
        book.addBuddy(buddy);
        return this.repo.save(book);
    }

    @GetMapping("/deletebuddy")
    public ModelAndView traditionalAddBuddy(@RequestParam(name = "bookId") long bookId,
                                            @RequestParam(name = "buddyId") long buddyId) {
        AddressBook book = this.repo.findById(bookId);
        book.removeBuddy(book.getBuddy(buddyId));
        this.repo.save(book);

        List<BuddyInfo> buddies = book.getBuddies();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional-viewbook");
        modelAndView.addObject("buddies", buddies);
        modelAndView.addObject("bookId", bookId);
        return modelAndView;
    }

    @PostMapping("/deletebuddy")
    public AddressBook removeBuddy(@RequestParam(name = "bookId") long bookId, @RequestBody BuddyInfo buddy) {
        AddressBook book = this.repo.findById(bookId);
        book.removeBuddy(buddy);
        return this.repo.save(book);
    }

    @GetMapping("/deletebook")
    public ModelAndView traditionalRemoveBook(@RequestParam(name = "bookId") long bookId) {
        this.repo.deleteById(bookId);

        List<AddressBook> addressBooks = this.repo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("traditional");
        modelAndView.addObject("addressBooks", addressBooks);
        return modelAndView;
    }

    @PostMapping("/deletebook")
    public void removeBook(@RequestParam(name = "bookId") long bookId) {
        this.repo.deleteById(bookId);
    }

    @GetMapping("/getbuddies")
    public List<BuddyInfo> getBuddies(@RequestParam(name = "bookId") long bookId) {
        return this.repo.findById(bookId).getBuddies();
    }

    @GetMapping("/getbooks")
    public List<AddressBook> getAddressBooks() {
        return this.repo.findAll();
    }
}

