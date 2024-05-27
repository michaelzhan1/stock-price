package org.example.stock;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String symbol;
        double price;
        while (true) {
            System.out.print("Enter symbol: ");
            symbol = sc.nextLine();
            if (symbol.isEmpty()) break;
            price = Stock.getPriceFromSymbol(symbol);
            if (price == -1) System.out.println("Symbol " + symbol + " does not exist");
            else System.out.println("Current price: " + price);
        }
    }
}
