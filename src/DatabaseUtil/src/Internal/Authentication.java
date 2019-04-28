package Internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Authentication {

    private static final String HASHING_ALGORITHM = "MD5";
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final String NUMBER_CHECKER = "[0-9]";

    /**
     * Checks to make sure the user entered text for the username and password fields
     * @param username - Unique string to identify user
     * @param password - chosen string to verify user identity
     */
    public static void checkArgumentLengths(String username, String password){
        if (username.length() == 0){
            throw new IllegalArgumentException("You must enter a username in the field provided");
        }
        if (password.length() == 0){
            throw new IllegalArgumentException("You must enter a password in the field provided");
        }
    }

    /**
     * Calls the other checkArgumentLengths method and adds an additional check for text entered in the repeat password field
     * @param username - Unique string to identify user
     * @param password - chosen string to verify user identity
     * @param passwordRepeated passwordRepeated - repeated chosen string
     */
    public static void checkArgumentLengths(String username, String password, String passwordRepeated){
        checkArgumentLengths(username, password);
        if (passwordRepeated.length() == 0){
            throw new IllegalArgumentException("You must re-enter a password in the field provided");
        }
    }

    /**
     * Combines the user entered plaintext password string and salt into a byte array and hashes it
     * @param password - chosen string to verify user identity
     * @param salt - random 16 byte array concatenated with the password byte array for additional security
     * @return - hashed byte array of the concatenated password and salt
     */
    public static byte[] hashPassword(String password, byte[] salt){
        byte[] passwordBytes = password.getBytes();
        byte[] plainTextBytes = new byte[passwordBytes.length + salt.length];
        System.arraycopy(passwordBytes, 0, plainTextBytes, 0, passwordBytes.length);
        System.arraycopy(salt, 0, plainTextBytes, passwordBytes.length, salt.length);
        try {
            MessageDigest passwordDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            passwordDigest.update(plainTextBytes);
            return passwordDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks to ensure the user entered password and repeat password strings are identical
     * @param password - chosen string to verify user identity
     * @param passwordRepeated - repeated chosen string
     */
    public static void passwordErrorChecking(String password, String passwordRepeated){
        if (password.length() < MINIMUM_PASSWORD_LENGTH || !Pattern.compile(NUMBER_CHECKER).matcher(password).find()){
            throw new IllegalArgumentException("Password must be at least 6 characters and contain a number");
        }
        else if (! password.equals(passwordRepeated)){
            throw new IllegalArgumentException("Password and repeated password do not match");
        }
    }
}
