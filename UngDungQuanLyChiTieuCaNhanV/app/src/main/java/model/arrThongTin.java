package model;

public class arrThongTin {
    public String date, time, nhom, taikhoan;
    public int money, ma;

    public arrThongTin(int ma, String date, String time, int money, String nhom, String taikhoan) {
        this.ma = ma;
        this.date = date;
        this.time = time;
        this.money = money;
        this.nhom = nhom;
        this.taikhoan = taikhoan;
    }
}
