package interpreter.bytecodes;

/**
 *
 * @author dreruff
 */
public interface AddressResolution {
    public String getLabel();
    public void setAddress(int address);
    public int getAddress();  
}
