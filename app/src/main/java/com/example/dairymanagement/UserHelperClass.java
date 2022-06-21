package com.example.dairymanagement;

public class UserHelperClass {
    String name, email, phoneno, password, curcow, curbuffalo, address,maxcow,maxbuffalo;

    public UserHelperClass() {
    }

    public UserHelperClass(String name,  String email, String phoneno, String password, String curcow, String curbuffalo, String address,String maxcow,String maxbuffalo) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.curcow = curcow;
        this.curbuffalo = curbuffalo;
        this.address = address;
        this.maxcow=maxcow;
        this.maxbuffalo=maxbuffalo;
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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurcow() {
        return curcow;
    }

    public void setCurcow(String curcow) {
        this.curcow = curcow;
    }

    public String getCurbuffalo() {
        return curbuffalo;
    }

    public void setCurbuffalo(String curbuffalo) {
        this.curbuffalo = curbuffalo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaxcow(String maxcow) {
        this.maxcow = maxcow;
    }

    public String getMaxcow() {
        return maxcow;
    }

    public String getMaxbuffalo() {
        return maxbuffalo;
    }

    public void setMaxbuffalo(String maxbuffalo) {
        this.maxbuffalo = maxbuffalo;
    }
}
