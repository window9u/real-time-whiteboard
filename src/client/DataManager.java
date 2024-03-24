package client;

import client.component.Painting;

import java.util.HashMap;
import java.util.Vector;

public class DataManager {
    private final HashMap<Integer, Painting> paintings;
    public DataManager() {
        paintings = new HashMap<>();
    }
    public Vector<Painting> getPaintings() {
        return new Vector<>(this.paintings.values());
    }
    public void add(Painting p){
        paintings.put(p.getId(), p);
    }
    public void delete(int id){
        paintings.remove(id);
    }
    public void edit(Painting p){
        paintings.replace(p.getId(), p);
    }
    public void select(int id){
        paintings.get(id).select();
    }
    public void free(int id){
        paintings.get(id).unselect();
    }
    public Painting getPainting(int id){
        return paintings.get(id);
    }

}
