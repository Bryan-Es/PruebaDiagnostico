package com.example;

import java.util.Scanner;
import com.example.Clases.Producto;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Producto> productos = new ArrayList<>();
        while (true) {
            System.out.println();
            System.out.println("--- Menú ---");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Salir");
            System.out.print("Seleccione una opcion: ");
            String opcion = sc.nextLine().trim();

            if (opcion.equals("1")) {
                System.out.println("Ingrese un producto (nombre precio cantidad) o (nombre,precio,cantidad):");
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.println("Entrada vacía. Intente de nuevo.");
                    continue;
                }

                String[] parts;
                if (line.contains(",")) {
                    parts = line.split(",");
                } else {
                    parts = line.split("\\s+");
                }

                if (parts.length < 3) {

                    System.out.println(
                            "Formato inválido. Use: nombre precio cantidad (ej: Manzana 1.2 10) o (Manzana,1.2,10)");
                    continue;
                }

                String nombre = parts[0].trim();
                try {
                    double precio = Double.parseDouble(parts[1].trim());
                    int cantidad = Integer.parseInt(parts[2].trim());

                    Producto p = new Producto(nombre, precio, cantidad);
                    productos.add(p);
                    System.out.println("Producto agregado: " + p);
                } catch (NumberFormatException e) {
                    System.out.println("Precio o cantidad inválidos: " + e.getMessage());
                }

            } else if (opcion.equals("2")) {
                if (productos.isEmpty()) {
                    System.out.println("No hay productos.");
                } else {
                    System.out.println("Listado de productos:");
                    for (int i = 0; i < productos.size(); i++) {
                        System.out.println((i + 1) + ") " + productos.get(i));
                        System.out.println("El total de productos es: " + productos.size());
                        System.out.println("La suma de los precios es: "
                                + productos.stream().mapToDouble(Producto::getPrecio).sum());
                    }
                }

            } else if (opcion.equals("3")) {
                break;

            } else {
                System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }
}