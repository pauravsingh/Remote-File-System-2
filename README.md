# Remote-File-System-2
Remote file system using Java RMI over a TCP/IP connection

This project was for an assignment to create a file system that can be accessed remotely using Sockets.
The system provides the following functionalities:

1. List directories
2. Make directory
3. Remove directory
4. Remove a file
5. Upload a file
6. Download a file
7. Shutdown the server

The system work over a CLI and the following instructions describe how to use it.
To setup the file system, following steps are to be followed

1. Compile and place the FileServer.class and FileInterface.class in the server and FileClient.class and FileInterface.class in the client. 
2. In a new terminal that is pointing at the java directory, configure the codebase as follows: >rmiregistry -J-Djava.rmi.server.codebase=file:///<path_to_FileServer>FileServer
3. Running Server:In a new terminal, run the server using > java FileServer start <portnumber>
4. Running Client:
   a. In a new terminal, set the enviroment variable for server machine name and port number
	for windows: >set PA2_SERVER=<machineName>:<portnumber>  
 	for linux: >export PA2_SERVER=<machineName>:<portnumber>
   b. Run the client using  > java -cp rmiclient.jar FileClient <command> <paths>
   c. The following are the list of commands with arguments
	1. java FileServer upload <path on client> <path on server> - to add a file in the server
	2. java FileClient download <path on server> <path on client> - to get a file from the server
	3. java FileClient dir <path on server> - to list all the files in server directory
	4. java FileClient mkdir <path on server> - to create a directory from the server if it doesnot exist
	5. java FileClient rmdir <path on server> - to remove directory from the server if it empty 
	6. java FileClient rm <path on server> - to delete a file in the server
	7. java FileClient shutdown - to close the server

Note: Please enter the absoulte path. The name of file in the destination must be included in the path.