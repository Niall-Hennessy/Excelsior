package sample;

import java.util.List;
import java.util.Stack;

public class UndoList {

    static List<Undo> undoList = new Stack<>();

    public static List<Undo> getUndoList() {
        return undoList;
    }
}
