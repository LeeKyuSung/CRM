package kyusung.crm.pojo;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class Order {

    String date;
    String menu;
    String price;
    String sawon;

    public Order(String date, String menu, String price, String sawon) {
        super();
        this.date = date;
        this.menu = menu;
        this.price = price;
        this.sawon = sawon;
    }

    public Order(String str) throws Exception {
        String[] tmp = str.split("&");
        for (int i = 0; i < tmp.length; i++) {
            String[] tmp2 = tmp[i].split("=");
            String key = tmp2[0];
            String value;
            if (tmp2.length == 1) {
                value = "";
            } else {
                value = URLDecoder.decode(tmp2[1], "UTF-8");
            }
            if (key.equals("date")) {
                date = value;
            } else if (key.equals("menu")) {
                menu = value;
            } else if (key.equals("price")) {
                price = value;
            } else if (key.equals("sawon")) {
                sawon = value;
            }
        }
    }

    public String getString() throws Exception {
        String ret = "";
        ret += "date=" + URLEncoder.encode(date, "UTF-8") + "&";
        ret += "menu=" + URLEncoder.encode(menu, "UTF-8") + "&";
        ret += "price=" + URLEncoder.encode(price, "UTF-8") + "&";
        ret += "sawon=" + URLEncoder.encode(sawon, "UTF-8") + "\n";
        return ret;
    }

    public String getDate() {
        return date;
    }

    public String getMenu() {
        return menu;
    }

    public String getPrice() {
        return price;
    }

    public String getSawon() {
        return sawon;
    }
}
