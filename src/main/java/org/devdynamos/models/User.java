package org.devdynamos.models;

public class User {
    private int id;
    private String name;
    private String password;
    private int type;

    public User() {}

    public User(int id, String name, String email, String password, int type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, int type){
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }

    public Object[] toObjectArray() {
        return new Object[]{
                name,
                password,
                type
        };
    }
}
