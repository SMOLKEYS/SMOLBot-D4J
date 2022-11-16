package smol.util;

/** Utility for stringifying numbers. */
public class Stringer{
    
    public static <T extends Number> String wrap(T inp){
        return "" + inp + "";
    }
}
