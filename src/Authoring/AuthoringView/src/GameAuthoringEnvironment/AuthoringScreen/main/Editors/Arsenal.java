package GameAuthoringEnvironment.AuthoringScreen.main.Editors;

import java.io.Serializable;

public class Arsenal implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String name = "";

    public Arsenal(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}