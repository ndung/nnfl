package io.sci.nnfl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Menu entry stored in MongoDB. Parent relationship is kept as a simple
 * parentId reference to avoid complex DBRef mappings.
 */
@Document(collection = "menu")
public class Menu {

    @Id
    private String id;

    private String title;

    // Null = non-clickable header/accordion
    private String href;

    private String icon; // optional (e.g., "ki-filled ki-home")

    private Integer orderIndex = 0;

    private boolean enabled = true;

    /** Identifier of the parent menu item; null for top-level items. */
    private String parentId;

    private Set<String> requiredRoles = new LinkedHashSet<>();

    /**
     * Transient list used when building menu trees. Not persisted in MongoDB.
     */
    @Transient
    private List<Menu> children = new ArrayList<>();

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
    public Set<String> getRequiredRoles() { return requiredRoles; }
    public void setRequiredRoles(Set<String> requiredRoles) { this.requiredRoles = requiredRoles; }
    public List<Menu> getChildren() { return children; }
    public void setChildren(List<Menu> children) { this.children = children; }
}

