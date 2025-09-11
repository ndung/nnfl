package io.sci.nnfl.web;

import io.sci.nnfl.model.Menu;
import io.sci.nnfl.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final MenuService menuService;

    public GlobalModelAttributes(MenuService menuService) {
        this.menuService = menuService;
    }

    @ModelAttribute("menuItems")
    public List<Menu> menuItems(Authentication auth) {
        return menuService.allowedFor(auth);
    }

}