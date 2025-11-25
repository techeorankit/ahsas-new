package com.nativegame.juicymatch.ui.models;

public class LoginModels {

    String gender, sender_id,message, u_id, name, mobile, photo, city, email, refer_id, addreass, otp, owner_name, shop_name,
            shop_mobile, bank_acc_no,bank_name,bank_ifsc, shop_address, shop_city, shop_logo,shop_color,pincode,like_count,comment_count,total_product,
            paid_status,trusted_status ,follow_s, c_id,u_wallet,shop_detail,about_us, fb_link, insta_link, linkedin_link, web_link;
    int shop_status;

    public LoginModels(String gender ,String sender_id,String message, String u_id, String name, String mobile, String photo, String city, String email, String refer_id, String addreass, String otp, String owner_name, String shop_name, String shop_mobile, String bank_acc_no, String bank_name, String bank_ifsc, String shop_address, String shop_city, String shop_logo, String shop_color, String pincode, String like_count, String comment_count, String total_product, String paid_status, String trusted_status, String follow_s, String c_id, String u_wallet, String shop_detail, String about_us, String fb_link, String insta_link, String linkedin_link, String web_link, int shop_status) {
        this.gender = gender;
        this.sender_id = sender_id;
        this.message = message;
        this.u_id = u_id;
        this.name = name;
        this.mobile = mobile;
        this.photo = photo;
        this.city = city;
        this.email = email;
        this.refer_id = refer_id;
        this.addreass = addreass;
        this.otp = otp;
        this.owner_name = owner_name;
        this.shop_name = shop_name;
        this.shop_mobile = shop_mobile;
        this.bank_acc_no = bank_acc_no;
        this.bank_name = bank_name;
        this.bank_ifsc = bank_ifsc;
        this.shop_address = shop_address;
        this.shop_city = shop_city;
        this.shop_logo = shop_logo;
        this.shop_color = shop_color;
        this.pincode = pincode;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.total_product = total_product;
        this.paid_status = paid_status;
        this.trusted_status = trusted_status;
        this.follow_s = follow_s;
        this.c_id = c_id;
        this.u_wallet = u_wallet;
        this.shop_detail = shop_detail;
        this.about_us = about_us;
        this.fb_link = fb_link;
        this.insta_link = insta_link;
        this.linkedin_link = linkedin_link;
        this.web_link = web_link;
        this.shop_status = shop_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_ifsc() {
        return bank_ifsc;
    }

    public void setBank_ifsc(String bank_ifsc) {
        this.bank_ifsc = bank_ifsc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefer_id() {
        return refer_id;
    }

    public void setRefer_id(String refer_id) {
        this.refer_id = refer_id;
    }

    public String getAddreass() {
        return addreass;
    }

    public void setAddreass(String addreass) {
        this.addreass = addreass;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_mobile(String shop_mobile) {
        this.shop_mobile = shop_mobile;
    }

    public String getBank_acc_no() {
        return bank_acc_no;
    }

    public void setBank_acc_no(String bank_acc_no) {
        this.bank_acc_no = bank_acc_no;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_city() {
        return shop_city;
    }

    public void setShop_city(String shop_city) {
        this.shop_city = shop_city;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getShop_color() {
        return shop_color;
    }

    public void setShop_color(String shop_color) {
        this.shop_color = shop_color;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getTotal_product() {
        return total_product;
    }

    public void setTotal_product(String total_product) {
        this.total_product = total_product;
    }

    public String getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(String paid_status) {
        this.paid_status = paid_status;
    }

    public String getTrusted_status() {
        return trusted_status;
    }

    public void setTrusted_status(String trusted_status) {
        this.trusted_status = trusted_status;
    }

    public String getFollow_s() {
        return follow_s;
    }

    public void setFollow_s(String follow_s) {
        this.follow_s = follow_s;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getU_wallet() {
        return u_wallet;
    }

    public void setU_wallet(String u_wallet) {
        this.u_wallet = u_wallet;
    }

    public String getShop_detail() {
        return shop_detail;
    }

    public void setShop_detail(String shop_detail) {
        this.shop_detail = shop_detail;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getFb_link() {
        return fb_link;
    }

    public void setFb_link(String fb_link) {
        this.fb_link = fb_link;
    }

    public String getInsta_link() {
        return insta_link;
    }

    public void setInsta_link(String insta_link) {
        this.insta_link = insta_link;
    }

    public String getLinkedin_link() {
        return linkedin_link;
    }

    public void setLinkedin_link(String linkedin_link) {
        this.linkedin_link = linkedin_link;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }

    public int getShop_status() {
        return shop_status;
    }

    public void setShop_status(int shop_status) {
        this.shop_status = shop_status;
    }
}
