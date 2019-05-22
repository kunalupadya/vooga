package BackendExternal;

public final class NotEnoughCashException extends Exception {


    /**
     * Create an exception based on an issue in our code.
     */
    public NotEnoughCashException(String message, Object... args) {
        super(format(message, args));
    }



    /**
     * Create an exception based on a caught exception with a different message.
     */
    public NotEnoughCashException(Throwable cause, String message, Object ... args) {
        super(format(message, args), cause);
    }


    // remove duplicate code, also placeholder for future improvements (like logging)
    private static String format (String message, Object ... args) {
        return String.format(message, args);
    }
}
