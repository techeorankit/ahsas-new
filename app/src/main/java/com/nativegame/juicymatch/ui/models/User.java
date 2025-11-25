package com.nativegame.juicymatch.ui.models;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    private String sender_id,email,wallet_amt,filterdata,userid,username,useremail,userphone,usercity,address,pincode,cityaddsection,suscribe,cartcount,servicecategoryname,splashsell;
    String gender ,shipname,shipemail,shipphone,myrefid,reffrence_id,shipaddress,shippincode,shipcity,showbottom,sellongetmi,servise,shopname,ownername,officeaddress,photo,chooseshopservice,apiservicecatid,token,
            shopcity,shopmobile,shopaddress,shoppincode, c_id;
    SharedPreferences sharedPreferences;
    boolean editor , registration= true ;

    public String getGender() {
        gender = sharedPreferences.getString( "gender","" );
        return gender;
    }

    public void setGender(String gender) {
        sharedPreferences.edit().putString( "gender",gender ).commit();
        this.gender = gender;
    }

    public String getSender_id() {
        sender_id = sharedPreferences.getString( "sender_id","" );
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        sharedPreferences.edit().putString( "sender_id",sender_id ).commit();
        this.sender_id = sender_id;
    }
    public String getShipname() {
        shipname = sharedPreferences.getString( "shipname","" );
        return shipname;
    }

    public void setShipname(String shipname) {
        sharedPreferences.edit().putString( "shipname",shipname ).commit();
        this.shipname = shipname;
    }

    public String getShipemail() {
        shipemail = sharedPreferences.getString( "shipemail","" );
        return shipemail;
    }

    public void setShipemail(String shipemail) {
        sharedPreferences.edit().putString( "shipemail",shipemail ).commit();
        this.shipemail = shipemail;
    }

    public String getShopcity() {
        shopcity = sharedPreferences.getString( "shopcity","" );
        return shopcity;
    }

    public void setShopcity(String shopcity) {
        sharedPreferences.edit().putString( "shopcity",shopcity ).commit();
        this.shopcity = shopcity;
    }

    public String getShopmobile() {
        shopmobile = sharedPreferences.getString( "shopmobile","" );
        return shopmobile;
    }

    public void setShopmobile(String shopmobile) {
        sharedPreferences.edit().putString( "shopmobile",shopmobile ).commit();
        this.shopmobile = shopmobile;
    }

    public String getShopaddress() {
        shopaddress = sharedPreferences.getString( "shopaddress","" );
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        sharedPreferences.edit().putString( "shopaddress",shopaddress ).commit();
        this.shopaddress = shopaddress;
    }

    public String getShoppincode() {
        shoppincode = sharedPreferences.getString( "shoppincode","" );
        return shoppincode;
    }

    public void setShoppincode(String shoppincode) {
        sharedPreferences.edit().putString( "shoppincode",shoppincode ).commit();
        this.shoppincode = shoppincode;
    }

    public String getC_id() {
        c_id = sharedPreferences.getString( "c_id","" );
        return c_id;
    }

    public void setC_id(String c_id) {
        sharedPreferences.edit().putString( "c_id", c_id).commit();
        this.c_id = c_id;
    }

    public String getToken() {
        token=sharedPreferences.getString( "token","" );
        return token;
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString( "token",token ).commit();
        this.token = token;
    }

    public String getWallet_amt() {
        wallet_amt=sharedPreferences.getString( "wallet","" );

        return wallet_amt;
    }

    public void setWallet_amt(String wallet_amt) {
        sharedPreferences.edit().putString( "wallet",wallet_amt ).commit();

        this.wallet_amt = wallet_amt;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor = sharedPreferences.edit().clear().commit();

    }

    public String getSplashsell() {
        splashsell=sharedPreferences.getString( "splashsell","" );
        return splashsell;
    }

    public void setSplashsell(String splashsell) {
        sharedPreferences.edit().putString( "splashsell",splashsell ).commit();
        this.splashsell = splashsell;
    }

    public String getChooseshopservice() {
        chooseshopservice=sharedPreferences.getString( "chooseservice","" );
        return chooseshopservice;
    }

    public void setChooseshopservice(String chooseshopservice) {
        sharedPreferences.edit().putString( "chooseservice",chooseshopservice ).commit();
        this.chooseshopservice = chooseshopservice;
    }

    public boolean isRegistration() {
        return  sharedPreferences.getBoolean( "registration",true );
    }

    public void setRegistration(boolean registration) {
        sharedPreferences.edit().putBoolean( "registration",registration ).commit();
    }

    public String getCartcount() {
        cartcount=sharedPreferences.getString( "cartcount","" );
        return cartcount;
    }

    public void setCartcount(String cartcount) {
        sharedPreferences.edit().putString( "cartcount",cartcount ).commit();
        this.cartcount = cartcount;
    }

    public String getSuscribe() {
        suscribe=sharedPreferences.getString( "subcribe","" );
        return suscribe;
    }
  public String getMyrefid() {
        myrefid=sharedPreferences.getString( "myrefid","" );
        return myrefid;
    }

    public void setSuscribe(String suscribe) {
        sharedPreferences.edit().putString( "subcribe",suscribe ).commit();
        this.suscribe = suscribe;
    }

    public String getCityaddsection() {
        cityaddsection=sharedPreferences.getString( "cityaddsection","" );
        return cityaddsection;
    }

    public void setCityaddsection(String cityaddsection) {
        sharedPreferences.edit().putString( "cityaddsection",cityaddsection ).commit();
        this.cityaddsection = cityaddsection;
    }

    public String getServise() {
        servise=sharedPreferences.getString( "servise","" );
        return servise;
    }

    public void setServise(String servise) {
        sharedPreferences.edit().putString( "servise",servise ).commit();
        this.servise = servise;
    }

    public String getPhoto() {
        photo=sharedPreferences.getString( "photo","" );
        return photo;
    }

    public void setPhoto(String photo) {
        sharedPreferences.edit().putString( "photo",photo ).commit();
        this.photo = photo;
    }

    public String getShopname() {
        shopname=sharedPreferences.getString( "shopname","" );
        return shopname;
    }

    public void setShopname(String shopname) {
        sharedPreferences.edit().putString( "shopname",shopname ).commit();
        this.shopname = shopname;
    }

    public String getOwnername() {
        ownername=sharedPreferences.getString( "owenername","" );
        return ownername;
    }

    public void setOwnername(String ownername) {
        sharedPreferences.edit().putString( "owenername",ownername ).commit();
        this.ownername = ownername;
    }

    public String getOfficeaddress() {
        officeaddress=sharedPreferences.getString( "officeaddress","" );
        return officeaddress;
    }

    public void setOfficeaddress(String officeaddress) {
        sharedPreferences.edit().putString( "officeaddress",officeaddress ).commit();
        this.officeaddress = officeaddress;
    }

    public String getSellongetmi() {
        sellongetmi=sharedPreferences.getString( "shellongetmi","" );
        return sellongetmi;
    }

    public void setSellongetmi(String sellongetmi) {
        sharedPreferences.edit().putString( "shellongetmi",sellongetmi ).commit();
        this.sellongetmi = sellongetmi;
    }

    public String getShowbottom() {
        showbottom=sharedPreferences.getString( "showbottom","" );

        return showbottom;
    }

    public void setShowbottom(String showbottom) {
        sharedPreferences.edit().putString( "showbottom",showbottom ).commit();

        this.showbottom = showbottom;
    }

    public String getAddress() {
        address=sharedPreferences.getString( "address","" );

        return address;
    }

    public void setAddress(String address) {
        sharedPreferences.edit().putString( "address",address ).commit();

        this.address = address;
    }

    public String getPincode() {
        pincode=sharedPreferences.getString( "pincode","" );

        return pincode;
    }

    public void setPincode(String pincode) {
        sharedPreferences.edit().putString( "pincode",pincode ).commit();

        this.pincode = pincode;
    }

    public String getShipphone() {
        shipphone=sharedPreferences.getString( "shippinphone","" );

        return shipphone;
    }

    public void setShipphone(String shipphone) {
        sharedPreferences.edit().putString( "shippinphone",shipphone ).commit();

        this.shipphone = shipphone;
    }

    public String getShipaddress() {
        shipaddress=sharedPreferences.getString( "shippinaddress","" );

        return shipaddress;
    }

    public void setShipaddress(String shipaddress) {
        sharedPreferences.edit().putString( "shippinaddress",shipaddress ).commit();

        this.shipaddress = shipaddress;
    }

    public String getShippincode() {
        shippincode=sharedPreferences.getString( "shippingpincode","" );

        return shippincode;
    }

    public void setShippincode(String shippincode) {
        sharedPreferences.edit().putString( "shippingpincode",shippincode ).commit();

        this.shippincode = shippincode;
    }
 public void setMyrefid(String myrefid) {
     this.myrefid = myrefid;

     sharedPreferences.edit().putString( "myrefid",myrefid ).commit();

    }

    public String getShipcity() {
        shipcity=sharedPreferences.getString( "shipcity","" );
        return shipcity;
    }

    public void setShipcity(String shipcity) {
        sharedPreferences.edit().putString( "shipcity",shipcity ).commit();

        this.shipcity = shipcity;
    }

    public String getUsername() {
        username=sharedPreferences.getString( "username","" );
        return username;
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString( "username",username ).commit();

        this.username = username;
    }

    public String getUseremail() {
        useremail=sharedPreferences.getString( "useremail","" );

        return useremail;
    }

    public void setUseremail(String useremail) {
        sharedPreferences.edit().putString( "useremail",useremail ).commit();

        this.useremail = useremail;
    }

    public String getUserphone() {
        userphone=sharedPreferences.getString( "userphone","" );

        return userphone;
    }

    public void setUserphone(String userphone) {
        sharedPreferences.edit().putString( "userphone",userphone ).commit();

        this.userphone = userphone;
    }

    public String getUsercity() {
        usercity=sharedPreferences.getString( "usercity","" );

        return usercity;
    }

    public void setUsercity(String usercity) {
        sharedPreferences.edit().putString( "usercity",usercity ).commit();

        this.usercity = usercity;
    }

    public String getUserid() {
        userid=sharedPreferences.getString( "userid","" );
        return userid;
    }

    public void setUserid(String userid) {
        sharedPreferences.edit().putString( "userid",userid ).commit();
        this.userid = userid;
    }

    public String getEmail() {
        email=sharedPreferences.getString( "userdata","" );
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString( "userdata",email ).commit();
    }

    public String getFilterdata() {
        filterdata=sharedPreferences.getString( "filterdata","" );
        return filterdata;
    }

    public void setFilterdata(String filterdata) {
        this.filterdata = filterdata;
        sharedPreferences.edit().putString( "filterdata",filterdata ).commit();
    }

    public String getServicecategoryname() {
        servicecategoryname=sharedPreferences.getString( "servicecategoryname","" );
        return servicecategoryname;
    }

    public void setServicecategoryname(String servicecategoryname) {
        sharedPreferences.edit().putString( "servicecategoryname",servicecategoryname ).commit();
        this.servicecategoryname = servicecategoryname;
    }

    public String getApiservicecatid() {
        apiservicecatid=sharedPreferences.getString( "apiservicecatid","" );
        return apiservicecatid;
    }

    public void setApiservicecatid(String apiservicecatid) {
        sharedPreferences.edit().putString( "apiservicecatid",apiservicecatid ).commit();
        this.apiservicecatid = apiservicecatid;
    }

    public User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences( "userinfo",Context.MODE_PRIVATE );
    }

    public String getReffrence_id() {
        reffrence_id=sharedPreferences.getString( "reffrence_id","" );

        return reffrence_id;
    }

    public void setReffrence_id(String reffrence_id) {
        sharedPreferences.edit().putString( "reffrence_id",reffrence_id ).commit();

        this.reffrence_id = reffrence_id;
    }
}
