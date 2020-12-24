package Core;

import java.util.Arrays;

public class BooleanFunction {
    public boolean[] lnot(boolean[] left, boolean[] right){
        boolean[] result = new boolean[left != null ? left.length : right.length];

        if (left != null){
            for (int i = 0; i < left.length; i++)
                result[i] = !left[i];
            return result;
        }
        if (right != null){
            for (int i = 0; i < right.length; i++)
                result[i] = !right[i];
        }
        return result;
    }

    public boolean[] land(boolean[] left, boolean[] right){
        boolean[] result = new boolean[left.length];
        for (int i = 0; i < left.length; i++){
            result[i] = left[i] | right[i];
        }
        return result;
    }
    public boolean[] lxor(boolean[] left, boolean[] right){
        boolean[] result = new boolean[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = (left[i] | !right[i]) & (!left[i] | right[i]);
        }
        return result;
    }

    public boolean[] lor(boolean[] left, boolean[] right) {
        boolean[] result = new boolean[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = left[i] & right[i];
        }
        return result;
    }
    public boolean[] limplies(boolean[] left, boolean[] right){
        boolean[] result = new boolean[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = !left[i] & right[i];
        }
        return result;
    }


    public boolean[] lidentity(boolean[] left, boolean[] right){
        boolean[] result = new boolean[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = (left[i] | right[i]) & (!left[i] | !right[i]);
        }
        return result;
    }
}
