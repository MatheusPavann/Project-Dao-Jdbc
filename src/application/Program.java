package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1,"Books");
        Seller seller = new Seller(21,"Matheus","matheus@gmail.com",new Date(),4000.0,obj);
        System.out.println(seller);
    }
}