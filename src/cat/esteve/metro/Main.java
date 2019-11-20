package cat.esteve.metro;

import cat.esteve.metro.lines.Line;
import cat.esteve.metro.utils.FileUtils;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);

    private String currentFilePath = "";

    private LinkedList<Line> lines = new LinkedList<Line>();

    private void start() {
        this.menu_main();
    }

    private void menu_main() {
        System.out.println(". : Metro de Barcelona : .");
        System.out.println("Benvolgut a l'administrador oficial de linies del metro de barrna bro.");

        int in = 0;
        do {
            sc = new Scanner(System.in);
            System.out.println();
            System.out.println("0) Sortir");
            System.out.println("1) Carregar linies");
            System.out.println("2) Editar linies");
            System.out.println("3) Imprimir linies");
            in = Integer.parseInt(this.sc.nextLine());

            switch(in) {
                case 1:
                    this.menu_load();
                    break;
                case 2:
                    this.menu_edit();
                    break;
                case 3:
                    this.menu_print();
                    break;
            }
        } while(in != 0);
        this.save_file();
    }

    private void menu_load() {
        System.out.println("Introdueix el nom del arxiu per carregar les linies: ");
        String name = this.sc.nextLine();
        String path = "res/" + name + ".met";
        String data = FileUtils.readFile(path);
        if(load_file(data)) {
            System.out.println("Linies carregades correctament!");
            this.currentFilePath = path;
        } else {
            System.out.println("Ho sentim pero les linies no s'han pogut carregar correctament!");
        }
        System.out.println();
    }

    private void menu_edit() {
        System.out.println();
        int in = 0;
        do {
            System.out.println("0) Enrrere");
            for(int i = 0; i < this.lines.size(); i++) {
                Line l = this.lines.get(i);
                System.out.println((i+1) + ") Editar linia " + l.getName());
            }
            in = Integer.parseInt(sc.next());
            if(in > 0 && in <= this.lines.size()) this.menu_edit_line(in-1);
        } while(in != 0);
    }

    private void menu_edit_line(int line) {
        Line l = this.lines.get(line);
        System.out.println("");
        System.out.println(". : Menu d'edicio de la linia " + l.getName() + " : .");
        int in = 0;
        do {
            System.out.println("0) Enrrere");
            System.out.println("1) Afegir parada");
            System.out.println("2) Eliminar parada");
            in = Integer.parseInt(this.sc.next());
            switch(in) {
                case 1:
                    this.menu_add_stop(line);
                    break;
                case 2:
                    this.menu_del_stop(line);
                    break;
            }
        } while(in != 0);
    }

    private void menu_add_stop(int line) {
        Line l = this.lines.get(line);
        System.out.println();
        System.out.println("Afegir parada a " + l.getName());
        System.out.print("Introdueix el nom de la parada: ");
        String name = this.sc.next();
        System.out.print("Introdueix la posicio de la parada: ");
        int pos = Integer.parseInt(this.sc.next())-1;
        if(pos >= l.getSize() || pos < 0) {
            System.out.println("La posició es incorrecte!");
            return;
        }
        l.addStop(name, pos);
        System.out.println("Parada afegida correctament!");
    }

    private void menu_del_stop(int line) {
        Line l = this.lines.get(line);
        System.out.println();
        System.out.println("Eliminar parada a " + l.getName());
        System.out.print("Introdueix la posicio de la parada: ");
        int pos = Integer.parseInt(this.sc.next())-1;
        if(pos >= l.getSize() || pos < 0) {
            System.out.println("La posició es incorrecte!");
            return;
        }
        l.removeStop(pos);
        System.out.println("Parada eliminada correctament!");
    }

    private void menu_print() {
        int in = 0;
        do {
            System.out.println();
            System.out.println("0) Enrrere");
            for(int i = 0; i < this.lines.size(); i++) {
                Line l = this.lines.get(i);
                System.out.println((i+1) + ") Imprimir linia " + l.getName());
            }
            in = Integer.parseInt(sc.nextLine());
            if(in > 0 && in <= this.lines.size()) this.lines.get(in-1).printInfo();
        } while(in != 0);
    }

    private boolean load_file(String data) {
        String[] lines = data.split("-");
        for(String it : lines) {
            String[] l_data = it.split("/");
            if(l_data.length > 2) return false;
            String[] l_info = l_data[0].split(",");
            String[] l_stops = l_data[1].split(",");
            Line new_line = new Line(Integer.parseInt(l_info[0]), l_info[1], this);
            for(String _it : l_stops) {
                new_line.addStop(_it);
            }
            this.lines.add(new_line);
        }
        return true;
    }

    private boolean save_file() {
        String to_save = "";
        for(Line l : this.lines) {
            String l_info = l.getId() + "," + l.getColor();
            String l_stops = "";
            for(int i = 0; i < l.getSize(); i++) {
                l_stops += l.getStop(i) + ",";
            }
            if(l_stops.endsWith(",")) l_stops = l_stops.substring(0, l_stops.length()-2);
            to_save += l_info + "/" + l_stops + "-";
        }
        if(to_save.endsWith("-")) to_save = to_save.substring(0, to_save.length()-2);
        FileUtils.writeFile(to_save, this.currentFilePath);
        return true;
    }

    public LinkedList<Line> getLines() {
        return this.lines;
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
