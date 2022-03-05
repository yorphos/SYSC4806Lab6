package com.kaueoliveira.sysc4806;

import javax.persistence.GenerationType;

@javax.persistence.Entity
public class BuddyInfo {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phoneNumber;

    public BuddyInfo() {
        this.name = "";
        this.phoneNumber = "";
    }

    public BuddyInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public BuddyInfo(String name, String phoneNumber, long id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        BuddyInfo buddy = (BuddyInfo) obj;
        return buddy.getName().equals(getName()) && buddy.getPhoneNumber().equals(getPhoneNumber());
    }

    @Override
    public String toString() {
        return this.name + " (" + this.phoneNumber + ")";
    }

}
