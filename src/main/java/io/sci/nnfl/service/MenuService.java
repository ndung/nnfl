package io.sci.nnfl.service;

import io.sci.nnfl.model.Menu;
import io.sci.nnfl.model.dto.MenuRequest;
import io.sci.nnfl.model.repository.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository repo;

    public MenuService(MenuRepository repo) { this.repo = repo; }


    @Transactional(readOnly = true)
    public Menu getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Menu create(MenuRequest req) {
        Menu e = new Menu();
        apply(e, req);
        return repo.save(e);
    }

    @Transactional
    public Menu update(String id, MenuRequest req) {
        var e = getById(id);
        apply(e, req);
        return repo.save(e);
    }

    @Transactional
    public void delete(String id) {
        // optionally ensure no children exist before delete
        repo.deleteById(id);
    }

    private void apply(Menu e, MenuRequest req) {
        e.setTitle(req.getTitle());
        e.setHref(req.getHref());
        e.setIcon(req.getIcon());
        e.setOrderIndex(req.getOrderIndex() == null ? 0 : req.getOrderIndex());
        e.setEnabled(req.isEnabled());
        e.setRequiredRoles(req.rolesAsSet());

        if (req.getParentId() != null) {
            if (e.getId() != null && req.getParentId().equals(e.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set self as parent");
            if (!repo.existsById(req.getParentId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent not found");
            e.setParentId(req.getParentId());
        } else {
            e.setParentId(null);
        }
    }

    public boolean hasAny(Set<String> required, Set<String> userRoles) {
        Set<String> req = normalizeRoles(required);
        if (req.isEmpty()) return true;                  // no restriction
        Set<String> user = normalizeRoles(userRoles);
        for (String r : req) if (user.contains(r)) return true;
        return false;
    }

    /** Build the permitted top-level menu for the current user. */
    public List<Menu> allowedFor(Authentication auth) {
        if (auth == null || auth.getAuthorities() == null) return List.of();

        Set<String> userRoles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Menu menu1 = new Menu();
        menu1.setId("1");
        menu1.setOrderIndex(1);
        menu1.setEnabled(true);
        menu1.setTitle("Material");
        menu1.setHref("/materials");

        Menu menu2 = new Menu();
        menu2.setId("2");
        menu2.setOrderIndex(2);
        menu2.setEnabled(true);
        menu2.setTitle("User");
        menu2.setHref("/admin/users");

        Menu menu3 = new Menu();
        menu3.setId("3");
        menu3.setOrderIndex(3);
        menu3.setEnabled(true);
        menu3.setTitle("Profile");
        menu3.setHref("/profile");

        return List.of(menu1, menu2, menu3);
    }

    /** Normalize roles: trim, uppercase, strip ROLE_ prefix; null-safe. */
    private static Set<String> normalizeRoles(Collection<String> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> s.toUpperCase(Locale.ROOT))
                .map(s -> s.startsWith("ROLE_") ? s.substring(5) : s)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
