package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
//        menu to interact with the DB
        PaintingController paintingController = new PaintingController();
        paintingController.startAPI();
    }
}