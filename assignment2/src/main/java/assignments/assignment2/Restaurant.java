package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private ArrayList <Menu> menuList;
    private String nama;
    // Konstruktor untuk membuat objek Restaurant dengan nama tertentu
    public Restaurant(String nama){
        this.nama = nama;
        this.menuList = new ArrayList <>();
    }
    // Method untuk mengambil dan mengeset variable private di class Restaurant
    public ArrayList < Menu > getMenuList() {
        return menuList;
    }

    public void addMenu(Menu menuItem) {
        menuList.add(menuItem);
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {

        this.nama = nama;
    }
}
