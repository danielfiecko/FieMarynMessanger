package com.danielfiecko.fiemarynapp.controller;

import com.danielfiecko.fiemarynapp.model.ConversationReply;
import com.danielfiecko.fiemarynapp.model.ConversationView;
import com.danielfiecko.fiemarynapp.model.User;
import com.danielfiecko.fiemarynapp.service.ConversationViewService;
import com.danielfiecko.fiemarynapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    ConversationViewService conversationViewService;

    @Autowired
    MessageSource messageSource;

    String loggedUserNickname = "Guest";

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String displayIndex(ModelMap model) {
        model.addAttribute("loggedUserNickname", loggedUserNickname);
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String displayLogin(ModelMap model) {
        model.addAttribute("entryUser", new User());
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String checkLoggedUser(@ModelAttribute("entryUser") @Valid User entryUser, BindingResult result,
                                  ModelMap model) {

        if (result.hasErrors()) {
            return "login";
        }

        if (!userService.nicknameExists(entryUser.getNickname())) {
            FieldError nicknameExistsError = new FieldError("entryUser", "nickname", messageSource.getMessage("nickname.not.found", new String[]{entryUser.getNickname()}, Locale.getDefault()));
            result.addError(nicknameExistsError);
            return "login";
        }

        if (!userService.isUserPasswordCorrect(entryUser.getNickname(), entryUser.getPassword())) {
            FieldError passwordError = new FieldError("entryUser", "password", messageSource.getMessage("not.correct.password", new String[]{entryUser.getNickname()}, Locale.getDefault()));
            result.addError(passwordError);
            return "login";
        }

        loggedUserNickname = entryUser.getNickname();
        model.addAttribute("loggedUserNickname", loggedUserNickname);
        return "index";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logOutUser(ModelMap model) {
        loggedUserNickname = "Guest";
        model.addAttribute("loggedUserNickname", loggedUserNickname);
        return "index";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String createNewUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("edit", false);
        return "signup&edit";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String saveUser(@Valid User brandNewUser, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "signup&edit";
        }

        if (!userService.isUserNicknameUnique(brandNewUser.getId(), brandNewUser.getNickname())) {
            FieldError nicknameError = new FieldError("user", "nickname", messageSource.getMessage("non.unique.nickname", new String[]{brandNewUser.getNickname()}, Locale.getDefault()));
            result.addError(nicknameError);
            return "signup&edit";
        }

        userService.saveUser(brandNewUser);

        model.addAttribute("success", "You " + brandNewUser.getNickname() + " registered successfully");
        return "success";
    }

    @RequestMapping(value = {"/-{loggedUserNickname}-contacts"}, method = RequestMethod.GET)
    public String showListOfContacts(@PathVariable String loggedUserNickname, ModelMap model) {
        if (!userService.nicknameExists(loggedUserNickname)) return "redirect:/logout";
        model.addAttribute("loggedUserNickname", loggedUserNickname);
        List<User> users = userService.findAllContacts(loggedUserNickname);
        model.addAttribute("users", users);
        return "contacts";
    }

    @RequestMapping(value = {"/-{loggedUserNickname}-edit-{nickname}-user"}, method = RequestMethod.GET)
    public String editSelectedUserFromContactList(@PathVariable String nickname, ModelMap model) {
        User userToEdit = userService.findUserByNickname(nickname);
        model.addAttribute("user", userToEdit);
        model.addAttribute("edit", true);
        return "signup&edit";
    }

    @RequestMapping(value = {"/-{loggedUserNickname}-edit-{nickname}-user"}, method = RequestMethod.POST)
    public String updateUserDetails(@Valid User updatedUser, BindingResult result, ModelMap model) {
        if (result.hasErrors()) return "signup&edit";
        userService.updateUser(updatedUser);
        model.addAttribute("success", "Contact " + updatedUser.getFirstName() + " updated successfully");
        return "success";
    }

    @RequestMapping(value = {"/-{loggedUserNickname}-delete-{nickname}-user"}, method = RequestMethod.GET)
    public String deleteUser(ModelMap model, @PathVariable String nickname, @PathVariable String loggedUserNickname) {
        userService.deleteUserByNickname(nickname);
        return "redirect:/-{loggedUserNickname}-contacts";
    }

    @RequestMapping(value = {"/-{loggedUserNickname}-chat-with-{nickname}-user"}, method = RequestMethod.GET)
    public String displayChatWindow(@PathVariable String nickname, ModelMap model) {
        model.addAttribute("loggedUserNickname", loggedUserNickname);
        List<User> contacts = userService.findAllContacts(loggedUserNickname);
        model.addAttribute("users", contacts);

        User userToChat = userService.findUserByNickname(nickname);
        model.addAttribute("userToChatNickname", userToChat.getNickname());
        model.addAttribute("userToChatId", userToChat.getId());
        model.addAttribute("chat", true);

        User loggedUser = userService.findUserByNickname(loggedUserNickname);
        ConversationView conversationView = conversationViewService.getConversationViewBetweenTwoUsers(loggedUser.getId(), userToChat.getId());
        model.addAttribute("conversationView", conversationView);

        model.addAttribute("someMessage", new ConversationReply());

        return "contacts";
    }


    @RequestMapping(value = {"/-{loggedUserNickname}-chat-with-{nickname}-user"}, method = RequestMethod.POST)
    public String sendMessage(@ModelAttribute("someMessage") @Valid ConversationReply someReply, BindingResult result, @PathVariable String nickname, ModelMap model) {
        if (result.hasErrors()) return "contacts";
        model.addAttribute("loggedUserNickname", loggedUserNickname);

        User personToChat = userService.findUserByNickname(nickname);
        User loggedUser = userService.findUserByNickname(loggedUserNickname);

        someReply.setMessageWriterId(loggedUser.getId());
        someReply.setConversationId(conversationViewService.searchConversationIdBetweenTwoUsers(loggedUser.getId(), personToChat.getId()));
        conversationViewService.sendMessage(someReply);

        return "redirect:/-{loggedUserNickname}-chat-with-{nickname}-user";
    }

}
