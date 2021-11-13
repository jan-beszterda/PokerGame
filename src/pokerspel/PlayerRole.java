package pokerspel;

public enum PlayerRole {

    DEALER("Dealer"), SMALL_BLIND("Small blind"), BIG_BLIND("Big blind");

    private final String roleName;

    PlayerRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
