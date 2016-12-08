import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.Registry;

public interface FileInterface extends Remote 
{
	public byte[] downloadOp(String serverPath) throws RemoteException;
	public void uploadOp(String serverPath, byte[] b) throws IOException;
	public String listDir(String serverPath) throws IOException;
	public String mkdirOp(String serverPath) throws IOException;
	public String rmdirOp(String serverPath) throws IOException;
	public String rmFileOp(String serverPath) throws IOException;
	public void shutdown(Registry registry) throws IOException;
}

