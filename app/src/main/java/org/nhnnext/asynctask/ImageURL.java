package org.nhnnext.asynctask;

import java.util.ArrayList;

/**
 * Created by eunjooim on 15. 9. 28.
 */
public class ImageURL {

    private ArrayList<String> URLs;

    public ImageURL() {
        URLs = new ArrayList<>();
        for (int i = 1; i < 10; i++) makeURL(i);
    }

    private void makeURL(int i) {

        String abs = "http://lorempixel.com/output/abstract";
        String anim = "http://lorempixel.com/output/animals";
        String busin = "http://http://lorempixel.com/output/business";
        String cats = "http://lorempixel.com/output/cats";
        String city = "http://lorempixel.com/output/city";
        String food = "http://lorempixel.com/output/food";
        String nightlife = "http://lorempixel.com/output/nightlife";
        String fashion = "http://lorempixel.com/output/fashion";
        String midURL = "-q-c-320-240-";
        String ext = ".jpg";

        String num = i + ext;

        String inputURL = abs + midURL + num;
        URLs.add(inputURL);

        inputURL = anim + midURL + num;
        URLs.add(inputURL);

        inputURL = busin + midURL + num;
        URLs.add(inputURL);

        inputURL = cats + midURL + num;
        URLs.add(inputURL);

        inputURL = city + midURL + num;
        URLs.add(inputURL);

        inputURL = food + midURL + i;
        URLs.add(inputURL);

        inputURL = nightlife + midURL + i;
        URLs.add(inputURL);

        inputURL = fashion + midURL + i + ext;
        URLs.add(inputURL);
    }

    public ArrayList<String> getURLS () {
        return URLs;
    }
}
