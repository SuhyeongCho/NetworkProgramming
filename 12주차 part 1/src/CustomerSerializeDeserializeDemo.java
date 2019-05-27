import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
public class CustomerSerializeDeserializeDemo {
 
    public static void main(String[] args) {
 
        Customer serializeCustomer = new Customer(102, "SR", 17);

        // creating output stream variables
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        // creating input stream variables
        FileInputStream fis = null;
        ObjectInputStream ois = null;
 
        // creating customer object reference 
        Customer deSerializeCustomer = null;
 
        try {

        	fos = new FileOutputStream("Customer.ser");
        	
        	oos = new ObjectOutputStream(fos);
        	
        	oos.writeObject(serializeCustomer);
        	oos.flush();
        	oos.close();
        	
        	System.out.println("Serialization : " + "Customer object saved to Customer.ser file\n");
        	System.out.println("Start reading binary data from Customer.ser");
        	fis = new FileInputStream("Customer.ser");
        	
        	ois = new ObjectInputStream(fis);
        	
        	System.out.println("reading objects value and casting to Customer class from Customer.cer(binary data)\n");
        	
        	deSerializeCustomer = (Customer)ois.readObject();
        	ois.close();
		} 
        catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }
        catch (ClassNotFoundException ccex) {
            ccex.printStackTrace();
        }
 
        // printing customer object to console using toString() method, refer Customer.java's toString()
        System.out.println("Printing customer values from "
                + "deserialized object... \n" + "-> "+deSerializeCustomer);
    }
}