package ca.uqtr.dmi.messagerielogic.constants;


public enum Role {
    AGENT(0), ADMIN(1);
    public final int role;
    public final String name;

    Role(int i) {
        role = i;
        name = name();
    }
    public static Role of(int role){
        for (Role r: values()){
            if(r.role == role){
                return r;
            }
        }
        return AGENT;
    }

}
