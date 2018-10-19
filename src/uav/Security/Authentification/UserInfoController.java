package uav.Security.Authentification;


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
    private UserInfoService userInfoService;

    @RequestMapping(value = "/userinfo/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") UserInfo contact, BindingResult result) {
        userInfoService.addContact(contact);
        return "redirect:/userinfo/index";
    }
    @RequestMapping("/userinfo/index")
    public String listContacts(Map<String, Object> map) {
        map.put("contact", new UserInfo());
        map.put("contactList", userInfoService.listContact());
        return "contact_list";
    }
    @RequestMapping("/userinfo/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") Integer contactId) {
        userInfoService.removeContact(contactId);
        return "redirect:/userinfo/index";
    }
    @RequestMapping("/userinfo/save")
    public String saveContact(@ModelAttribute("contact") UserInfo contact, BindingResult result) {
        userInfoService.addContact(contact);
        return "redirect:/userinfo/edit/" + contact.getId();
    }
    @RequestMapping("/userinfo/edit/{contactId}")
    public String editContact(@PathVariable("countryId") Integer contactId, Map<String, Object> map) {
        UserInfo contact = userInfoService.retriveUserInfo(contactId);
        map.put("contact", contact);
        return "userinfo_edit";
    }
    @RequestMapping("/userinfo/find")
    public String findUsers(@ModelAttribute("findUser") UserInfo contact, BindingResult result, Map<String, Object> map) {
        map.put("contactList", userInfoService.findUsersByTelephone(contact.getTelephone()));
        map.put("contact", new UserInfo());
        return "user_list";
    }
    @RequestMapping("/userinfo")
    public String home() {
        return "redirect:/userinfo/index";
    }
}
