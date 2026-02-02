package com.library.model;

import java.time.LocalDate;

public class Member {

    private int memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private int maxBooksAllowed;

    
    public Member() {
    }

    // Constructor for INSERT
    public Member(String name, String email, String phone,
                  LocalDate membershipDate, int maxBooksAllowed) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
        this.maxBooksAllowed = maxBooksAllowed;
    }

    // Constructor for SELECT
    public Member(int memberId, String name, String email, String phone,
                  LocalDate membershipDate, int maxBooksAllowed) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
        this.maxBooksAllowed = maxBooksAllowed;
    }

    // Getters & Setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }
    
    
    
}
