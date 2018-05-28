package uav.Authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by administrator on 23.06.17.
 */
@Controller
public class UserInfoController {
    @Autowired
    private ContactService contactService;
    @RequestMapping("/index")
    public String listContacts(Map<String, Object> map) {
        map.put("contact", new UserInfo());
        map.put("contactList", contactService.listContact());
        return "contact";
    }
    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") UserInfo contact, BindingResult result) {
        contactService.addContact(contact);
        return "redirect:/index";
    }
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") Integer contactId) {
        contactService.removeContact(contactId);
        return "redirect:/index";
    }
}
