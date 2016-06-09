#o_server.py
import subprocess
import socket

host = '192.168.0.100'        # Symbolic name meaning all available interfaces
port = 12345     # Arbitrary non-privileged port
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((host, port))
s.listen(1)
conn, addr = s.accept()
while True:
    data = conn.recv(1024)
    if not data: break
    
    print(data);
    if(data  == "SHUTDOWN"):
	print("Done")
        subprocess.call(['osascript', '-e',
	'tell app "System Events" to shut down'])
conn.close()
