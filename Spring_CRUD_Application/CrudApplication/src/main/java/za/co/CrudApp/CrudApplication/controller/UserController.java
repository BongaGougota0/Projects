package za.co.CrudApp.CrudApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.CrudApp.CrudApplication.model.Contact;
import za.co.CrudApp.CrudApplication.service.UserService;
import za.co.CrudApp.CrudApplication.util.ResponseApp;

import java.util.List;

@RestController
@RequestMapping(path="/")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("create-user")
    public ResponseEntity<Contact> createUser(@RequestBody Contact contact)
    {
        if(this.userService.createUser(contact))
        {
            return new ResponseEntity<>(contact, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * @param contact - Java Pojo from client (postman, angular front end ...)
     * @return - Object if created true, null if email already exists of JSON body does not match
     *              expected POJO class.
     */
    @PostMapping("create-contact")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact)
    {
        if(this.userService.createUser(contact))
        {
            return new ResponseEntity<>(contact, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //View the list of users in the data source.
    @GetMapping("users")
    public ResponseEntity<List<Contact>> getUsers()
    {
        List<Contact> all_contacts = this.userService.getUsers();
        return new ResponseEntity<>(all_contacts, HttpStatus.OK);
    }

    /**
     * Updating the user details.
     * email forms part of the url e.g www.localhost:8080/update-user/bonga@mail.com
     * @param email - of the user to update.
     * @return status body. Details updated if email exists in the data source
     */
    @PutMapping("update-user/{email}")
    public ResponseEntity<?> updateUser(@PathVariable("email") String email, @RequestBody Contact contact)
    {
        if(this.userService.updateContact(email, contact))
        {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Operation unsuccessful",HttpStatus.BAD_REQUEST);
//        return new ResponseApp(new ResponseEntity().response());
    }

    /**
     * deleting the user details.
     * email forms part of the url e.g www.localhost:8080/delete-user/bonga@mail.com
     * @param email - of the user to delete.
     * @return status body message.
     */
    @DeleteMapping("delete-user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email)
    {
        if(this.userService.deleteUser(email))
        {
            return new ResponseEntity<>("User account deleted.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not deleted.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = {"/",""})
    public String homePage()
    {
        return "This is a default access page (:8080, /...), no authentication required.";
    }

    @GetMapping(value = "home")
    public String authHome()
    {
        return "This is a home page (8080:/home), it requires authentication.";
    }
}