package org.example.model;


public record Contact(String fullName, String phoneNumber, String email) {

    @Override
    public String toString() {
        return fullName + " | " + phoneNumber + " | " + email;
    }

    public String toFile(){
        return fullName + ";" + phoneNumber + ";" + email;
    }
}
