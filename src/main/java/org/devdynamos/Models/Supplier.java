package org.devdynamos.Models;

public class Supplier {

    private String name;
    private String uid;
    private String partName;
    private String email;
    private String phone;
    private String address;


    public Supplier(String name, String uid, String partName, String email, String phone, String address) {
        this.name = name;
        this.uid = uid;
        this.partName = partName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


    public String getSupplierDetails()
    {
        return ("Supplier name is " + name + " and UID - " + uid + ". We import " + partName + " from this Supplier. \nContact Details \nEmail -  " + email + " \nPhone Number - " + phone + " \n Address - " + address);
    }

    public void updateSupplier(String type, String value)
    {
        if(type.equals("name"))
        {
            this.name = value;
        }
        else if(type.equals("uid"))
        {
            this.uid = value;
        }
        else if(type.equals("part"))
        {
            this.partName = value;
        }
        else if(type.equals("email"))
        {
            this.email = value;
        }
        else if(type.equals("phone"))
        {
            this.phone = value;
        }
        else if(type.equals("address"))
        {
            this.address = value;
        }
    }


    //alternative method

/*
    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

 */
}
