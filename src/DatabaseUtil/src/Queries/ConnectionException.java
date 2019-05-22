package Queries;

public class ConnectionException extends RuntimeException{
    public ConnectionException(){
        super("Connection to database failed");
    }
    public ConnectionException(String msg){
        super("Connection to database failed" + "\n"+ msg);
    }
    public ConnectionException(String msg, Exception e){
        this(msg+"\n"+e.toString());
    }
    public ConnectionException(Exception e){
        this(e.toString());
    }
}
