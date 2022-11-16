package smol.util;

/** Utility for stringifying numbers. */
public class Stringer<T extends Number>{
    
    public static String wrap(T inp){
        return "" + inp + "";
    }
}
