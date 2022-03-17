package com.group13.sysc4806;

import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
public class AddressBook {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @javax.persistence.OneToMany(cascade = CascadeType.ALL)
    private List<BuddyInfo> buddies;

    public AddressBook() {
        this.buddies = new ArrayList<>();
    }

    public AddressBook(long id) {
        this.buddies = new ArrayList<>();
        this.id = id;
    }

    public AddressBook(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddy) {
        this.buddies.add(buddy);
    }

    public void removeBuddy(BuddyInfo buddy) {
        this.buddies.remove(buddy);
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @javax.persistence.OneToMany(cascade = CascadeType.ALL)
    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public BuddyInfo getBuddy(long id) {
        for (BuddyInfo buddy: this.buddies) {
            if(buddy.getId() == id) {
                return buddy;
            }
        }

        return new BuddyInfo();
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (BuddyInfo buddy : this.getBuddies()) {
            result = prime * result + buddy.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        AddressBook book = (AddressBook) obj;
        return book.getBuddies().equals(this.getBuddies());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Object buddy : this.buddies.toArray()) {
            sb.append("[").append(index++).append("] ").append(buddy).append("\n");
        }

        return sb.toString();
    }
}
