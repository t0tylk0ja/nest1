package com.dominik.nestapp;

import java.util.HashMap;

public class DevList {

    HashMap<String,Dev>devList;

    public DevList (){
        devList = new HashMap<String,Dev>();
        devList.put("MURAPOL",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Fmurapol.png?alt=media&token=4d69c114-4e17-46f7-84c4-3ff3bf27c3bc",
                "Murapol S.A.","wroclaw@murapol.pl","ul. Buforowa 93, Wrocław"));
        devList.put("ATAL",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Fatal.png?alt=media&token=bacf33d6-306a-4fea-a8b0-269a406e7e81",
                        "Atal S.A.","wroclaw@atal.pl","ul. Armii Krajowej 61, Wrocław"));
        devList.put("ARCHICOM",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Farchicom.png?alt=media&token=8f67eee6-cfa4-458b-9133-fafc85f44b33",
                        "Archicom S.A.","archicom@archicom.pl","ul. Św.Mikołaja 7, Wrocław"));
        devList.put("LOKUM",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Flokum.png?alt=media&token=b65673b6-c414-44c9-8042-5eb98c91b8f4",
                        "Lokum Deweloper","info@lokumdeweloper.pl","ul. Krawiecka 1, Wrocław"));

        devList.put("VANTAGE",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Fvantage.png?alt=media&token=55709cb9-e184-49ac-8639-4a9a367b42bb",
                        "Vantage Development","biuro@vantage-sa.pl","ul. Dąbrowskiego 44, Wrocław"));

        devList.put("PCG",
                new Dev("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/logos%2Fpcg.png?alt=media&token=ff558b38-7a3b-4f7e-b6ce-e7935f046caa",
                        "PCG Deweloper","kontakt@pcg.pl","ul. Życzliwa 21, Wrocław"));

    }

    public HashMap returnDevList(){
        return devList;
    }

}
