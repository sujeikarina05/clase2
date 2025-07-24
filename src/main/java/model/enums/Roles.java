package model.enums;

public enum Roles {
    MANAGER("administrador"),
    CASHIER("cajero"),
    WAITER("mozo"),
    CHEF("chef");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public static Roles getRole(String roleName) {
        for (Roles role : Roles.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

    public String getRoleName() {
        return roleName;
    }
}
