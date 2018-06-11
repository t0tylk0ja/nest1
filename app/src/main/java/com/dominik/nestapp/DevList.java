package com.dominik.nestapp;

import java.util.HashMap;

public class DevList {

    HashMap<String,Dev>devList;

    public DevList (){
        devList = new HashMap<String,Dev>();
        devList.put("MURAPOL",
                new Dev("Murapol S.A.","wroclaw@murapol.pl","ul. Buforowa 93, Wrocław",R.drawable.murapol));
        devList.put("ATAL",
                new Dev("Atal S.A.","wroclaw@atal.pl","ul. Armii Krajowej 61, Wrocław",R.drawable.atal));
        devList.put("ARCHICOM",
                new Dev("Archicom S.A.","archicom@archicom.pl","ul. Św.Mikołaja 7, Wrocław",R.drawable.archicom));
        devList.put("LOKUM",
                new Dev("Lokum Deweloper","info@lokumdeweloper.pl","ul. Krawiecka 1, Wrocław",R.drawable.lokum));
        devList.put("VANTAGE",
                new Dev("Vantage Development","biuro@vantage-sa.pl","ul. Dąbrowskiego 44, Wrocław",R.drawable.vantage));
        devList.put("PCG",
                new Dev("PCG Deweloper","kontakt@pcg.pl","ul. Życzliwa 21, Wrocław",R.drawable.pcg));

    }

    public HashMap returnDevList(){
        return devList;
    }

}
