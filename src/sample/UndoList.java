package sample;

import java.util.List;
import java.util.Stack;

public class UndoList {

    static List<Undo> undoList = new Stack<>();

    public static void addUndo(Undo undo){
        undoList.add(undo);

        if(undoList.size()>15)
            undoList.remove(0);
    }

    public static Undo getUndo(){
        Undo undo = undoList.get(undoList.size()-1);
        undoList.remove(undo);
        return undo;
    }

    public static int size(){
        return undoList.size();
    }

    public static boolean contains(Undo undo){
        return undoList.contains(undo);
    }
}
