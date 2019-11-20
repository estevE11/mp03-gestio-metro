package cat.esteve.metro.lines;

import cat.esteve.metro.Main;

import java.util.LinkedList;

public class Line {
    private Main main;

    private String color;
    private int id;
    private LinkedList<String> stops;

    public Line(int id, String color, Main main) {
        this.id = id;
        this.color = color;
        this.stops = new LinkedList<String>();

        this.main = main;
    }

    public void printInfo() {
        LinkedList<Line> lines = main.getLines();
        System.out.println();
        System.out.println("Linia " + this.getName());
        System.out.println("Parades:");
        for(int i = 0; i < this.stops.size(); i++) {
            String coincidence = "";
            for(Line l : lines) {
                if(l.getId() == this.id) continue;
                for(int j = 0; j < l.getSize(); j++) {
                    if(l.getStop(j).equals(this.stops.get(i))) coincidence = l.getName();
                }
            }
            System.out.println((i+1) + ". " + this.stops.get(i) + (!coincidence.equals("") ? (" - Linia " + coincidence) : ""));
        }
    }

    public void addStop(String name) {
        this.stops.add(name);
    }

    public void addStop(String name, int i) {
        this.stops.add(i, name);
    }

    public void removeStop(int i) {
        this.stops.remove(i);
    }

    public String getStop(int i) {
        return this.stops.get(i);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public int getSize() {
        return this.stops.size();
    }

    public String getName() {
        return this.color + " (" + this.id + ")";
    }
}
