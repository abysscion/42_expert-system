package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BooleanFunction {
    public static ArrayList<Boolean> lnot(ArrayList<Boolean> left, ArrayList<Boolean> right){
        ArrayList<Boolean> result = new ArrayList<>();
        if (left != null){
            result = new ArrayList<>(Collections.nCopies(left.size(), false));
            for (int i = 0; i < left.size(); i++)
                result.set(i, !left.get(i));
            return result;
        }
        if (right != null){
            result = new ArrayList<>(Collections.nCopies(right.size(), false));
            for (int i = 0; i < right.size(); i++)
                result.set(i, !right.get(i));
        }
        return result;
    }

    public static ArrayList<Boolean> land(ArrayList<Boolean> left, ArrayList<Boolean> right){
        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(left.size(), false));
        for (int i = 0; i < left.size(); i++){
            result.set(i, left.get(i) | right.get(i));
        }
        return result;
    }
    public static ArrayList<Boolean> lxor(ArrayList<Boolean> left, ArrayList<Boolean> right){
        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(left.size(), false));
        for (int i = 0; i < left.size(); i++) {
            result.set(i, (left.get(i) | !right.get(i)) & (!left.get(i) | right.get(i)));
        }
        return result;
    }

    public static ArrayList<Boolean> lor(ArrayList<Boolean> left, ArrayList<Boolean> right) {
        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(left.size(), false));
        for (int i = 0; i < left.size(); i++) {
            result.set(i, left.get(i) & right.get(i));
        }
        return result;
    }
    public static ArrayList<Boolean> limplies(ArrayList<Boolean> left, ArrayList<Boolean> right){
        ArrayList<Boolean> result =  new ArrayList<>(Collections.nCopies(left.size(), false));
        for (int i = 0; i < left.size(); i++) {
            result.set(i, !left.get(i) & right.get(i));
        }
        return result;
    }


    public static ArrayList<Boolean> lidentity(ArrayList<Boolean> left, ArrayList<Boolean> right){
        ArrayList<Boolean> result =  new ArrayList<>(Collections.nCopies(left.size(), false));
        for (int i = 0; i < left.size(); i++) {
            result.set(i, (left.get(i) | right.get(i)) & (!left.get(i) | !right.get(i)));
        }
        return result;
    }
}
