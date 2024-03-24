package client.network;

import client.DataManager;
import client.component.*;
import client.frame.FrameManager;


public class ResponseManager {

    private final DataManager dm;
    private FrameManager fm;
    public ResponseManager(DataManager dataManager) {
        //if server sends paintings, use them
        this.dm = dataManager;
    }
    public void setFrameManager(FrameManager fm){
        this.fm=fm;
    }

    public void create(Painting p, boolean myRequest){
        dm.add(p);
        if(myRequest){
            fm.setAction(FrameManager.Action.FOCUS);
            fm.setFocusedPainting(p);
        }
        fm.repaint();
    }
    public void remove(int id ){
        dm.delete(id);
        fm.repaint();
    }
    public void update(Painting p){
        dm.edit(p);
        fm.repaint();
    }
    public void select(int id, boolean myRequest){
        if(myRequest){
            fm.setAction(FrameManager.Action.FOCUS);
            fm.setFocusedPainting(dm.getPainting(id));
        }
        dm.select(id);
        fm.repaint();
    }
    public void free(int id, boolean myRequest){
        if(myRequest){
            fm.setAction(FrameManager.Action.NORMAL);
            fm.setFocusedPainting(null);
        }
        dm.free(id);
        fm.repaint();
    }

}
