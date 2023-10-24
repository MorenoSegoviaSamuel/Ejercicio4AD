package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Path JsonLibros = Path.of(".", "src", "main", "resources", "JsonLibros.json");
        List<Book> listaLibros = new ArrayList<>();
        int respuesta;


        Book b1 = new Book("12345B", "Pepito Grillo", "Pepe", 45, "20-06-2002");
        Book b2 = new Book("12345C", "El mago de Oz", "Pepe", 500, "30-04-1950");
        Book b3 = new Book("12345D", "Wigetta: El origen", "Willyrex", 120, "01-12-2022");

        listaLibros.add(b1);
        listaLibros.add(b2);
        listaLibros.add(b3);

        escribirListaObjetosJson(listaLibros, JsonLibros);

        List<Book> listaLibros2;

        System.out.println("\n|LEER ARRAY OBJETOS JSON-|");
        listaLibros2 = leerArrayObjetosJson(JsonLibros);
        listaLibros2.forEach(System.out::println);

        System.out.println("\n|-BUSCAR LIBROS-|");
        System.out.println(buscarLibro(listaLibros, "Pepe"));

do {
        System.out.println("\n|-Menú interactivo-|" +
                "\n-1- AÑADIR LIBROS" +
                "\n-2- BUSCAR LIBROS" +
                "\n-3- VER LISTA DE LIBROS ALMACENADOS" +
                "\n-4- SALIR");

        respuesta = sc.nextInt();
        switch (respuesta) {
            case 1:
                listaLibros = agregarLibro(listaLibros);
                break;
            case 2:
                String palabra;
                System.out.println("\nBusqueda por Titulo o autor:");
                palabra = sc.next();
                buscarLibro(listaLibros,palabra).stream().forEach(System.out::println);
                break;
            case 3:
                listaLibros.stream().forEach(System.out::println);
                break;
            case 4:
                System.out.println("Cerrando menú");
                break;
            default:
                System.out.println("Opción no valida");
                break;
            }
        }while (respuesta != 4);
    }


    public static void escribirListaObjetosJson(List<Book> libros, Path ruta) {

        try {
            Files.deleteIfExists(ruta);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(ruta.toFile(), libros);
        } catch (IOException e) {
            System.out.println("El fichero no existe");
        }
    }

    public static List<Book> leerArrayObjetosJson(Path ruta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(ruta.toFile(), new TypeReference<>() { });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Book> buscarLibro (List<Book> libros, String palabra){
        return libros.stream().filter((libro)-> libro.titulo().contains(palabra) || libro.autor().contains(palabra)).toList();
    }



    public static Book crearLibro()throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String titulo, isbn, fechaPubli, autor;
        int numPag;
        System.out.println("\nTitulo:");
        titulo = br.readLine();
        System.out.println("ISBN:");
        isbn = br.readLine();
        System.out.println("Fecha de Publicación:");
        fechaPubli = br.readLine();
        System.out.println("Autor:");
        autor = br.readLine();
        System.out.println("Número de páginas:");
        numPag =Integer.parseInt(br.readLine());

        Book b1 = new Book(isbn,titulo,autor,numPag,fechaPubli);
        return b1;
    }

    public static List<Book> agregarLibro(List<Book> libros)throws Exception{

        Set<Book> setLibros = new HashSet<>(libros);
        Book libro = crearLibro();
        if(setLibros.contains(libro)){
            System.out.println("Ese libro ya existe");
        }
        else{
            setLibros.add(libro);
        }
        return setLibros.stream().toList();

    }
}

