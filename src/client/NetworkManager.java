package client;

import client.component.Painting;

import java.io.ObjectOutputStream;

public class NetworkManager {
    private final ObjectOutputStream out;
    private final PaintingManager pm;
    public NetworkManager(ObjectOutputStream out, PaintingManager pm) {
        this.out = out;
        this.pm = pm;
    }
    public void createObject(Painting object) {
    }
    public void removeObject(int id) {
    }
    public void updateObject(Painting object) {
    }
    public void selectObject(int id) {
    }


}
