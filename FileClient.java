
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileClient{
	
	//downloadFile is used to request a file from the server and save it in a desired location
	public void downloadFile(FileInterface f, String serverPath, String clientPath) throws IOException
	{	
	try{
		byte[] filedata = f.downloadOp(serverPath);
		FileOutputStream fos = new FileOutputStream(clientPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(filedata,0,filedata.length);
        bos.flush();
        bos.close();
        System.out.println("File Downloaded at:"+clientPath);
	   } catch(Exception e){
		   System.out.println("Client: Downloading File - Unsuccesful");
		   System.out.println("Client Error "+e.getMessage());
		   e.printStackTrace();
             }
	}

	//uploadFile is used to send the file to the server
	public void uploadFile(FileInterface f, String clientPath, String serverPath) throws IOException
	{
		try {
	         File file = new File(clientPath);
	         byte fileData[] = new byte[(int)file.length()];
	         FileInputStream fis = new FileInputStream(clientPath);
	         BufferedInputStream bis = new BufferedInputStream(fis);
	         bis.read(fileData,0,fileData.length);
	         bis.close();
	         System.out.println("File uploaded at:"+serverPath);
	         f.uploadOp(serverPath, fileData);
	       }
		catch(Exception e){
			System.out.println("Client: Uploading File - Unsuccesful");
			System.out.println("Client Error: "+e.getMessage());
			e.printStackTrace();
			}
	}

	//listDir requests and lists all the files and directories in a directory on the server
	public void listDir(FileInterface f, String serverPath) throws IOException
	{
			try {
				String NameList = f.listDir(serverPath);
				System.out.println(NameList);
			} catch (Exception e) {
				System.out.println("Client: List files - Unsuccesful");
				System.out.println("Client Error: "+e.getMessage());
				e.printStackTrace();
			}
		
	}

	//createDir sends a request to create a directory on the server if it doesn't already exist
	public void createDir(FileInterface f, String serverPath) throws IOException
	{
		try {
			String result = f.mkdirOp(serverPath);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Client: Create Directory - Unsuccesful");
			System.out.println("Client Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	//removeDir sends a request to remove a directory from the server if empty
	public void removeDir(FileInterface f, String serverPath) throws IOException
	{
		try {
			String result = f.rmdirOp(serverPath);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Client: Remove Directory - Unsuccesful");
			System.out.println("Client Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	//removeFile requests the server to delete the file
	public void removeFile(FileInterface f, String serverPath) throws IOException
	{
		try {
			String result = f.rmFileOp(serverPath);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Client: Remo	ve File - Unsuccesful");
			System.out.println("Client Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	//shutdown terminates the server connection
	public void shutdown(FileInterface f, Registry registry) throws IOException
	{	
		try {
			f.shutdown(registry);
		} catch (IOException e) {
			System.out.println("Connection terminated");
		}
		
		
	}	
	
   public static void main(String args[]) {
      
      try {
    	 String machinename = System.getenv("PA2_SERVER");
    	 Registry registry = LocateRegistry.getRegistry(machinename.substring(0, machinename.indexOf(':')));
         FileInterface fi = (FileInterface) registry.lookup("FileInterface");
         FileClient c =new FileClient();
                  
         if(args[0].equalsIgnoreCase("download"))
     	{
     		c.downloadFile(fi, args[1],args[2]);
     	}
     	else if(args[0].equalsIgnoreCase("upload"))
     	{
     		c.uploadFile(fi, args[1], args[2]);
     	}
     	else if(args[0].equalsIgnoreCase("dir"))
     	{    
     		 c.listDir(fi, args[1]);
     	}
     	else if(args[0].equalsIgnoreCase("mkdir"))
     	{
     		c.createDir(fi, args[1]);
     	}
     	else if(args[0].equalsIgnoreCase("rmdir"))
     	{
     		c.removeDir(fi, args[1]);
     	}
     	else if(args[0].equalsIgnoreCase("rm"))
     	{
     		c.removeFile(fi, args[1]);
     	}
     	else if(args[0].equalsIgnoreCase("shutdown"))
     	{
     		c.shutdown(fi, registry);
     	}
     	else
     	{
     		System.out.println("Invalid operation requested");
     	}
      } catch(Exception e) {
         System.err.println("Client exception: "+ e.getMessage());
         e.printStackTrace();
      }
   }
}