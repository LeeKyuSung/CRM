package kyusung.crm.pojo;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class Customer {

    String id;
    String tel1;
    String tel2;
    String name;
    String addr;
    String addr2;

    public Customer(String id, String tel1, String tel2, String name, String addr, String addr2) {
        super();
        this.id = id;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.name = name;
        this.addr = addr;
        this.addr2 = addr2;
    }

    public Customer(String str) throws Exception {
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

            if (key.equals("id")) {
                id = value;
            } else if (key.equals("tel1")) {
                tel1 = value.trim();
            } else if (key.equals("tel2")) {
                tel2 = value.trim();
            } else if (key.equals("name")) {
                name = value.trim();
            } else if (key.equals("addr")) {
                addr = value.trim();
            } else if (key.equals("addr2")) {
                addr2 = value.trim();
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getString() throws Exception {
        String ret = "";
        ret += "id=" + URLEncoder.encode(id, "UTF-8") + "&";
        ret += "tel1=" + URLEncoder.encode(tel1, "UTF-8") + "&";
        ret += "tel2=" + URLEncoder.encode(tel2, "UTF-8") + "&";
        ret += "name=" + URLEncoder.encode(name, "UTF-8") + "&";
        ret += "addr=" + URLEncoder.encode(addr, "UTF-8") + "&";
        ret += "addr2=" + URLEncoder.encode(addr2, "UTF-8") + "\n";
        return ret;
    }

    public boolean isTel(String inputTel) {
        String input = inputTel.trim();
        if (tel1.equals(input) || tel2.equals(input)) {
            return true;
        } else {
            return false;
        }
    }

    public String getNotificationStr() {
        String ret = "";
        if (!"".equals(name)) {
            ret += "[" + name + "] ";
        }
        if (!"".equals(addr)) {
            ret += addr;

        }
        if (!"".equals(addr2)) {
            ret += "\n" + addr2;

        }
        ret += "\n";

        return ret;
    }
}
